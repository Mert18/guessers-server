package dev.m2t.guessers.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class ChangePasswordRequest {
    @NotNull(message = "Username cannot be null")
    private final String username;

    @NotNull(message = "Password cannot be null")
    private final String password;

    public ChangePasswordRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
