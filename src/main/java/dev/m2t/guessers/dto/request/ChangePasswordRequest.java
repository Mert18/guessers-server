package dev.m2t.guessers.dto.request;

import jakarta.validation.constraints.NotNull;

public record ChangePasswordRequest(
        @NotNull(message = "Username cannot be null")
        String username,
        @NotNull(message = "Password cannot be null")
        String password
) {
}
