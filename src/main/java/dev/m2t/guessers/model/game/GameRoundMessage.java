package dev.m2t.guessers.model.game;

import java.util.List;
import java.util.Map;

public class GameRoundMessage {
    private String action;
    private Integer round;
    private List<String> roundPicks;
    private Map<String, Integer> playersScore;
    private List<String> matchResults;

    public GameRoundMessage(Integer round, List<String> roundPicks, Map<String, Integer> playersScore, List<String> matchResults) {
        this.action = "ROUND";
        this.round = round;
        this.roundPicks = roundPicks;
        this.playersScore = playersScore;
        this.matchResults = matchResults;
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

    public List<String> getMatchResults() {
        return matchResults;
    }
}
