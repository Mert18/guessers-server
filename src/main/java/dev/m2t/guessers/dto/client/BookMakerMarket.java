package dev.m2t.guessers.dto.client;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.List;

public class BookMakerMarket {
    private String key;
    @JsonProperty("last_update")
    private LocalDateTime lastUpdate;
    private List<BookMakerMarketOutcome> outcomes;

    public BookMakerMarket() {
    }

    public BookMakerMarket(String key, LocalDateTime lastUpdate, List<BookMakerMarketOutcome> outcomes) {
        this.key = key;
        this.lastUpdate = lastUpdate;
        this.outcomes = outcomes;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public List<BookMakerMarketOutcome> getOutcomes() {
        return outcomes;
    }

    public void setOutcomes(List<BookMakerMarketOutcome> outcomes) {
        this.outcomes = outcomes;
    }
}
