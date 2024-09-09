package dev.m2t.guessers.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.m2t.guessers.model.enums.EventStatusEnum;
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
    @JsonIgnore
    private Room room;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EventGuessOption> eventGuessOptions = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private EventStatusEnum status;

    private LocalDateTime createdOn = LocalDateTime.now();

    private LocalDateTime eventTime;

    public Event() {

    }

    public Event(String name, String description, Room room, List<EventGuessOption> eventGuessOptions, EventStatusEnum status, LocalDateTime eventTime) {
        this.name = name;
        this.description = description;
        this.room = room;
        this.eventGuessOptions = eventGuessOptions;
        this.status = status;
        this.eventTime = eventTime;
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

    public List<EventGuessOption> getEventGuessOptions() {
        return eventGuessOptions;
    }

    public void setEventGuessOptions(List<EventGuessOption> eventGuessOptions) {
        this.eventGuessOptions = eventGuessOptions;
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

    public void addEventGuessOption(EventGuessOption eventGuessOption) {
        eventGuessOptions.add(eventGuessOption);
        eventGuessOption.setEvent(this);
    }

    public LocalDateTime getEventTime() {
        return eventTime;
    }

    public void setEventTime(LocalDateTime eventTime) {
        this.eventTime = eventTime;
    }
}
