package com.egor.rssaggregator.service;

import com.egor.rssaggregator.dto.input.AddFeedDto;
import com.egor.rssaggregator.dto.output.GetFeedDto;
import com.egor.rssaggregator.dto.output.MainNewsEntryDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FeedService {
    void addFeed(AddFeedDto addFeedDto, String email);

    List<GetFeedDto> getFeeds(String email);

    void deleteFeed(long id, String email);

    Page<MainNewsEntryDto> getNewsHeadings(String email, Pageable pageable);
}
