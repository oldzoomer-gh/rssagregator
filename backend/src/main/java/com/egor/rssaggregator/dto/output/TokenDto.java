package com.egor.rssaggregator.dto.output;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TokenDto {
    @NotBlank
    private String token;
}
