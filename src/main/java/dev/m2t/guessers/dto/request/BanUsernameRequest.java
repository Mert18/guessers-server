package dev.m2t.guessers.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record BanUsernameRequest(
        @NotNull(message = "Username cannot be null")
        @Size(min = 3, max = 30, message = "Username must be between 3 and 30 characters")
        String username
) {
}
