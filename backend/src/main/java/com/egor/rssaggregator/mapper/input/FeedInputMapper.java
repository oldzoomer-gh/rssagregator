package com.egor.rssaggregator.mapper.input;

import com.egor.rssaggregator.dto.input.AddFeedDto;
import com.egor.rssaggregator.entity.Feed;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface FeedInputMapper {
    Feed toEntity(AddFeedDto addFeedDto);
}
