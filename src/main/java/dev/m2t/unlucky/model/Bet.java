package dev.m2t.unlucky.model;

public class Bet {
    private Event event;
    private EventOption option;
    private User user;
    private Double odds;

    public Bet() {

    }

    public Bet(Event event, EventOption option, User user, Double odds) {
        this.event = event;
        this.option = option;
        this.user = user;
        this.odds = odds;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public EventOption getOption() {
        return option;
    }

    public void setOption(EventOption option) {
        this.option = option;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Double getOdds() {
        return odds;
    }

    public void setOdds(Double odds) {
        this.odds = odds;
    }
}
