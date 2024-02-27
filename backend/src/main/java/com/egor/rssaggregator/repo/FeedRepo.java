package com.egor.rssaggregator.repo;

import com.egor.rssaggregator.entity.Feed;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FeedRepo extends JpaRepository<Feed, Long> {
    Optional<Feed> findById(long id);
}
