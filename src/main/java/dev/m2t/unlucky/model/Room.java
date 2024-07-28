package dev.m2t.unlucky.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Room {
    @Id
    private String id;
    private String name;
    private String description;
    private User owner;
    private boolean isPublic;
    private List<RoomInvite> invites;
    private List<User> users;
    private LocalDateTime createdOn = LocalDateTime.now();

    public Room() {

    }

    public Room(String name, String description, User owner, boolean isPublic) {
        this.name = name;
        this.description = description;
        this.owner = owner;
        this.isPublic = isPublic;
        this.invites = new ArrayList<>();
        this.users = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    public List<RoomInvite> getInvites() {
        return invites;
    }

    public void setInvites(List<RoomInvite> invites) {
        this.invites = invites;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }
}
