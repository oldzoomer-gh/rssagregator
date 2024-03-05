package com.egor.rssaggregator.service.impl;

import com.egor.rssaggregator.dto.input.AddFeedDto;
import com.egor.rssaggregator.dto.output.GetFeedDto;
import com.egor.rssaggregator.dto.output.MainNewsEntryDto;
import com.egor.rssaggregator.entity.Feed;
import com.egor.rssaggregator.entity.User;
import com.egor.rssaggregator.exception.UserNotFound;
import com.egor.rssaggregator.mapper.input.FeedInputMapper;
import com.egor.rssaggregator.mapper.output.FeedOutputMapper;
import com.egor.rssaggregator.repo.UserRepo;
import com.egor.rssaggregator.service.FeedService;
import com.egor.rssaggregator.util.GetFeed;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
@AllArgsConstructor
public class FeedServiceImpl implements FeedService {
    private final FeedInputMapper feedInputMapper;
    private final FeedOutputMapper feedOutputMapper;
    private final UserRepo userRepo;

    @Override
    public void addFeed(AddFeedDto addFeedDto, String email) throws UserNotFound {
        Feed feed = feedInputMapper.toEntity(addFeedDto);

        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new UserNotFound("User not found!"));

        user.getFeeds().add(feed);

        userRepo.save(user);
    }

    @Override
    public List<GetFeedDto> getFeeds(String email) throws UserNotFound {
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new UserNotFound("User not found!"));

        return feedOutputMapper.toDto(user.getFeeds());
    }

    @Override
    public void deleteFeed(long id, String email) throws UserNotFound {
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new UserNotFound("User not found!"));

        user.getFeeds().removeIf(feed -> feed.getId() == id);

        userRepo.save(user);
    }

    @Override
    @Cacheable(value = "mainNews", key = "#email")
    public Page<MainNewsEntryDto> getNewsHeadings(String email, Pageable pageable)
            throws UserNotFound, ExecutionException, InterruptedException {
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new UserNotFound("User not found!"));
        List<Feed> feeds = user.getFeeds();

        var newsEntries = new ArrayList<MainNewsEntryDto>();

        for (Feed feed : feeds) {
            String url = feed.getUrl();

            var news = GetFeed.getFeed(url)
                    .getEntries()
                    .stream()
                    .map(entry -> {
                        var title = entry.getTitle();
                        var link = entry.getLink();
                        var date = entry.getPublishedDate();

                        var mainNewsEntry = new MainNewsEntryDto();
                        mainNewsEntry.setNewsHead(title);
                        mainNewsEntry.setFeedUrl(link);
                        mainNewsEntry.setNewsDate(date.toInstant().
                                atZone(ZoneId.systemDefault())
                                .toLocalDateTime());

                        return mainNewsEntry;
                    }).toList();

            newsEntries.addAll(news);
        }

        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), newsEntries.size());
        return new PageImpl<>(newsEntries.subList(start, end), pageable, newsEntries.size());
    }
}
