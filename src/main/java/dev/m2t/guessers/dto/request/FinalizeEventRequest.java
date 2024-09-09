package dev.m2t.guessers.dto.request;

import java.util.List;

public class FinalizeEventRequest {
    private List<Long> winnerEventGuessOptionCases;

    public FinalizeEventRequest() {
    }

    public List<Long> getWinnerEventGuessOptionCases() {
        return winnerEventGuessOptionCases;
    }

    public void setWinnerEventGuessOptionCases(List<Long> winnerEventGuessOptionCases) {
        this.winnerEventGuessOptionCases = winnerEventGuessOptionCases;
    }
}
