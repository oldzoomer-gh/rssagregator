package ru.gavrilovegor519.rssaggregator.dto.output.feed;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FeedOutputDto {
    private Long id;

    private String name;

    private String url;
}
