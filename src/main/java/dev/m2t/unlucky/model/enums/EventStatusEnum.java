package dev.m2t.unlucky.model.enums;

public enum EventStatusEnum {
    NOT_STARTED,
    IN_PROGRESS,
    STARTED,
    FINISHED,
    CANCELLED;

    public static EventStatusEnum fromString(String status) {
        switch (status) {
            case "NOT_STARTED":
                return NOT_STARTED;
            case "IN_PROGRESS":
                return IN_PROGRESS;
            case "STARTED":
                return STARTED;
            case "FINISHED":
                return FINISHED;
            case "CANCELLED":
                return CANCELLED;
            default:
                return null;
        }
    }

    public static String toString(EventStatusEnum status) {
        switch (status) {
            case NOT_STARTED:
                return "NOT_STARTED";
            case IN_PROGRESS:
                return "IN_PROGRESS";
            case STARTED:
                return "STARTED";
            case FINISHED:
                return "FINISHED";
            case CANCELLED:
                return "CANCELLED";
            default:
                return null;
        }
    }
}
