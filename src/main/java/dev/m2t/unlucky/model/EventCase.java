package dev.m2t.unlucky.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
public class EventCase {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @OneToMany(mappedBy = "eventCase", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EventCaseOption> options;

    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;
    private LocalDateTime createdOn = LocalDateTime.now();

    public EventCase() {

    }

    public EventCase(String name, List<EventCaseOption> options, Event event) {
        this.name = name;
        this.options = options;
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

    public List<EventCaseOption> getOptions() {
        return options;
    }

    public void setOptions(List<EventCaseOption> options) {
        this.options = options;
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
