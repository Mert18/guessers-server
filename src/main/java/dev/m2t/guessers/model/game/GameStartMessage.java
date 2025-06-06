package dev.m2t.guessers.model.game;

public class GameStartMessage {
    private String action;

    public GameStartMessage() {
        this.action = "START";
    }

    public String getAction() {
        return action;
    }
}
