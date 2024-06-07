package dev.m2t.unlucky.model.enums;

public enum SlipStatusEnum {
    IN_PROGRESS,
    COMPLETED,
    CANCELLED;

    public static SlipStatusEnum fromString(String status) {
        switch (status) {
            case "IN_PROGRESS":
                return IN_PROGRESS;
            case "COMPLETED":
                return COMPLETED;
            case "CANCELLED":
                return CANCELLED;
            default:
                return null;
        }
    }

    public static String toString(SlipStatusEnum status) {
        switch (status) {
            case IN_PROGRESS:
                return "IN_PROGRESS";
            case COMPLETED:
                return "COMPLETED";
            case CANCELLED:
                return "CANCELLED";
            default:
                return null;
        }
    }
}
