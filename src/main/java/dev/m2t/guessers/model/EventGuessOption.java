package dev.m2t.guessers.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class EventGuessOption {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @OneToMany(mappedBy = "eventGuessOption", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EventGuessOptionCase> eventGuessOptionCases = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    @JsonIgnore
    private Event event;
    private LocalDateTime createdOn = LocalDateTime.now();
    private Integer precedence;

    public EventGuessOption() {

    }

    public EventGuessOption(String name, List<EventGuessOptionCase> options, Event event, Integer precedence) {
        this.name = name;
        this.eventGuessOptionCases = options;
        this.event = event;
        this.precedence = precedence;
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

    public List<EventGuessOptionCase> getEventGuessOptionCases() {
        return eventGuessOptionCases;
    }

    public void setEventGuessOptionCases(List<EventGuessOptionCase> eventGuessOptionCases) {
        this.eventGuessOptionCases = eventGuessOptionCases;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public void addEventGuessOptionCase(EventGuessOptionCase eventGuessOptionCase) {
        eventGuessOptionCases.add(eventGuessOptionCase);
        eventGuessOptionCase.setEventGuessOption(this);
    }

    public Integer getPrecedence() {
        return precedence;
    }

    public void setPrecedence(Integer precedence) {
        this.precedence = precedence;
    }
}
