package dev.m2t.guessers.dto.client;

public class BookMakerMarketOutcome {
    private String name;
    private Double price;

    public BookMakerMarketOutcome() {
    }

    public BookMakerMarketOutcome(String key, Double price) {
        this.name = key;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
