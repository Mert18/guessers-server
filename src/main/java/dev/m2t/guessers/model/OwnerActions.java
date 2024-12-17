package dev.m2t.guessers.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.m2t.guessers.model.enums.OwnerActionsEnum;
import jakarta.persistence.*;

import java.time.LocalDateTime;


@Entity
public class OwnerActions {
    @Id
    @GeneratedValue
    private Long id;

    private LocalDateTime date = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    private OwnerActionsEnum action;
    private String actionDescription;

    @ManyToOne
    @JsonIgnore
    private User actor;

    @ManyToOne
    @JsonIgnore
    private Room room;

    public OwnerActions() {
    }

    public OwnerActions(LocalDateTime date, OwnerActionsEnum action, String actionDescription, User actor, Room room) {
        this.date = date;
        this.action = action;
        this.actionDescription = actionDescription;
        this.actor = actor;
        this.room = room;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public OwnerActionsEnum getAction() {
        return action;
    }

    public void setAction(OwnerActionsEnum action) {
        this.action = action;
    }

    public String getActionDescription() {
        return actionDescription;
    }

    public void setActionDescription(String actionDescription) {
        this.actionDescription = actionDescription;
    }

    public User getActor() {
        return actor;
    }

    public void setActor(User actor) {
        this.actor = actor;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }
}
