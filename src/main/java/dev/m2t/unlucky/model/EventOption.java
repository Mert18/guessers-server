package dev.m2t.unlucky.model;

public class EventOption {
    private String name;
    private Double odds;
    private Integer optionNumber;

    public EventOption() {
    }

    public EventOption(String name, Double odds, Integer optionNumber) {
        this.name = name;
        this.odds = odds;
        this.optionNumber = optionNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getOdds() {
        return odds;
    }

    public void setOdds(Double odds) {
        this.odds = odds;
    }

    public Integer getOptionNumber() {
        return optionNumber;
    }

    public void setOptionNumber(Integer optionNumber) {
        this.optionNumber = optionNumber;
    }
}
