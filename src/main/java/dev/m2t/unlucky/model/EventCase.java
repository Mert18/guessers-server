package dev.m2t.unlucky.model;

import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.List;

public class EventCase {
    @Id
    private String id;

    private String name;
    private List<EventCaseOption> options;
    private Event event;
    private LocalDateTime createdOn = LocalDateTime.now();

    public EventCase() {

    }

    public EventCase(String name, List<EventCaseOption> options, Event event) {
        this.name = name;
        this.options = options;
        this.event = event;
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
