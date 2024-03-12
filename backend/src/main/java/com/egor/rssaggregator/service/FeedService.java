package com.egor.rssaggregator.service;

import com.egor.rssaggregator.dto.input.AddFeedDto;
import com.egor.rssaggregator.dto.output.GetFeedDto;
import com.egor.rssaggregator.dto.output.MainNewsEntryDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Management of feeds service layer.
 * @author Egor Gavrilov (gavrilovegor519@gmail.com)
 */
public interface FeedService {
    /**
     * Add new feed.
     * @param addFeedDto Add feed DTO.
     * @param email Email of user who added feed.
     */
    void addFeed(AddFeedDto addFeedDto, String email);

    /**
     * Get all feeds.
     * @param email Email of user who requested feeds.
     * @return List of feeds.
     */
    List<GetFeedDto> getFeeds(String email);

    /**
     * Delete feed.
     * @param id ID of feed.
     * @param email Email of user who deleted feed.
     */
    void deleteFeed(long id, String email);

    /**
     * Get news headings.
     * @param email Email of user who requested news.
     * @param pageable Pageable object.
     * @return List of news headings.
     */
    Page<MainNewsEntryDto> getNewsHeadings(String email, Pageable pageable);
}
