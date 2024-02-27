package com.egor.rssaggregator.repo;

import com.egor.rssaggregator.entity.News;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NewsRepo extends JpaRepository<News, Long> {
    Optional<News> findById(long id);
}
