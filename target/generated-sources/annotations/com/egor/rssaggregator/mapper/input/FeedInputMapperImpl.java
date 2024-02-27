package com.egor.rssaggregator.mapper.input;

import com.egor.rssaggregator.dto.input.AddFeedDto;
import com.egor.rssaggregator.entity.Feed;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-02-27T21:12:19+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.9 (Red Hat, Inc.)"
)
@Component
public class FeedInputMapperImpl implements FeedInputMapper {

    @Override
    public Feed toEntity(AddFeedDto addFeedDto) {
        if ( addFeedDto == null ) {
            return null;
        }

        Feed.FeedBuilder feed = Feed.builder();

        feed.name( addFeedDto.getName() );
        feed.url( addFeedDto.getUrl() );

        return feed.build();
    }
}
