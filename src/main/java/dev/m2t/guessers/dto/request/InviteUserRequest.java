package dev.m2t.guessers.dto.request;

import jakarta.validation.constraints.NotNull;

public class InviteUserRequest {
    @NotNull(message = "Username cannot be null")
    private String username;

    public InviteUserRequest() {
    }

    public InviteUserRequest(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
