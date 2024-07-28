package dev.m2t.unlucky.dto.response;

import dev.m2t.unlucky.model.Room;
import org.springframework.data.domain.Page;

import java.util.ArrayList;

public class ListPublicRoomsResponse {
    Page<Room> rooms;

    public ListPublicRoomsResponse() {
    }

    public ListPublicRoomsResponse(Page<Room> rooms) {
        this.rooms = rooms;
    }

    public Page<Room> getRooms() {
        return rooms;
    }

    public void setRooms(Page<Room> rooms) {
        this.rooms = rooms;
    }
}
