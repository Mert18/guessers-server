package dev.m2t.unlucky.dto.request;

import dev.m2t.unlucky.dto.SingleGuess;

import java.util.List;

public class CreateGuessPaperRequest {
    private List<SingleGuess> guesses;
    private Double stake;
    private Long roomId;

    public CreateGuessPaperRequest() {
    }

    public CreateGuessPaperRequest(List<SingleGuess> guesses, Double stake, Long roomId) {
        this.guesses = guesses;
        this.stake = stake;
        this.roomId = roomId;
    }

    public List<SingleGuess> getGuesses() {
        return guesses;
    }

    public void setGuesses(List<SingleGuess> guesses) {
        this.guesses = guesses;
    }

    public Double getStake() {
        return stake;
    }

    public void setStake(Double stake) {
        this.stake = stake;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }
}
