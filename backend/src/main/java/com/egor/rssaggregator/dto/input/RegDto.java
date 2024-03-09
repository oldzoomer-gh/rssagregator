package com.egor.rssaggregator.dto.input;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegDto {
    @NotBlank(message = "Email can't be blank")
    @Email(message = "Invalid email")
    @Size(max = 50, message = "Email must be less than 50 characters")
    private String email;

    @NotBlank(message = "Password can't be blank")
    @Size(max = 32, min = 8, message = "Password must be between 8 and 32 characters")
    private String password;
}
