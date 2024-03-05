package com.egor.rssaggregator.mapper.output;

import com.egor.rssaggregator.dto.output.GetFeedDto;
import com.egor.rssaggregator.entity.Feed;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface FeedOutputMapper {
    GetFeedDto toDto(Feed feed);

    List<GetFeedDto> toDto(List<Feed> feeds);
}
