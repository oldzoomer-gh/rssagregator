package com.egor.rssaggregator.dto.output;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MainNewsEntryDto {
    @NotBlank
    @Size(max = 100)
    private String newsHead;

    @NotNull
    private LocalDateTime newsDate;

    @NotBlank
    @Size(max = 250)
    private String feedUrl;
}
