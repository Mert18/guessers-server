package dev.m2t.unlucky.model;

import dev.m2t.unlucky.model.enums.RoomInviteStatusEnum;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

public class RoomInvite {
    @Id
    private String id;

    private Room room;
    private User user;
    private RoomInviteStatusEnum status;
    private LocalDateTime createdOn = LocalDateTime.now();

    public RoomInvite() {

    }

    public RoomInvite(Room room, User user, RoomInviteStatusEnum status) {
        this.room = room;
        this.user = user;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public RoomInviteStatusEnum getStatus() {
        return status;
    }

    public void setStatus(RoomInviteStatusEnum status) {
        this.status = status;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }
}
