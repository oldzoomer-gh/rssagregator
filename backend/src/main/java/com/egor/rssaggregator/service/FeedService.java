package com.egor.rssaggregator.service;

import com.egor.rssaggregator.dto.input.AddFeedDto;
import com.egor.rssaggregator.dto.output.GetFeedDto;
import com.egor.rssaggregator.dto.output.MainNewsEntryDto;
import com.egor.rssaggregator.exception.DuplicateFeed;
import com.egor.rssaggregator.exception.IncorrectInputData;
import com.egor.rssaggregator.exception.UserNotFound;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface FeedService {
    void addFeed(AddFeedDto addFeedDto, String email) throws UserNotFound, DuplicateFeed;

    List<GetFeedDto> getFeeds(String email) throws UserNotFound;

    void deleteFeed(long id, String email) throws UserNotFound;

    Page<MainNewsEntryDto> getNewsHeadings(String email, Pageable pageable)
            throws UserNotFound, ExecutionException, InterruptedException, IncorrectInputData;
}
