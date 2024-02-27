package com.egor.rssaggregator.dto.output;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class MainNewsEntryDto {
    @NotBlank
    @Size(max = 100)
    private String newsHead;

    @NotNull
    private LocalDate newsDate;

    @NotNull
    private GetFeedDto source;
}
