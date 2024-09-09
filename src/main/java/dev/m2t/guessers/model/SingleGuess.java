package dev.m2t.guessers.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
public class SingleGuess {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    @ManyToOne
    @JoinColumn(name = "event_guess_option_id", nullable = false)
    private EventGuessOption eventGuessOption;

    @ManyToOne
    @JoinColumn(name = "event_guess_option_case_id", nullable = false)
    private EventGuessOptionCase eventGuessOptionCase;

    @ManyToOne
    @JoinColumn(name = "guess_paper_id", nullable = false)
    @JsonIgnore
    private GuessPaper guessPaper;

    public SingleGuess() {
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

    public EventGuessOption getEventGuessOption() {
        return eventGuessOption;
    }

    public void setEventGuessOption(EventGuessOption eventGuessOption) {
        this.eventGuessOption = eventGuessOption;
    }

    public EventGuessOptionCase getEventGuessOptionCase() {
        return eventGuessOptionCase;
    }

    public void setEventGuessOptionCase(EventGuessOptionCase eventGuessOptionCase) {
        this.eventGuessOptionCase = eventGuessOptionCase;
    }

    public GuessPaper getGuessPaper() {
        return guessPaper;
    }

    public void setGuessPaper(GuessPaper guessPaper) {
        this.guessPaper = guessPaper;
    }
}
