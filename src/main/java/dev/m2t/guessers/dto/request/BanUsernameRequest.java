package dev.m2t.guessers.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class BanUsernameRequest {
    @NotNull(message = "Username cannot be null")
    @Size(min = 3, max = 30, message = "Username must be between 3 and 30 characters")
    private String username;

    public BanUsernameRequest() {
    }

    public BanUsernameRequest(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
