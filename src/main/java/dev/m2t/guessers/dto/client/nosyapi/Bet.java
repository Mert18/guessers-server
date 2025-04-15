package dev.m2t.guessers.dto.client.nosyapi;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Bet {

    @JsonProperty("gameID")
    private String gameId;

    @JsonProperty("matchID")
    private String matchId;

    @JsonProperty("gameName")
    private String gameName;

    @JsonProperty("type")
    private String type;

    private List<BetOdd> odds;

    public Bet() {

    }

    public Bet(String gameId, String matchId, String gameName, String type, List<BetOdd> betOdds) {
        this.gameId = gameId;
        this.matchId = matchId;
        this.gameName = gameName;
        this.type = type;
        this.odds = betOdds;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    public String getMatchId() {
        return matchId;
    }

    public void setMatchId(String matchId) {
        this.matchId = matchId;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public List<BetOdd> getOdds() {
        return odds;
    }

    public void setOdds(List<BetOdd> odds) {
        this.odds = odds;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
