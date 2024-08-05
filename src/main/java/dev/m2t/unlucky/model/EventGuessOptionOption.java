package dev.m2t.unlucky.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class EventGuessOptionOption {
    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private Double odds;
    @ManyToOne
    @JoinColumn(name = "event_guess_option_id", nullable = false)
    private EventGuessOption eventGuessOption;
    private LocalDateTime createdOn = LocalDateTime.now();

    public EventGuessOptionOption() {

    }

    public EventGuessOptionOption(String name, Double odds, EventGuessOption eventGuessOption) {
        this.name = name;
        this.odds = odds;
        this.eventGuessOption = eventGuessOption;
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

    public Double getOdds() {
        return odds;
    }

    public void setOdds(Double odds) {
        this.odds = odds;
    }

    public EventGuessOption getEventGuessOption() {
        return eventGuessOption;
    }

    public void setEventGuessOption(EventGuessOption eventGuessOption) {
        this.eventGuessOption = eventGuessOption;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }
}
