package dev.m2t.guessers.dto.request;

import jakarta.validation.constraints.NotNull;

public record InviteUserRequest(
        @NotNull(message = "Username cannot be null")
        String username
) {
}
