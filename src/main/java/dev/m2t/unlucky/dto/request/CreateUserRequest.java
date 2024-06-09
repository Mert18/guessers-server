package dev.m2t.unlucky.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CreateUserRequest {
    @NotNull(message = "Username cannot be null")
    @Size(min = 4, max = 30, message = "Username must be between 4 and 20 characters")
    private String username;
    @NotNull(message = "Password cannot be null")
    @Size(min = 4, max = 50, message = "Password must be between 8 and 20 characters")
    private String password;

    public CreateUserRequest() {
    }

    public CreateUserRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
