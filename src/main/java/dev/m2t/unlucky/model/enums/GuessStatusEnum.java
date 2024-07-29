package dev.m2t.unlucky.model.enums;

public enum GuessStatusEnum {
    IN_PROGRESS,
    WON,
    LOST,
    CANCELLED;

    GuessStatusEnum() {
    }

    public static GuessStatusEnum fromString(String status) {
        return valueOf(status);
    }

    public static GuessStatusEnum fromValue(String status) {
        return valueOf(status);
    }

    public String toString() {
        return this.name();
    }
}
