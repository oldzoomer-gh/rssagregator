package ru.gavrilovegor519.rssaggregator.dto.output.feed;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class NewsEntryDto {
    private String newsHead;

    private LocalDateTime newsDate;

    private String feedUrl;
}
