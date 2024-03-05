package com.egor.rssaggregator.dto.input;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AddFeedDto {
    @NotBlank
    @Size(max = 50)
    private String name;

    @NotBlank
    @Size(max = 250)
    private String url;
}
