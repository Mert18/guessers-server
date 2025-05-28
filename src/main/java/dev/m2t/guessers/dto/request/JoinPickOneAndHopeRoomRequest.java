package dev.m2t.guessers.dto.request;

import dev.m2t.guessers.model.User;

public class JoinPickOneAndHopeRoomRequest {
    private String username;
    private String object;
    private String sessionId;

    public JoinPickOneAndHopeRoomRequest() {

    }

    public JoinPickOneAndHopeRoomRequest(String username, String object, String sessionId) {
        this.username = username;
        this.object = object;
        this.sessionId = sessionId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}
