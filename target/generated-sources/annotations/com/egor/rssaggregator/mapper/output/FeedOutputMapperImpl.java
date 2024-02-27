package com.egor.rssaggregator.mapper.output;

import com.egor.rssaggregator.dto.output.GetFeedDto;
import com.egor.rssaggregator.entity.Feed;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-02-27T21:12:19+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.9 (Red Hat, Inc.)"
)
@Component
public class FeedOutputMapperImpl implements FeedOutputMapper {

    @Override
    public GetFeedDto toDto(Feed feed) {
        if ( feed == null ) {
            return null;
        }

        GetFeedDto.GetFeedDtoBuilder getFeedDto = GetFeedDto.builder();

        getFeedDto.name( feed.getName() );
        getFeedDto.url( feed.getUrl() );

        return getFeedDto.build();
    }
}
