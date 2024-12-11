package dev.m2t.guessers.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class ReadyEvent {
    @Id
    private String id;

    private String name;

    @Column(columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private ZonedDateTime commenceTime;

    @OneToMany(mappedBy = "readyEvent", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReadyEventOption> readyEventOptions = new ArrayList<>();

    private LocalDateTime createdOn = LocalDateTime.now();

    private String league;

    public ReadyEvent() {

    }

    public ReadyEvent(String id, String name, ZonedDateTime commenceTime, List<ReadyEventOption> readyEventOptions, LocalDateTime createdOn, String league) {
        this.id = id;
        this.name = name;
        this.commenceTime = commenceTime;
        this.readyEventOptions = readyEventOptions;
        this.createdOn = createdOn;
        this.league = league;
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

    public List<ReadyEventOption> getReadyEventOptions() {
        return readyEventOptions;
    }

    public void setReadyEventOptions(List<ReadyEventOption> readyEventOptions) {
        this.readyEventOptions = readyEventOptions;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public ZonedDateTime getCommenceTime() {
        return commenceTime;
    }

    public void setCommenceTime(ZonedDateTime commenceTime) {
        this.commenceTime = commenceTime;
    }

    public void  addReadyEventOption(ReadyEventOption readyEventOption) {
        readyEventOptions.add(readyEventOption);
        readyEventOption.setReadyEvent(this);
    }

    public String getLeague() {
        return league;
    }

    public void setLeague(String league) {
        this.league = league;
    }
}
