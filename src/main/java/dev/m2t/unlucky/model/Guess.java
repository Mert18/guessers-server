package dev.m2t.unlucky.model;

import dev.m2t.unlucky.model.enums.GuessStatusEnum;
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
    private EventGuessOption eventGuessOption;

    @ManyToOne
    @JoinColumn(name = "event_case_option_id", nullable = false)
    private EventGuessOptionCase eventGuessOptionCase;

    @ManyToOne
    @JoinColumn(name = "guess_paper_id", nullable = false)
    private GuessPaper guessPaper;

    @Enumerated(EnumType.STRING)
    private GuessStatusEnum status;

    public Guess () {

    }

    public Guess (Event event, EventGuessOption eventGuessOption, EventGuessOptionCase eventGuessOptionCase) {
        this.event = event;
        this.eventGuessOption = eventGuessOption;
        this.eventGuessOptionCase = eventGuessOptionCase;
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

    public EventGuessOption getEventCase() {
        return eventGuessOption;
    }

    public void setEventCase(EventGuessOption eventGuessOption) {
        this.eventGuessOption = eventGuessOption;
    }

    public EventGuessOptionCase getEventCaseOption() {
        return eventGuessOptionCase;
    }

    public void setEventCaseOption(EventGuessOptionCase eventGuessOptionCase) {
        this.eventGuessOptionCase = eventGuessOptionCase;
    }

    public GuessPaper getGuessPaper() {
        return guessPaper;
    }

    public void setGuessPaper(GuessPaper guessPaper) {
        this.guessPaper = guessPaper;
    }

    public GuessStatusEnum getStatus() {
        return status;
    }

    public void setStatus(GuessStatusEnum status) {
        this.status = status;
    }
}
