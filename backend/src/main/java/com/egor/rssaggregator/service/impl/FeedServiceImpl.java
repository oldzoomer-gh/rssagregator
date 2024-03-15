package com.egor.rssaggregator.service.impl;

import com.egor.rssaggregator.dto.FeedDto;
import com.egor.rssaggregator.dto.NewsEntryDto;
import com.egor.rssaggregator.entity.Feed;
import com.egor.rssaggregator.entity.User;
import com.egor.rssaggregator.exception.DuplicateFeedException;
import com.egor.rssaggregator.exception.IncorrectInputDataException;
import com.egor.rssaggregator.exception.UserNotFoundException;
import com.egor.rssaggregator.mapper.FeedMapper;
import com.egor.rssaggregator.repo.FeedRepo;
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

@Service
@AllArgsConstructor
public class FeedServiceImpl implements FeedService {
    private final FeedMapper feedMapper;
    private final UserRepo userRepo;
    private final FeedRepo feedRepo;

    @Override
    public void addFeed(FeedDto feedDto, String email) {
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found!"));

        if (user.getFeeds().stream().anyMatch(f ->
                f.getUrl().equals(feedDto.getUrl()) || f.getName().equals(feedDto.getName()))) {
            throw new DuplicateFeedException("Feed already exists!");
        }

        Feed feed = feedMapper.map(feedDto);
        feed.setUser(user);
        feedRepo.save(feed);
    }

    @Override
    public List<FeedDto> getFeeds(String email) {
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found!"));

        return feedMapper.mapToList(user.getFeeds());
    }

    @Override
    public void deleteFeed(long id, String email) {
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found!"));

        user.getFeeds().removeIf(feed -> feed.getId() == id);

        userRepo.save(user);
    }

    @Override
    @Cacheable(value = "mainNews", key = "{#email, #pageable.pageNumber, #pageable.pageSize}")
    public Page<NewsEntryDto> getNewsHeadings(String email, Pageable pageable) {
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found!"));
        List<Feed> feeds = user.getFeeds();

        var newsEntries = new ArrayList<NewsEntryDto>();

        for (Feed feed : feeds) {
            String url = feed.getUrl();

            var news = new ArrayList<>(GetFeed.getFeed(url)
                    .getEntries()
                    .stream()
                    .map(entry -> {
                        var title = entry.getTitle();
                        var link = entry.getLink();
                        var date = entry.getPublishedDate();

                        var mainNewsEntry = new NewsEntryDto();
                        mainNewsEntry.setNewsHead(title);
                        mainNewsEntry.setFeedUrl(link);
                        mainNewsEntry.setNewsDate(date.toInstant().
                                atZone(ZoneId.systemDefault())
                                .toLocalDateTime());

                        return mainNewsEntry;
                    }).toList());

            newsEntries.addAll(news);
        }

        newsEntries.sort((x, y) -> y.getNewsDate().compareTo(x.getNewsDate()));

        int start = (int) pageable.getOffset();

        if (start > newsEntries.size()) {
            throw new IncorrectInputDataException("Incorrect page number!");
        }

        int end = Math.min(start + pageable.getPageSize(), newsEntries.size());
        return new PageImpl<>(newsEntries.subList(start, end), pageable, newsEntries.size());
    }
}
