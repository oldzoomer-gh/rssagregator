package com.egor.rssaggregator.dto.input;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginDto {
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid Email")
    @Size(max = 50, message = "Email is too long")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(max = 32, min = 8, message = "Password is too long, or too short")
    private String password;
}
