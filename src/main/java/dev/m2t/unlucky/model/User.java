package dev.m2t.unlucky.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "users")
public class User {
    @Id
    private String id;
    private String username;
    private Double balance;
    private Double luckPercentage;
    private List<String> rooms = new ArrayList<>();
    private List<String> pendingRoomInvites = new ArrayList<>();

    public User(String username, Double balance, Double luckPercentage) {
        this.username = username;
        this.balance = balance;
        this.luckPercentage = luckPercentage;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Double getLuckPercentage() {
        return luckPercentage;
    }

    public void setLuckPercentage(Double luckPercentage) {
        this.luckPercentage = luckPercentage;
    }

    public List<String> getPendingRoomInvites() {
        return pendingRoomInvites;
    }

    public void setPendingRoomInvites(List<String> pendingRoomInvites) {
        this.pendingRoomInvites = pendingRoomInvites;
    }

    public List<String> getRooms() {
        return rooms;
    }

    public void setRooms(List<String> rooms) {
        this.rooms = rooms;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
