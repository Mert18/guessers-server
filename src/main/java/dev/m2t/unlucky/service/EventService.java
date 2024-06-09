package dev.m2t.unlucky.service;

import dev.m2t.unlucky.dto.BaseResponse;
import dev.m2t.unlucky.dto.request.CreateEventRequest;
import dev.m2t.unlucky.exception.RoomNotExistsException;
import dev.m2t.unlucky.exception.UnauthorizedException;
import dev.m2t.unlucky.model.Event;
import dev.m2t.unlucky.model.Room;
import dev.m2t.unlucky.model.enums.EventStatusEnum;
import dev.m2t.unlucky.repository.EventPagingRepository;
import dev.m2t.unlucky.repository.EventRepository;
import dev.m2t.unlucky.repository.RoomRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EventService {
    private final EventRepository eventRepository;
    private final EventPagingRepository eventPagingRepository;
    private final RoomRepository roomRepository;

    public EventService(EventRepository eventRepository, EventPagingRepository eventPagingRepository, RoomRepository roomRepository) {
        this.eventRepository = eventRepository;
        this.eventPagingRepository = eventPagingRepository;
        this.roomRepository = roomRepository;
    }

    public BaseResponse createEvent(CreateEventRequest createEventRequest, String username) {
        Optional<Room> room = roomRepository.findById(createEventRequest.getRoomId());
        if(room.isEmpty()) {
            throw new RoomNotExistsException("Room with id "+ createEventRequest.getRoomId() + " does not exist.");
        }else if (!room.get().getOwner().equals(username)){
            throw new UnauthorizedException("You are not the owner of this room. Only the owner can create events.");
        }else {
            // Set option numbers for options
            for(int i = 0; i< createEventRequest.getOptions().size(); i++) {
                createEventRequest.getOptions().get(i).setOptionNumber(i);
            }
            Event event = new Event(createEventRequest.getName(), createEventRequest.getDescription(), createEventRequest.getRoomId(), createEventRequest.getOptions(), EventStatusEnum.NOT_STARTED);
            Event savedEvent = eventRepository.save(event);
            return new BaseResponse("Event created successfully.", true, true, savedEvent);
        }
    }

    // Lists events that are not started yet for a given room
    public BaseResponse listEvents(String roomId, String username, Pageable pageable) {
        Optional<Room> room = roomRepository.findById(roomId);
        if(room.isEmpty()) {
            throw new RoomNotExistsException("Room with id "+ roomId + " does not exist.");
        }else if (!room.get().getUsers().contains(username)){
            throw new UnauthorizedException("You are not one of the members of this room. Only the members can list events.");
        }else {
            Page<Event> events = eventPagingRepository.findByStatusAndRoomId(EventStatusEnum.NOT_STARTED, roomId, pageable);

            return new BaseResponse("Events fetched successfully.", true, false, events);
        }
    }
}
