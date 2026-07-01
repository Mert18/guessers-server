package dev.m2t.guessers.model.enums;

public enum EventStatusEnum {
    NOT_STARTED,
    IN_PROGRESS,
    STARTED,
    FINISHED,
    CANCELLED;

    public static EventStatusEnum fromString(String status) {
        return switch (status) {
            case "NOT_STARTED" -> NOT_STARTED;
            case "IN_PROGRESS" -> IN_PROGRESS;
            case "STARTED" -> STARTED;
            case "FINISHED" -> FINISHED;
            case "CANCELLED" -> CANCELLED;
            default -> null;
        };
    }

    public static String toString(EventStatusEnum status) {
        return switch (status) {
            case NOT_STARTED -> "NOT_STARTED";
            case IN_PROGRESS -> "IN_PROGRESS";
            case STARTED -> "STARTED";
            case FINISHED -> "FINISHED";
            case CANCELLED -> "CANCELLED";
        };
    }
}
