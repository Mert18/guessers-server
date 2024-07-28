package dev.m2t.unlucky.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Entity
public class EventCaseOption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    private String name;
    private Double odd;
    private EventCase eventCase;
    private LocalDateTime createdOn = LocalDateTime.now();

    public EventCaseOption() {

    }

    public EventCaseOption(String name, Double odd, EventCase eventCase) {
        this.name = name;
        this.odd = odd;
        this.eventCase = eventCase;
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
