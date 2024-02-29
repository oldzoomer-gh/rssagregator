package com.egor.rssaggregator.dto.output;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class MoreTextNewsEntryDto {
    @NotBlank
    @Size(max = 100)
    private String newsHead;

    @NotNull
    private LocalDateTime newsDate;

    @NotNull
    private String newsText;

    @NotBlank
    @Size(max = 250)
    private String feedUrl;
}
