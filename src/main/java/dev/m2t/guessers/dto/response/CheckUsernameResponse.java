package dev.m2t.guessers.dto.response;

public class CheckUsernameResponse {
    private boolean exists;
    private boolean isBanned;
    private String username;

    public CheckUsernameResponse() {
    }

    public CheckUsernameResponse(String username, boolean exists, boolean isBanned) {
        this.username = username;
        this.exists = exists;
        this.isBanned = isBanned;
    }

    public boolean isExists() {
        return exists;
    }

    public void setExists(boolean exists) {
        this.exists = exists;
    }

    public boolean isBanned() {
        return isBanned;
    }

    public void setBanned(boolean banned) {
        isBanned = banned;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
