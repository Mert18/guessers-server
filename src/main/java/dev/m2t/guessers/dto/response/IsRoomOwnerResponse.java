package dev.m2t.guessers.dto.response;

public class IsRoomOwnerResponse {
    private boolean isOwner;

    public IsRoomOwnerResponse(boolean isOwner) {
        this.isOwner = isOwner;
    }

    public boolean isOwner() {
        return isOwner;
    }

    public void setOwner(boolean isOwner) {
        this.isOwner = isOwner;
    }
}
