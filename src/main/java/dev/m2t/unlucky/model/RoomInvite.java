package dev.m2t.unlucky.model;

import dev.m2t.unlucky.model.enums.RoomInviteStatusEnum;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class RoomInvite {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    private RoomInviteStatusEnum status;
    private LocalDateTime createdOn = LocalDateTime.now();

    public RoomInvite() {

    }

    public RoomInvite(Room room, User user, RoomInviteStatusEnum status) {
        this.room = room;
        this.user = user;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
