package com.egor.rssaggregator.dto.output;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class GetFeedDto {
    @NotBlank
    @Size(max = 50)
    private String name;

    @NotBlank
    @Size(max = 250)
    private String url;
}
