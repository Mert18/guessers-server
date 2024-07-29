package dev.m2t.unlucky.dto.request;

import dev.m2t.unlucky.model.EventCase;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public class CreateEventRequest {
    @NotNull(message = "Name cannot be null")
    @Size(min = 3, message = "Name must be at least 3 characters long")
    private String name;

    @NotNull(message = "Description cannot be null")
    @Size(min = 3, message = "Description must be at least 3 characters long")
    private String description;

    @NotNull(message = "Options cannot be null")
    @Size(min = 2, message = "Options must be at least 2")
    private List<EventCase> eventCases;

    public CreateEventRequest() {
    }

    public CreateEventRequest(String name, String description, List<EventCase> eventCases) {
        this.name = name;
        this.description = description;
        this.eventCases = eventCases;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<EventCase> getEventCases() {
        return eventCases;
    }

    public void setEventCases(List<EventCase> eventCases) {
        this.eventCases = eventCases;
    }
}
