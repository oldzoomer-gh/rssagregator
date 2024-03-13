package com.egor.rssaggregator.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

@Getter
@Setter
public class FeedDto {
    @NotBlank(message = "Name is required")
    @Size(max = 50, message = "Name must be less than 50 characters")
    private String name;

    @NotBlank(message = "URL is required")
    @Size(max = 250, message = "URL must be less than 250 characters")
    @URL(message = "Invalid URL")
    private String url;
}
