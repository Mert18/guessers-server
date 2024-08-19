package dev.m2t.unlucky.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity
public class Stats {
    @Id
    @GeneratedValue
    private Long id;

    private Long userCount;
    private Long roomCount;
    private Long eventCount;
    private LocalDateTime lastUpdated;

    public Stats() {

    }

    public Stats(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public Stats(Long userCount, Long roomCount, Long eventCount, LocalDateTime lastUpdated) {
        this.userCount = userCount;
        this.roomCount = roomCount;
        this.eventCount = eventCount;
        this.lastUpdated = lastUpdated;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserCount() {
        return userCount;
    }

    public void setUserCount(Long userCount) {
        this.userCount = userCount;
    }

    public Long getRoomCount() {
        return roomCount;
    }

    public void setRoomCount(Long roomCount) {
        this.roomCount = roomCount;
    }

    public Long getEventCount() {
        return eventCount;
    }

    public void setEventCount(Long eventCount) {
        this.eventCount = eventCount;
    }

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}
