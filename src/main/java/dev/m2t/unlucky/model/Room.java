package dev.m2t.unlucky.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Room {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String description;

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;
    private boolean isPublic;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RoomInvite> invites;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RoomUser> roomUsers = new ArrayList<>();

    private LocalDateTime createdOn = LocalDateTime.now();

    public Room() {

    }

    public Room(String name, String description, User owner, boolean isPublic) {
        this.name = name;
        this.description = description;
        this.owner = owner;
        this.isPublic = isPublic;
        this.invites = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public List<RoomUser> getRoomUsers() {
        return roomUsers;
    }

    public void setRoomUsers(List<RoomUser> roomUsers) {
        this.roomUsers = roomUsers;
    }
}
