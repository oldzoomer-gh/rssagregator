package com.egor.rssaggregator.dto.output;

import com.egor.rssaggregator.entity.Feed;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class MoreTextNewsEntryDto {
    @NotBlank
    @Size(max = 100)
    private String newsHead;

    @NotNull
    private LocalDate newsDate;

    private String newsText;

    @NotNull
    private Feed source;
}
