package dev.m2t.guessers.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class LendLog {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    @ManyToOne
    @JoinColumn(name = "room_user_id", nullable = false)
    private RoomUser roomUser;

    private Double amount;

    private LocalDateTime createdOn;

    public LendLog() {

    }

    public LendLog(Room room, RoomUser roomUser, Double amount) {
        this.room = room;
        this.roomUser = roomUser;
        this.amount = amount;
        this.createdOn = LocalDateTime.now();
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

    public RoomUser getRoomUser() {
        return roomUser;
    }

    public void setRoomUser(RoomUser roomUser) {
        this.roomUser = roomUser;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }
}
