package dev.m2t.unlucky.model.enums;

public enum SingleGuessStatusEnum {
    IN_PROGRESS,
    WON,
    LOST,
    CANCELLED;

    SingleGuessStatusEnum() {
    }

    public static SingleGuessStatusEnum fromString(String status) {
        return valueOf(status);
    }

    public static SingleGuessStatusEnum fromValue(String status) {
        return valueOf(status);
    }

    public String toString() {
        return this.name();
    }
}
