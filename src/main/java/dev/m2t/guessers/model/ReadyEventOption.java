package dev.m2t.guessers.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class ReadyEventOption {
    @Id
    private String id;

    private String name;

    @OneToMany(mappedBy = "readyEventOption", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReadyEventOptionCase> readyEventOptionCases = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "ready_event_id", nullable = false)
    @JsonIgnore
    private ReadyEvent readyEvent;
    private int precedence;

    public ReadyEventOption() {

    }

    public ReadyEventOption(String id, String name, List<ReadyEventOptionCase> readyEventOptionCases, ReadyEvent readyEvent, int precedence) {
        this.id = id;
        this.name = name;
        this.readyEventOptionCases = readyEventOptionCases;
        this.readyEvent = readyEvent;
        this.precedence = precedence;
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

    public List<ReadyEventOptionCase> getReadyEventOptionCases() {
        return readyEventOptionCases;
    }

    public void setReadyEventOptionCases(List<ReadyEventOptionCase> readyEventOptionCases) {
        this.readyEventOptionCases = readyEventOptionCases;
    }

    public ReadyEvent getReadyEvent() {
        return readyEvent;
    }

    public void setReadyEvent(ReadyEvent readyEvent) {
        this.readyEvent = readyEvent;
    }

    public void addReadyEventOptionCase(ReadyEventOptionCase readyEventOptionCase) {
        readyEventOptionCases.add(readyEventOptionCase);
        readyEventOptionCase.setReadyEventOption(this);
    }

    public int getPrecedence() {
        return precedence;
    }

    public void setPrecedence(int precedence) {
        this.precedence = precedence;
    }
}
