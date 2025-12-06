package dev.m2t.guessers.model;

import java.time.LocalDateTime;

public class PublicAnnouncement {
    private String id;
    private String type;
    private String title;
    private String message;
    private LocalDateTime timestamp;

    public PublicAnnouncement() {
        this.timestamp = LocalDateTime.now();
    }

    public PublicAnnouncement(String type, String title, String message) {
        this.type = type;
        this.title = title;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
