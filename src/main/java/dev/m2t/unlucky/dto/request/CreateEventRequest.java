package dev.m2t.unlucky.dto.request;

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
    private List<EventOption> options;

    public CreateEventRequest() {
    }

    public CreateEventRequest(String name, String description, List<EventOption> options) {
        this.name = name;
        this.description = description;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
