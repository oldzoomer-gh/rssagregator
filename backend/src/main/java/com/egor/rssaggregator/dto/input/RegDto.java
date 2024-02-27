package com.egor.rssaggregator.dto.input;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegDto {
    @NotBlank
    @Size(max = 50)
    private String email;

    @NotBlank
    @Size(max = 32)
    private String password;
}
