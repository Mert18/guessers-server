package dev.m2t.unlucky.model;

import dev.m2t.unlucky.model.enums.EventStatusEnum;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "events")
public class Event {
    @Id
    private String id;
    private String name;
    private LocalDateTime date;
    private List<EventOption> options;
    private EventStatusEnum status;

    public Event() {

    }

    public Event(String name, LocalDateTime date, List<EventOption> options, EventStatusEnum status) {
        this.name = name;
        this.date = date;
        this.options = options;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public EventStatusEnum getStatus() {
        return status;
    }

    public void setStatus(EventStatusEnum status) {
        this.status = status;
    }
}
