package dev.m2t.guessers.model.enums;

public enum OwnerActionsEnum {
    CREATE_EVENT,
    START_EVENT,
    END_EVENT,
    INVITE,
    LEND_TOKEN;

    OwnerActionsEnum() {
    }

    public static OwnerActionsEnum fromString(String status) {
        return valueOf(status);
    }

    public static OwnerActionsEnum fromValue(String status) {
        return valueOf(status);
    }

    public String toString() {
        return this.name();
    }
}
