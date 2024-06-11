package dev.m2t.unlucky.dto.request;
import java.util.List;

public class FinalizeEventRequest {
    private String eventId;
    private List<Integer> winnerOptionNumbers;
    private String roomId;

    public FinalizeEventRequest() {
    }

    public FinalizeEventRequest(String eventId, List<Integer> winnerOptionNumbers, String roomId) {
        this.eventId = eventId;
        this.winnerOptionNumbers = winnerOptionNumbers;
        this.roomId = roomId;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public List<Integer> getWinnerOptionNumbers() {
        return winnerOptionNumbers;
    }

    public void setWinnerOptionNumbers(List<Integer> winnerOptionNumbers) {
        this.winnerOptionNumbers = winnerOptionNumbers;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }
}
