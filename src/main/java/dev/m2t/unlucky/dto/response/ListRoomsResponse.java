package dev.m2t.unlucky.dto.response;

import dev.m2t.unlucky.model.Room;

import java.util.ArrayList;
import java.util.List;

public class ListRoomsResponse {
    private List<Room> rooms = new ArrayList<>();

    public ListRoomsResponse() {
    }

    public ListRoomsResponse(List<Room> rooms) {
        this.rooms = rooms;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }
}
