package ru.gavrilovegor519.rssaggregator.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.gavrilovegor519.rssaggregator.dto.output.feed.NewsEntryDto;
import ru.gavrilovegor519.rssaggregator.entity.Feed;
import ru.gavrilovegor519.rssaggregator.entity.User;
import ru.gavrilovegor519.rssaggregator.exception.DuplicateFeedException;
import ru.gavrilovegor519.rssaggregator.exception.FeedNotFoundException;
import ru.gavrilovegor519.rssaggregator.exception.IncorrectInputDataException;
import ru.gavrilovegor519.rssaggregator.exception.UserNotFoundException;
import ru.gavrilovegor519.rssaggregator.repo.UserRepo;
import ru.gavrilovegor519.rssaggregator.service.FeedService;
import ru.gavrilovegor519.rssaggregator.util.GetFeed;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class FeedServiceImpl implements FeedService {
    private final UserRepo userRepo;

    @Override
    @Transactional
    public Feed addFeed(Feed feed, String email) {
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found!"));

        if (user.getFeeds().stream().anyMatch(f ->
                f.getUrl().equals(feed.getUrl()) || f.getName().equals(feed.getName()))) {
            throw new DuplicateFeedException("Feed already exists!");
        }

        user.addFeed(feed);

        return feed;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Feed> getFeeds(String email) {
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found!"));

        return user.getFeeds();
    }

    @Override
    @Transactional
    public void deleteFeed(long id, String email) {
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found!"));

        Feed feed = user.getFeeds().stream()
                .filter(feed2 -> feed2.getId() == id)
                .findFirst()
                .orElseThrow(() -> new FeedNotFoundException("Feed not found!"));

        user.removeFeed(feed);
    }

    @Override
    @Cacheable(value = "mainNews", key = "{#email, #pageable.pageNumber, #pageable.pageSize}")
    @Transactional(readOnly = true)
    public Page<NewsEntryDto> getNewsHeadings(String email, Pageable pageable) {
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found!"));
        List<Feed> feeds = user.getFeeds();

        List<NewsEntryDto> newsEntries = new LinkedList<>();

        feeds.stream()
                .map(Feed::getUrl)
                .forEach(url -> newsEntries.addAll(new ArrayList<>(GetFeed.getFeed(url)
                        .getEntries()
                        .stream()
                        .map(entry -> {
                            String title = entry.getTitle();
                            String link = entry.getLink();
                            Date date = entry.getPublishedDate();

                            NewsEntryDto mainNewsEntry = new NewsEntryDto();
                            mainNewsEntry.setNewsHead(title);
                            mainNewsEntry.setFeedUrl(link);
                            mainNewsEntry.setNewsDate(date.toInstant().
                                    atZone(ZoneId.systemDefault())
                                    .toLocalDateTime());

                            return mainNewsEntry;
                        }).toList())));

        newsEntries.sort((x, y) -> y.getNewsDate().compareTo(x.getNewsDate()));

        int start = (int) pageable.getOffset();

        if (start > newsEntries.size()) {
            throw new IncorrectInputDataException("Incorrect page number!");
        }

        int end = Math.min(start + pageable.getPageSize(), newsEntries.size());
        return new PageImpl<>(newsEntries.subList(start, end), pageable, newsEntries.size());
    }
}
