package dev.m2t.unlucky.model;


import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class User {
    @Id
    @GeneratedValue
    private Long id;

    private String username;
    private Double luck;

    @OneToMany(mappedBy = "owner")
    private List<Room> ownedRooms;

    private LocalDateTime createdOn = LocalDateTime.now();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RoomUser> rooms = new ArrayList<>();

    public User() {

    }

    public User(String username, Double luck) {
        this.username = username;
        this.luck = luck;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Double getLuck() {
        return luck;
    }

    public void setLuck(Double luck) {
        this.luck = luck;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public List<RoomUser> getRooms() {
        return rooms;
    }

    public void setRooms(List<RoomUser> rooms) {
        this.rooms = rooms;
    }

    public List<Room> getOwnedRooms() {
        return ownedRooms;
    }

    public void setOwnedRooms(List<Room> ownedRooms) {
        this.ownedRooms = ownedRooms;
    }
}
