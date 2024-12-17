package dev.m2t.guessers.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.m2t.guessers.model.enums.UserActionsEnum;
import jakarta.persistence.*;

import java.time.LocalDateTime;


@Entity
public class UserActions {
    @Id
    @GeneratedValue
    private Long id;

    private LocalDateTime date = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    private UserActionsEnum action;
    private String actionDescription;

    @ManyToOne
    @JsonIgnore
    private User actor;

    @ManyToOne
    @JsonIgnore
    private Room room;

    public UserActions() {
    }

    public UserActions(LocalDateTime date, UserActionsEnum action, String actionDescription, User actor, Room room) {
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

    public UserActionsEnum getAction() {
        return action;
    }

    public void setAction(UserActionsEnum action) {
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
