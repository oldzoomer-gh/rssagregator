package ru.gavrilovegor519.rssaggregator.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import ru.gavrilovegor519.rssaggregator.dto.input.user.LoginDto;
import ru.gavrilovegor519.rssaggregator.dto.input.user.RegDto;
import ru.gavrilovegor519.rssaggregator.entity.User;
import ru.gavrilovegor519.rssaggregator.mapper.util.EncodedMapping;
import ru.gavrilovegor519.rssaggregator.mapper.util.PasswordEncoderMapper;

@Mapper(componentModel = "spring",
        uses = PasswordEncoderMapper.class,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    @Mapping(source = "password", target = "password", qualifiedBy = EncodedMapping.class)
    User toEntity(LoginDto loginDto);

    @Mapping(source = "password", target = "password", qualifiedBy = EncodedMapping.class)
    User toEntity(RegDto regDto);
}
