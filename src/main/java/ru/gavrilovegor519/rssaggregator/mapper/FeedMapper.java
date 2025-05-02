package ru.gavrilovegor519.rssaggregator.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import ru.gavrilovegor519.rssaggregator.dto.input.feed.FeedInputDto;
import ru.gavrilovegor519.rssaggregator.dto.output.feed.FeedOutputDto;
import ru.gavrilovegor519.rssaggregator.entity.Feed;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface FeedMapper {
    Feed map(FeedInputDto feedInputDto);

    List<FeedOutputDto> mapOutputToList(List<Feed> feeds);

    FeedOutputDto mapOutput(Feed feed);
}
