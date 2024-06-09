package dev.m2t.unlucky.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CreateRoomRequest {
    @NotNull(message = "Name cannot be null")
    private String name;
    @NotNull(message = "Description cannot be null")
    @Size(max = 255, message = "Description cannot be more than 255 characters")
    private String description;

    public CreateRoomRequest() {
    }

    public CreateRoomRequest(String name, String description) {
        this.name = name;
        this.description = description;
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
}
