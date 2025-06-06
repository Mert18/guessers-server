package dev.m2t.guessers.model.game;

import java.util.List;
import java.util.Map;

public class GameRoundMessage {
    private String action;
    private Integer round;
    private List<String> roundPicks;
    private Map<String, Integer> playersScore;

    public GameRoundMessage(Integer round, List<String> roundPicks, Map<String, Integer> playersScore) {
        this.action = "ROUND";
        this.round = round;
        this.roundPicks = roundPicks;
        this.playersScore = playersScore;
    }

    public String getAction() {
        return action;
    }

    public Integer getRound() {
        return round;
    }

    public List<String> getRoundPicks() {
        return roundPicks;
    }

    public Map<String, Integer> getPlayersScore() {
        return playersScore;
    }
}
