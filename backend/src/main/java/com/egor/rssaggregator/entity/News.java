package com.egor.rssaggregator.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "news")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "newsHead", length = 100, nullable = false)
    private String newsHead;

    @Column(name = "newsDate", nullable = false)
    private LocalDate newsDate;

    @Column(name = "newsText")
    @Lob
    private String newsText;

    @ToString.Exclude
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "feed_id")
    private Feed feed;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        News news = (News) o;
        return Objects.equals(id, news.id) &&
                Objects.equals(newsHead, news.newsHead) &&
                Objects.equals(newsDate, news.newsDate) &&
                Objects.equals(feed, news.feed);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, newsHead, newsDate, feed);
    }
}
