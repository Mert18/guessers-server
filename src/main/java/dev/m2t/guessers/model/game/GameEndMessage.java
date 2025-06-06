package dev.m2t.guessers.model.game;

public class GameEndMessage {
    private String action;

    public GameEndMessage() {
        this.action = "END";
    }

    public String getAction() {
        return action;
    }
}
