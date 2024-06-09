package dev.m2t.unlucky.dto.request;

import dev.m2t.unlucky.model.EventOption;

import java.util.List;

public class CreateEventRequest {
    private String name;
    private String description;
    private String roomId;
    private List<EventOption> options;

    public CreateEventRequest() {
    }

    public CreateEventRequest(String name, String description, String roomId, List<EventOption> options) {
        this.name = name;
        this.description = description;
        this.roomId = roomId;
        this.options = options;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<EventOption> getOptions() {
        return options;
    }

    public void setOptions(List<EventOption> options) {
        this.options = options;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
