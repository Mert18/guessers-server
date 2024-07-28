package dev.m2t.unlucky.model.enums;

public enum RoomInviteStatusEnum {
    PENDING,
    ACCEPTED,
    REJECTED;

    RoomInviteStatusEnum() {
    }

    public static RoomInviteStatusEnum fromString(String status) {
        return valueOf(status);
    }

    public static RoomInviteStatusEnum fromValue(String status) {
        return valueOf(status);
    }
}
