package dev.m2t.unlucky.model;

import dev.m2t.unlucky.model.enums.EventStatusEnum;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Event {
    @Id
    private String id;
    private String name;
    private String description;
    private Room room;
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
