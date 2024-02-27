package com.egor.rssaggregator.mapper.input;

import com.egor.rssaggregator.dto.input.AddFeedDto;
import com.egor.rssaggregator.entity.Feed;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FeedInputMapper {
    Feed toEntity(AddFeedDto addFeedDto);
}
