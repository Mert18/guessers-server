package dev.m2t.guessers.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
public class ReadyEventOptionCase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Double odds;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ready_event_option_id", nullable = false)
    @JsonIgnore
    private ReadyEventOption readyEventOption;

    public ReadyEventOptionCase() {

    }

    public ReadyEventOptionCase(Long id, String name, Double odds, ReadyEventOption readyEventOption) {
        this.id = id;
        this.name = name;
        this.odds = odds;
        this.readyEventOption = readyEventOption;
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

    public ReadyEventOption getReadyEventOption() {
        return readyEventOption;
    }

    public void setReadyEventOption(ReadyEventOption readyEventOption) {
        this.readyEventOption = readyEventOption;
    }
}
