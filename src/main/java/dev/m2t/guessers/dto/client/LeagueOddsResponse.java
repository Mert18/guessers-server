package dev.m2t.guessers.dto.client;

import java.util.List;

public class LeagueOddsResponse {
    private List<LeagueEvent> leagueEvents;

    public LeagueOddsResponse() {
    }

    public LeagueOddsResponse(List<LeagueEvent> leagueEvents) {
        this.leagueEvents = leagueEvents;
    }

    public List<LeagueEvent> getLeagueEvents() {
        return leagueEvents;
    }

    public void setLeagueEvents(List<LeagueEvent> leagueEvents) {
        this.leagueEvents = leagueEvents;
    }
}
