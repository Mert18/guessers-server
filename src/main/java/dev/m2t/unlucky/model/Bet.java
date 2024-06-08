package dev.m2t.unlucky.model;

public class Bet {
    private Event event;
    private EventOption option;

    public Bet() {

    }

    public Bet(Event event, EventOption option) {
        this.event = event;
        this.option = option;
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
}
