package dev.m2t.unlucky.dto.response;

import dev.m2t.unlucky.model.Room;
import dev.m2t.unlucky.model.User;

import java.util.List;
import java.util.Map;

public class RoomMetadataResponse {
    private Room room;
    private Boolean owner;
    private List<Map.Entry<String, Integer>> rankPredictions;
    private List<User> riches;

    public RoomMetadataResponse(Room room, Boolean owner, List<Map.Entry<String, Integer>> rankPredictions, List<User> riches) {
        this.room = room;
        this.owner = owner;
        this.rankPredictions = rankPredictions;
        this.riches = riches;
    }

    public RoomMetadataResponse() {

    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Boolean getOwner() {
        return owner;
    }

    public void setOwner(Boolean owner) {
        this.owner = owner;
    }

    public List<Map.Entry<String, Integer>> getRankPredictions() {
        return rankPredictions;
    }

    public void setRankPredictions(List<Map.Entry<String, Integer>> rankPredictions) {
        this.rankPredictions = rankPredictions;
    }

    public List<User> getRiches() {
        return riches;
    }

    public void setRiches(List<User> riches) {
        this.riches = riches;
    }
}
