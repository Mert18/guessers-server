package dev.m2t.unlucky.model;

import java.time.LocalDateTime;

public class RoomUser {
    private Room room;
    private User user;
    private Double balance;
    private Boolean isOwner;
    private Integer score;
    private LocalDateTime createdOn = LocalDateTime.now();

    public RoomUser() {

    }

    public RoomUser(Room room, User user, Double balance, Boolean isOwner, Integer score) {
        this.room = room;
        this.user = user;
        this.balance = balance;
        this.isOwner = isOwner;
        this.score = score;
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

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Boolean getOwner() {
        return isOwner;
    }

    public void setOwner(Boolean owner) {
        isOwner = owner;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }
}
