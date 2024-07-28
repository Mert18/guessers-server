package dev.m2t.unlucky.model;

public class Guess {
    private Event event;
    private EventCase eventCase;
    private EventCaseOption eventCaseOption;

    public Guess () {

    }

    public Guess (Event event, EventCase eventCase, EventCaseOption eventCaseOption) {
        this.event = event;
        this.eventCase = eventCase;
        this.eventCaseOption = eventCaseOption;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public EventCase getEventCase() {
        return eventCase;
    }

    public void setEventCase(EventCase eventCase) {
        this.eventCase = eventCase;
    }

    public EventCaseOption getEventCaseOption() {
        return eventCaseOption;
    }

    public void setEventCaseOption(EventCaseOption eventCaseOption) {
        this.eventCaseOption = eventCaseOption;
    }
}
