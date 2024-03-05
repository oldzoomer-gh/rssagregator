package com.egor.rssaggregator.dto.output;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TokenDto {
    @NotBlank
    private String token;
}
