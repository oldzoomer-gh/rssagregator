package ru.gavrilovegor519.rssaggregator.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.gavrilovegor519.rssaggregator.dto.output.feed.NewsEntryDto;
import ru.gavrilovegor519.rssaggregator.entity.Feed;
import ru.gavrilovegor519.rssaggregator.entity.User;
import ru.gavrilovegor519.rssaggregator.exception.DuplicateFeedException;
import ru.gavrilovegor519.rssaggregator.exception.IncorrectInputDataException;
import ru.gavrilovegor519.rssaggregator.exception.UserNotFoundException;
import ru.gavrilovegor519.rssaggregator.repo.FeedRepo;
import ru.gavrilovegor519.rssaggregator.repo.UserRepo;
import ru.gavrilovegor519.rssaggregator.service.FeedService;
import ru.gavrilovegor519.rssaggregator.util.GetFeed;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class FeedServiceImpl implements FeedService {
    private final UserRepo userRepo;
    private final FeedRepo feedRepo;

    @Override
    public void addFeed(Feed feed, String email) {
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found!"));

        if (user.getFeeds().stream().anyMatch(f ->
                f.getUrl().equals(feed.getUrl()) || f.getName().equals(feed.getName()))) {
            throw new DuplicateFeedException("Feed already exists!");
        }

        feed.setUser(user);
        feedRepo.save(feed);
    }

    @Override
    public List<Feed> getFeeds(String email) {
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found!"));

        return user.getFeeds();
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
