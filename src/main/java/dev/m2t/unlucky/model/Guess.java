package dev.m2t.unlucky.model;

import jakarta.persistence.*;

@Entity
public class Guess {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    @ManyToOne
    @JoinColumn(name = "event_case_id", nullable = false)
    private EventCase eventCase;

    @ManyToOne
    @JoinColumn(name = "event_case_option_id", nullable = false)
    private EventCaseOption eventCaseOption;

    @ManyToOne
    @JoinColumn(name = "guess_paper_id", nullable = false)
    private GuessPaper guessPaper;

    public Guess () {

    }

    public Guess (Event event, EventCase eventCase, EventCaseOption eventCaseOption) {
        this.event = event;
        this.eventCase = eventCase;
        this.eventCaseOption = eventCaseOption;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public GuessPaper getGuessPaper() {
        return guessPaper;
    }

    public void setGuessPaper(GuessPaper guessPaper) {
        this.guessPaper = guessPaper;
    }
}
