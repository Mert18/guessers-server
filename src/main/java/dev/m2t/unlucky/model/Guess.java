package dev.m2t.unlucky.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Guess {
    @Id
    private String id;
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Event event;
    private EventCase eventCase;
    private EventCaseOption eventCaseOption;

    public Guess () {

    }

    public Guess (Event event, EventCase eventCase, EventCaseOption eventCaseOption) {
        this.event = event;
        this.eventCase = eventCase;
        this.eventCaseOption = eventCaseOption;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public EventCase getEventCase() {
        return eventCase;
    }

    public void setEventCase(EventCase eventCase) {
        this.eventCase = eventCase;
    }

    public EventCaseOption getEventCaseOption() {
        return eventCaseOption;
    }

    public void setEventCaseOption(EventCaseOption eventCaseOption) {
        this.eventCaseOption = eventCaseOption;
    }
}
