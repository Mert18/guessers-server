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

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    @OneToMany(mappedBy = "guessPaper", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SingleGuess> guesses = new ArrayList<>();

    private Double totalOdd = 1.0;
    private Double stake;
    private Double wins;
    private GuessPaperStatusEnum status;
    private LocalDateTime createdOn = LocalDateTime.now();

    public GuessPaper() {

    }

    public GuessPaper(User user, Room room, List<SingleGuess> guesses, Double totalOdd, Double stake, Double wins, GuessPaperStatusEnum status) {
        this.user = user;
        this.room = room;
        this.guesses = guesses;
        this.totalOdd = totalOdd;
        this.stake = stake;
        this.wins = wins;
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

    public List<SingleGuess> getGuesses() {
        return guesses;
    }

    public void setGuesses(List<SingleGuess> guesses) {
        this.guesses = guesses;
    }

    public Double getTotalOdd() {
        return totalOdd;
    }

    public void setTotalOdd(Double totalOdd) {
        this.totalOdd = totalOdd;
    }

    public Double getStake() {
        return stake;
    }

    public void setStake(Double stake) {
        this.stake = stake;
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

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Double getWins() {
        return wins;
    }

    public void setWins(Double wins) {
        this.wins = wins;
    }
}
