package dev.m2t.guessers.dto.client.nosyapi;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SingleMatch {
    @JsonProperty("MatchID")
    private String matchId;

    @JsonProperty("Teams")
    private String teams;

    public SingleMatch() {}

    public SingleMatch(String matchId, String teams) {
        this.matchId = matchId;
        this.teams = teams;
    }

    public String getMatchId() {
        return matchId;
    }

    public void setMatchId(String matchId) {
        this.matchId = matchId;
    }

    public String getTeams() {
        return teams;
    }

    public void setTeams(String teams) {
        this.teams = teams;
    }
}
