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
    private EventGuessOptionOption eventGuessOptionOption;

    @ManyToOne
    @JoinColumn(name = "guess_paper_id", nullable = false)
    private GuessPaper guessPaper;

    @Enumerated(EnumType.STRING)
    private GuessStatusEnum status;

    public Guess () {

    }

    public Guess (Event event, EventGuessOption eventGuessOption, EventGuessOptionOption eventGuessOptionOption) {
        this.event = event;
        this.eventGuessOption = eventGuessOption;
        this.eventGuessOptionOption = eventGuessOptionOption;
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

    public EventGuessOptionOption getEventCaseOption() {
        return eventGuessOptionOption;
    }

    public void setEventCaseOption(EventGuessOptionOption eventGuessOptionOption) {
        this.eventGuessOptionOption = eventGuessOptionOption;
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
