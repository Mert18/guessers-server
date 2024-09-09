package dev.m2t.guessers.dto.response;

import dev.m2t.guessers.model.Room;
import org.springframework.data.domain.Page;

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
