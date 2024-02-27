package com.egor.rssaggregator.mapper.output;

import com.egor.rssaggregator.dto.output.MainNewsEntryDto;
import com.egor.rssaggregator.dto.output.MoreTextNewsEntryDto;
import com.egor.rssaggregator.entity.News;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = FeedOutputMapper.class)
public interface NewsEntryOutputDto {
    MainNewsEntryDto toMainDto(News news);
    MoreTextNewsEntryDto toMoreDto(News news);
}
