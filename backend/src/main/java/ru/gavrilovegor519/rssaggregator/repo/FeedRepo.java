package ru.gavrilovegor519.rssaggregator.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gavrilovegor519.rssaggregator.entity.Feed;

public interface FeedRepo extends JpaRepository<Feed, Long> {

}
