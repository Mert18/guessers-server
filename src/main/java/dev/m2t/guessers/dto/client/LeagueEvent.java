package dev.m2t.guessers.dto.client;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.List;

public class LeagueEvent {
    private String id;

    @JsonProperty("sport_key")
    private String sportKey;

    @JsonProperty("home_team")
    private String homeTeam;

    @JsonProperty("away_team")
    private String awayTeam;

    @JsonProperty("commence_time")
    private LocalDateTime commenceTime;


    private List<LeagueEventBookMaker> bookmakers;

    public LeagueEvent() {
    }

    public LeagueEvent(String id, String sportKey, String homeTeam, String awayTeam, LocalDateTime commenceTime, List<LeagueEventBookMaker> bookmakers) {
        this.id = id;
        this.sportKey = sportKey;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.commenceTime = commenceTime;
        this.bookmakers = bookmakers;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(String homeTeam) {
        this.homeTeam = homeTeam;
    }

    public String getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(String awayTeam) {
        this.awayTeam = awayTeam;
    }

    public LocalDateTime getCommenceTime() {
        return commenceTime;
    }

    public void setCommenceTime(LocalDateTime commenceTime) {
        this.commenceTime = commenceTime;
    }

    public List<LeagueEventBookMaker> getBookmakers() {
        return bookmakers;
    }

    public void setBookmakers(List<LeagueEventBookMaker> bookMakers) {
        this.bookmakers = bookMakers;
    }

    public String getSportKey() {
        return sportKey;
    }

    public void setSportKey(String sportKey) {
        this.sportKey = sportKey;
    }
}
