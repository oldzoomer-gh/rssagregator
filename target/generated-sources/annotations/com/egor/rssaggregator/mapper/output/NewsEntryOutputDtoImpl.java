package com.egor.rssaggregator.mapper.output;

import com.egor.rssaggregator.dto.output.MainNewsEntryDto;
import com.egor.rssaggregator.dto.output.MoreTextNewsEntryDto;
import com.egor.rssaggregator.entity.News;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-02-27T21:12:18+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.9 (Red Hat, Inc.)"
)
@Component
public class NewsEntryOutputDtoImpl implements NewsEntryOutputDto {

    @Autowired
    private FeedOutputMapper feedOutputMapper;

    @Override
    public MainNewsEntryDto toMainDto(News news) {
        if ( news == null ) {
            return null;
        }

        MainNewsEntryDto.MainNewsEntryDtoBuilder mainNewsEntryDto = MainNewsEntryDto.builder();

        mainNewsEntryDto.newsHead( news.getNewsHead() );
        mainNewsEntryDto.newsDate( news.getNewsDate() );
        mainNewsEntryDto.source( feedOutputMapper.toDto( news.getSource() ) );

        return mainNewsEntryDto.build();
    }

    @Override
    public MoreTextNewsEntryDto toMoreDto(News news) {
        if ( news == null ) {
            return null;
        }

        MoreTextNewsEntryDto.MoreTextNewsEntryDtoBuilder moreTextNewsEntryDto = MoreTextNewsEntryDto.builder();

        moreTextNewsEntryDto.newsHead( news.getNewsHead() );
        moreTextNewsEntryDto.newsDate( news.getNewsDate() );
        moreTextNewsEntryDto.newsText( news.getNewsText() );
        moreTextNewsEntryDto.source( feedOutputMapper.toDto( news.getSource() ) );

        return moreTextNewsEntryDto.build();
    }
}
