package dev.m2t.unlucky.model;

import dev.m2t.unlucky.model.enums.GuessPaperStatusEnum;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class GuessPaper {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "guessPaper", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Guess> guesses = new ArrayList<>();
    private Double totalOdd = 1.0;
    private GuessPaperStatusEnum status;
    private LocalDateTime createdOn = LocalDateTime.now();

    public GuessPaper() {

    }

    public GuessPaper(User user, List<Guess> guesses, Double totalOdd, GuessPaperStatusEnum status) {
        this.user = user;
        this.guesses = guesses;
        this.totalOdd = totalOdd;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Guess> getGuesses() {
        return guesses;
    }

    public void setGuesses(List<Guess> guesses) {
        this.guesses = guesses;
    }

    public Double getTotalOdd() {
        return totalOdd;
    }

    public void setTotalOdd(Double totalOdd) {
        this.totalOdd = totalOdd;
    }

    public GuessPaperStatusEnum getStatus() {
        return status;
    }

    public void setStatus(GuessPaperStatusEnum status) {
        this.status = status;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }
}
