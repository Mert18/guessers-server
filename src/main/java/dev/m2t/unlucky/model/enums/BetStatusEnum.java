package dev.m2t.unlucky.model.enums;

public enum BetStatusEnum {
    PENDING,
    WON,
    LOST;

    public static BetStatusEnum fromString(String status) {
        switch (status) {
            case "PENDING":
                return PENDING;
            case "WON":
                return WON;
            case "LOST":
                return LOST;
            default:
                return null;
        }
    }

    public static String toString(BetStatusEnum status) {
        switch (status) {
            case PENDING:
                return "PENDING";
            case WON:
                return "WON";
            case LOST:
                return "LOST";
            default:
                return null;
        }
    }
}
