package com.egor.rssaggregator.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Entity
@Table(name = "feeds")
@Getter
@Setter
public class Feed {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", unique = true, length = 50, nullable = false)
    private String name;

    @Column(name = "url", unique = true, length = 250, nullable = false)
    private String url;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Feed feed)) return false;
        return Objects.equals(id, feed.id) && Objects.equals(url, feed.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, url);
    }
}
