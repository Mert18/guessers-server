package dev.m2t.guessers.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CreateUserRequest(
        @NotNull(message = "Username cannot be null")
        @Size(min = 3, max = 30, message = "Username must be between 4 and 20 characters")
        @Pattern(regexp = "^[a-zA-Z0-9._-]+$", message = "Username can only contain alphanumeric characters, dashes, underscores, and periods.")
        String username,
        @NotNull(message = "Password cannot be null")
        @Size(min = 3, max = 50, message = "Password must be at least 3 characters.")
        String password
) {
}
