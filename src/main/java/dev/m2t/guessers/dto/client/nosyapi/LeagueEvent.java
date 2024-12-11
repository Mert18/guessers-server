package dev.m2t.guessers.dto.client.nosyapi;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class LeagueEvent {
    @JsonProperty("MatchID")
    private Long matchId;

    @JsonProperty("DateTime")
    private String dateTime;

    @JsonProperty("League")
    private String league;

    @JsonProperty("Teams")
    private String teams;

    @JsonProperty("Team1")
    private String team1;

    @JsonProperty("Team2")
    private String team2;

    @JsonProperty("Team1Logo")
    private String team1Logo;

    @JsonProperty("Team2Logo")
    private String team2Logo;

    @JsonProperty("Bets")
    private List<Bet> bets;

    public LeagueEvent() {

    }

    public LeagueEvent(Long matchId, String dateTime, String league, String teams, String team1, String team2, String team1Logo, String team2Logo, List<Bet> bets) {
        this.matchId = matchId;
        this.dateTime = dateTime;
        this.league = league;
        this.teams = teams;
        this.team1 = team1;
        this.team2 = team2;
        this.team1Logo = team1Logo;
        this.team2Logo = team2Logo;
        this.bets = bets;
    }

    public Long getMatchId() {
        return matchId;
    }

    public void setMatchId(Long matchId) {
        this.matchId = matchId;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getLeague() {
        return league;
    }

    public void setLeague(String league) {
        this.league = league;
    }

    public String getTeam1() {
        return team1;
    }

    public void setTeam1(String team1) {
        this.team1 = team1;
    }

    public String getTeam2() {
        return team2;
    }

    public void setTeam2(String team2) {
        this.team2 = team2;
    }

    public String getTeam1Logo() {
        return team1Logo;
    }

    public void setTeam1Logo(String team1Logo) {
        this.team1Logo = team1Logo;
    }

    public String getTeam2Logo() {
        return team2Logo;
    }

    public void setTeam2Logo(String team2Logo) {
        this.team2Logo = team2Logo;
    }

    public List<Bet> getBets() {
        return bets;
    }

    public void setBets(List<Bet> bets) {
        this.bets = bets;
    }

    public String getTeams() {
        return teams;
    }

    public void setTeams(String teams) {
        this.teams = teams;
    }
}
