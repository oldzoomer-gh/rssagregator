package com.egor.rssaggregator.mapper;

import com.egor.rssaggregator.dto.input.feed.FeedInputDto;
import com.egor.rssaggregator.entity.Feed;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface FeedMapper {
    Feed map(FeedInputDto feedInputDto);
    FeedInputDto map(Feed feed);
    List<FeedInputDto> mapToList(List<Feed> feeds);
}
