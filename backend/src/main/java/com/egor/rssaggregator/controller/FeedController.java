package com.egor.rssaggregator.controller;

import com.egor.rssaggregator.dto.input.AddFeedDto;
import com.egor.rssaggregator.dto.output.GetFeedDto;
import com.egor.rssaggregator.dto.output.MainNewsEntryDto;
import com.egor.rssaggregator.exception.UserNotFound;
import com.egor.rssaggregator.service.FeedService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/1.0/feed")
@AllArgsConstructor
public class FeedController {
    private final FeedService feedService;

    @PostMapping("/add")
    public void addFeed(@RequestBody AddFeedDto dto,
                        Authentication authentication)
            throws UserNotFound {
        feedService.addFeed(dto, authentication.getName());
    }

    @GetMapping("/listFeeds")
    public List<GetFeedDto> listFeeds(Authentication authentication)
            throws UserNotFound {
        return feedService.getFeeds(authentication.getName());
    }

    @DeleteMapping("/delete/{id}")
    public void deleteFeed(@PathVariable long id,
                           Authentication authentication)
            throws UserNotFound {
        feedService.deleteFeed(id, authentication.getName());
    }

    @GetMapping("/getNews")
    public Page<MainNewsEntryDto> getNews(Authentication authentication,
                                          @RequestParam(defaultValue = "0") int page,
                                          @RequestParam(defaultValue = "10") int size)
            throws UserNotFound, ExecutionException, InterruptedException {
        var pageable = PageRequest.of(page, size);
        return feedService.getNewsHeadings(authentication.getName(), pageable);
    }
}
