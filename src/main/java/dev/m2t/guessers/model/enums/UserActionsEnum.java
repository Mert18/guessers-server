package dev.m2t.guessers.model.enums;

public enum UserActionsEnum {
    CREATE_EVENT,
    START_EVENT,
    END_EVENT,
    INVITE,
    LEND_TOKEN;

    UserActionsEnum() {
    }

    public static UserActionsEnum fromString(String status) {
        return valueOf(status);
    }

    public static UserActionsEnum fromValue(String status) {
        return valueOf(status);
    }

    public String toString() {
        return this.name();
    }
}
