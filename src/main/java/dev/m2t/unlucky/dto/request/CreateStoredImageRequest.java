package dev.m2t.unlucky.dto.request;

import java.time.LocalDate;

public class CreateStoredImageRequest {
    private String url;
    private String description;
    private LocalDate date;

    public CreateStoredImageRequest() {
    }

    public CreateStoredImageRequest(String url, String description, LocalDate date) {
        this.url = url;
        this.description = description;
        this.date = date;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
