package com.egor.rssaggregator.dto.input;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

@Data
public class AddFeedDto {
    @NotBlank(message = "Name is required")
    @Size(max = 50, message = "Name must be less than 50 characters")
    private String name;

    @NotBlank(message = "URL is required")
    @Size(max = 250, message = "URL must be less than 250 characters")
    @URL(message = "Invalid URL")
    private String url;
}
