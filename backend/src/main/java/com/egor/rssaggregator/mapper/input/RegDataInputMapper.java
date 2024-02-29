package com.egor.rssaggregator.mapper.input;

import com.egor.rssaggregator.dto.input.RegDto;
import com.egor.rssaggregator.entity.User;
import com.egor.rssaggregator.mapper.util.EncodedMapping;
import com.egor.rssaggregator.mapper.util.PasswordEncoderMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        uses = PasswordEncoderMapper.class,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RegDataInputMapper {
    @Mapping(source = "password", target = "password", qualifiedBy = EncodedMapping.class)
    User toEntity(RegDto regDto);
}
