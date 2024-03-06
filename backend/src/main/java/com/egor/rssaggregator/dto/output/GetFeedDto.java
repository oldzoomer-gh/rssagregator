package com.egor.rssaggregator.dto.output;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetFeedDto {
    private String name;

    private String url;
}
