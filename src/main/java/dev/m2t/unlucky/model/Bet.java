package dev.m2t.unlucky.model;

import dev.m2t.unlucky.model.enums.BetStatusEnum;

public class Bet {
    private Event event;
    private EventOption option;
    private BetStatusEnum status = BetStatusEnum.PENDING;

    public Bet() {

    }

    public Bet(Event event, EventOption option, BetStatusEnum status) {
        this.event = event;
        this.option = option;
        this.status = status;
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

    public BetStatusEnum getStatus() {
        return status;
    }

    public void setStatus(BetStatusEnum status) {
        this.status = status;
    }
}
