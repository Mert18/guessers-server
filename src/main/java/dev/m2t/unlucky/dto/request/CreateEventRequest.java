package dev.m2t.unlucky.dto.request;

import dev.m2t.unlucky.model.EventGuessOption;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public class CreateEventRequest {
    @NotNull(message = "Name cannot be null")
    @Size(min = 3, message = "Name must be at least 3 characters long")
    private String name;

    private String description;

    @NotNull(message = "Options cannot be null")
    private List<EventGuessOption> eventGuessOptions;

    public CreateEventRequest() {
    }

    public CreateEventRequest(String name, String description, List<EventGuessOption> guessOptions) {
        this.name = name;
        this.description = description;
        this.eventGuessOptions = guessOptions;
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

    public List<EventGuessOption> getEventGuessOptions() {
        return eventGuessOptions;
    }

    public void setEventGuessOptions(List<EventGuessOption> eventGuessOptions) {
        this.eventGuessOptions = eventGuessOptions;
    }
}
