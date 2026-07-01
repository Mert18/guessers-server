package dev.m2t.guessers.dto.response;

public record CheckUsernameResponse(String username, boolean exists, boolean isBanned) {
}
