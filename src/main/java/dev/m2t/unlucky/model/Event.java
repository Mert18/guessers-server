package dev.m2t.unlucky.model;

import dev.m2t.unlucky.model.enums.EventStatusEnum;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Event {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String description;

    @ManyToOne
    private Room room;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EventCase> eventCases = new ArrayList<>();

    private EventStatusEnum status;
    private LocalDateTime createdOn = LocalDateTime.now();

    public Event() {

    }

    public Event(String name, String description, Room room, List<EventCase> eventCases, EventStatusEnum status) {
        this.name = name;
        this.description = description;
        this.room = room;
        this.eventCases = eventCases;
        this.status = status;
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

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public List<EventCase> getEventCases() {
        return eventCases;
    }

    public void setEventCases(List<EventCase> eventCases) {
        this.eventCases = eventCases;
    }

    public EventStatusEnum getStatus() {
        return status;
    }

    public void setStatus(EventStatusEnum status) {
        this.status = status;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }
}
