package dev.m2t.unlucky.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    private String username;
    private Double luck;
    private LocalDateTime createdOn = LocalDateTime.now();
    private List<RoomUser> rooms = new ArrayList<>();

    public User() {

    }

    public User(String username, Double luck) {
        this.username = username;
        this.luck = luck;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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
}
