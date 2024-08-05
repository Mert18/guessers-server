package dev.m2t.unlucky.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.List;

@Entity
public class EventGuessOption {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @OneToMany(mappedBy = "eventGuessOption", cascade = CascadeType.ALL, orphanRemoval = true)
    @Size(min = 2, message = "Options must be at least 2")
    private List<EventGuessOptionOption> eventGuessOptionOptions;

    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;
    private LocalDateTime createdOn = LocalDateTime.now();

    public EventGuessOption() {

    }

    public EventGuessOption(String name, List<EventGuessOptionOption> options, Event event) {
        this.name = name;
        this.eventGuessOptionOptions = options;
        this.event = event;
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

    public List<EventGuessOptionOption> getEventGuessOptionOptions() {
        return eventGuessOptionOptions;
    }

    public void setEventGuessOptionOptions(List<EventGuessOptionOption> eventGuessOptionOptions) {
        this.eventGuessOptionOptions = eventGuessOptionOptions;
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
}
