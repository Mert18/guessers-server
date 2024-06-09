package dev.m2t.unlucky.dto.request;

import org.springframework.data.domain.Pageable;

public class ListRoomBetSlipsRequest {
    private String roomId;
    private Pageable pageable;

    public ListRoomBetSlipsRequest() {
    }

    public ListRoomBetSlipsRequest(String roomId, Pageable pageable) {
        this.roomId = roomId;
        this.pageable = pageable;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public Pageable getPageable() {
        return pageable;
    }

    public void setPageable(Pageable pageable) {
        this.pageable = pageable;
    }
}
