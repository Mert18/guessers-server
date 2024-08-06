package dev.m2t.unlucky.dto;

public class SingleGuess {
    private Long eventId;
    private Long eventGuessOptionId;
    private Long eventGuessOptionCaseId;

    public SingleGuess() {
    }

    public SingleGuess(Long eventId, Long eventGuessOptionId, Long eventGuessOptionCaseId) {
        this.eventId = eventId;
        this.eventGuessOptionId = eventGuessOptionId;
        this.eventGuessOptionCaseId = eventGuessOptionCaseId;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public Long getEventGuessOptionId() {
        return eventGuessOptionId;
    }

    public void setEventGuessOptionId(Long eventGuessOptionId) {
        this.eventGuessOptionId = eventGuessOptionId;
    }

    public Long getEventGuessOptionCaseId() {
        return eventGuessOptionCaseId;
    }

    public void setEventGuessOptionCaseId(Long eventGuessOptionCaseId) {
        this.eventGuessOptionCaseId = eventGuessOptionCaseId;
    }
}
