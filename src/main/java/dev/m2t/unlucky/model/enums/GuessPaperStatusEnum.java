package dev.m2t.unlucky.model.enums;

public enum GuessPaperStatusEnum {
    IN_PROGRESS,
    WON,
    LOST,
    CANCELLED;

    GuessPaperStatusEnum() {
    }

    public static GuessPaperStatusEnum fromString(String status) {
        return valueOf(status);
    }

    public static GuessPaperStatusEnum fromValue(String status) {
        return valueOf(status);
    }

    public String toString() {
        return this.name();
    }
}
