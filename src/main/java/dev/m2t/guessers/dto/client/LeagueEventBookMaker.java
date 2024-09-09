package dev.m2t.guessers.dto.client;

import java.util.List;

public class LeagueEventBookMaker {
    private String key;
    private List<BookMakerMarket> markets;

    public LeagueEventBookMaker() {
    }

    public LeagueEventBookMaker(String key, List<BookMakerMarket> markets) {
        this.key = key;
        this.markets = markets;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public List<BookMakerMarket> getMarkets() {
        return markets;
    }

    public void setMarkets(List<BookMakerMarket> markets) {
        this.markets = markets;
    }
}
