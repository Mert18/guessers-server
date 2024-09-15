package dev.m2t.guessers.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Prize {
    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String description;

    private Double value;

    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    @JsonIgnore
    private Room room;

    private boolean active = true;

    @ManyToOne
    @JoinColumn(name = "sold_to_id")
    @JsonIgnore
    private RoomUser soldTo;

    private LocalDateTime createdOn = LocalDateTime.now();

    public Prize() {

    }

    public Prize(String name, String description, Double value, Room room, boolean active, RoomUser soldTo) {
        this.name = name;
        this.description = description;
        this.value = value;
        this.room = room;
        this.active = active;
        this.soldTo = soldTo;
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

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public RoomUser getSoldTo() {
        return soldTo;
    }

    public void setSoldTo(RoomUser soldTo) {
        this.soldTo = soldTo;
    }
}
