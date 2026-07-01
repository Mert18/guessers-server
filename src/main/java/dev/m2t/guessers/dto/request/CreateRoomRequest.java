package dev.m2t.guessers.dto.request;

import jakarta.validation.constraints.NotNull;

public record CreateRoomRequest(
        @NotNull(message = "Name cannot be null")
        String name,
        Boolean publico
) {
}
