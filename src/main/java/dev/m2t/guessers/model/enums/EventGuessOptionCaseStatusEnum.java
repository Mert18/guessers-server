package dev.m2t.guessers.model.enums;

public enum EventGuessOptionCaseStatusEnum {
    IN_PROGRESS,
    WON,
    LOST,
    CANCELLED;

    EventGuessOptionCaseStatusEnum() {
    }

    public static EventGuessOptionCaseStatusEnum fromString(String status) {
        return valueOf(status);
    }

    public static EventGuessOptionCaseStatusEnum fromValue(String status) {
        return valueOf(status);
    }

    public String toString() {
        return this.name();
    }

}
