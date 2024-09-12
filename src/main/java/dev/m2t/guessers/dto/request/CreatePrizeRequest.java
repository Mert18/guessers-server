package dev.m2t.guessers.dto.request;

public class CreatePrizeRequest {
    private String name;
    private String description;
    private Double value;
    private Long roomId;

    public CreatePrizeRequest() {
    }

    public CreatePrizeRequest(String name, String description, Double value, Long roomId) {
        this.name = name;
        this.description = description;
        this.value = value;
        this.roomId = roomId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Double getValue() {
        return value;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }
}
