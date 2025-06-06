package dev.m2t.guessers.model;

import dev.m2t.guessers.dto.request.JoinPickOneAndHopeRoomRequest;
import dev.m2t.guessers.model.enums.PickAndHopeGameStatusEnum;

import java.time.Instant;
import java.util.List;

public class GameRoom {
    private final String id;
    private final List<JoinPickOneAndHopeRoomRequest> players;
    private final Instant createdAt;
    private PickAndHopeGameStatusEnum status; // WAITING, STARTED, etc.

    public GameRoom(String id, List<JoinPickOneAndHopeRoomRequest> players) {
        this.id = id;
        this.players = players;
        this.createdAt = Instant.now();
        this.status = PickAndHopeGameStatusEnum.WAITING;
    }

    public String getId() {
        return id;
    }

    public List<JoinPickOneAndHopeRoomRequest> getPlayers() {
        return players;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public PickAndHopeGameStatusEnum getStatus() {
        return status;
    }

    public void setStatus(PickAndHopeGameStatusEnum status) {
        this.status = status;
    }
}