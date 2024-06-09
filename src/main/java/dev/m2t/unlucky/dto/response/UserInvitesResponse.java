package dev.m2t.unlucky.dto.response;

import dev.m2t.unlucky.model.Room;

import java.util.ArrayList;
import java.util.List;

public class UserInvitesResponse {
    private String username;
    private List<Room> pendingInvites = new ArrayList<>();

    public UserInvitesResponse() {
    }

    public UserInvitesResponse(String username, List<Room> pendingInvites) {
        this.username = username;
        this.pendingInvites = pendingInvites;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Room> getPendingInvites() {
        return pendingInvites;
    }

    public void setPendingInvites(List<Room> pendingInvites) {
        this.pendingInvites = pendingInvites;
    }
}
