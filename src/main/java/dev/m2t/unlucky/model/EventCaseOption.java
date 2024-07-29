package dev.m2t.unlucky.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class EventCaseOption {
    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private Double odd;
    @ManyToOne
    @JoinColumn(name = "event_case_id", nullable = false)
    private EventCase eventCase;
    private LocalDateTime createdOn = LocalDateTime.now();

    public EventCaseOption() {

    }

    public EventCaseOption(String name, Double odd, EventCase eventCase) {
        this.name = name;
        this.odd = odd;
        this.eventCase = eventCase;
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

    public Double getOdd() {
        return odd;
    }

    public void setOdd(Double odd) {
        this.odd = odd;
    }

    public EventCase getEventCase() {
        return eventCase;
    }

    public void setEventCase(EventCase eventCase) {
        this.eventCase = eventCase;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }
}
