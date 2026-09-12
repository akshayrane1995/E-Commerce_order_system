package com.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserCreateDto(

        @NotBlank(message = "Name is required")
        String name,

        @NotBlank(message = "Email is required")
        @Email(message = "Please enter a valid email")
        String email,

        @NotBlank(message = "Password is required")
        @Size(min = 6, message = "Password must be at least 6 characters")
        String password,

        @NotBlank(message = "Phone is required")
        @Pattern(regexp = "^[6-9]\\d{9}$",  message = "Phone number must be a valid 10-digit number")
        String phone

) {}