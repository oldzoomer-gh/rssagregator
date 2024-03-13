package com.egor.rssaggregator.mapper;

import com.egor.rssaggregator.dto.FeedDto;
import com.egor.rssaggregator.entity.Feed;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface FeedMapper {
    Feed map(FeedDto feedDto);
    FeedDto map(Feed feed);
    List<FeedDto> mapToList(List<Feed> feeds);
}
