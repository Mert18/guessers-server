package dev.m2t.unlucky.dto.request;

import dev.m2t.unlucky.model.EventOption;

import java.time.LocalDateTime;
import java.util.List;

public class CreateEventRequest {
    private String name;
    private LocalDateTime date;
    private List<EventOption> options;

    public CreateEventRequest() {
    }

    public CreateEventRequest(String name, LocalDateTime date, List<EventOption> options) {
        this.name = name;
        this.date = date;
        this.options = options;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public List<EventOption> getOptions() {
        return options;
    }

    public void setOptions(List<EventOption> options) {
        this.options = options;
    }
}
