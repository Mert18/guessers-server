package dev.m2t.guessers.dto.request;

import jakarta.validation.constraints.NotNull;

public class CreateRoomRequest {
    @NotNull(message = "Name cannot be null")
    private String name;

    private Boolean publico;
    private Boolean borderless;

    public CreateRoomRequest() {
    }

    public CreateRoomRequest(String name, Boolean publico, Boolean borderless) {
        this.name = name;
        this.publico = publico;
        this.borderless = borderless;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getPublico() {
        return publico;
    }

    public void setPublico(Boolean publico) {
        this.publico = publico;
    }

    public Boolean getBorderless() {
        return borderless;
    }

    public void setBorderless(Boolean borderless) {
        this.borderless = borderless;
    }
}
