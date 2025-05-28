package dev.m2t.guessers.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "\"user\"")
public class User {
    @Id
    @GeneratedValue
    private Long id;

    private String username;
    private Double luck;
    private Integer publicGamePlayCount;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Room> ownedRooms = new ArrayList<>();

    private LocalDateTime createdOn = LocalDateTime.now();

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<RoomUser> rooms = new HashSet<>();

    public User() {

    }

    public User(String username) {
        this.username = username;
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

    public Set<RoomUser> getRooms() {
        return rooms;
    }

    public void setRooms(Set<RoomUser> rooms) {
        this.rooms = rooms;
    }

    public List<Room> getOwnedRooms() {
        return ownedRooms;
    }

    public void setOwnedRooms(List<Room> ownedRooms) {
        this.ownedRooms = ownedRooms;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;

        User user = (User) o;

        return username != null && username.equals(user.username);
    }

    public Integer getPublicGamePlayCount() {
        return publicGamePlayCount;
    }

    public void setPublicGamePlayCount(Integer publicGamePlayCount) {
        this.publicGamePlayCount = publicGamePlayCount;
    }
}
