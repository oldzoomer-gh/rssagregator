package com.egor.rssaggregator.repo;

import com.egor.rssaggregator.entity.Feed;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedRepo extends JpaRepository<Feed, Long> {

}
