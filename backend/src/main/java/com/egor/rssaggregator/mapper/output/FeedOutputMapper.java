package com.egor.rssaggregator.mapper.output;

import com.egor.rssaggregator.dto.output.GetFeedDto;
import com.egor.rssaggregator.entity.Feed;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FeedOutputMapper {
    GetFeedDto toDto(Feed feed);
}
