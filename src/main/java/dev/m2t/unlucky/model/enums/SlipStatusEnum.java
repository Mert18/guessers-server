package dev.m2t.unlucky.model.enums;

public enum SlipStatusEnum {
    IN_PROGRESS,
    WON,
    LOST,
    CANCELLED;

    SlipStatusEnum() {
    }

    public static SlipStatusEnum fromString(String status) {
        return valueOf(status);
    }

    public static SlipStatusEnum fromValue(String status) {
        return valueOf(status);
    }

    public String toValue() {
        return this.name();
    }

    public String toString() {
        return this.name();
    }
}
