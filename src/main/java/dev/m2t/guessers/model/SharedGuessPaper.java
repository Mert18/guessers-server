package dev.m2t.guessers.model;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
public class SharedGuessPaper {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name="guess_paper_id", nullable = false)
    private GuessPaper guessPaper;

    private String token;

    @Column(name = "expiry_time", nullable = false)
    private Timestamp expiryTime;


    public SharedGuessPaper() {

    }

    public SharedGuessPaper(Long id, GuessPaper guessPaper, String token, Timestamp expiryTime) {
        this.id = id;
        this.guessPaper = guessPaper;
        this.token = token;
        this.expiryTime = expiryTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public GuessPaper getGuessPaper() {
        return guessPaper;
    }

    public void setGuessPaper(GuessPaper guessPaper) {
        this.guessPaper = guessPaper;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Timestamp getExpiryTime() {
        return expiryTime;
    }

    public void setExpiryTime(Timestamp expiryTime) {
        this.expiryTime = expiryTime;
    }
}
