package dev.m2t.unlucky.service;

import dev.m2t.unlucky.dto.BaseResponse;
import dev.m2t.unlucky.dto.request.CreateEventRequest;
import dev.m2t.unlucky.dto.request.FinalizeEventRequest;
import dev.m2t.unlucky.exception.EventNotExistsException;
import dev.m2t.unlucky.exception.RoomNotExistsException;
import dev.m2t.unlucky.exception.UnauthorizedException;
import dev.m2t.unlucky.model.*;
import dev.m2t.unlucky.model.enums.EventGuessOptionCaseStatusEnum;
import dev.m2t.unlucky.model.enums.EventStatusEnum;
import dev.m2t.unlucky.repository.EventPagingRepository;
import dev.m2t.unlucky.repository.EventRepository;
import dev.m2t.unlucky.repository.RoomRepository;
import dev.m2t.unlucky.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {
    private final RoomRepository roomRepository;
    private final EventPagingRepository eventPagingRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private static final Logger logger = LoggerFactory.getLogger(EventService.class);

    public EventService(RoomRepository roomRepository, EventPagingRepository eventPagingRepository, UserRepository userRepository, EventRepository eventRepository) {
        this.roomRepository = roomRepository;
        this.eventPagingRepository = eventPagingRepository;
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
    }

    public BaseResponse getEvent(Long eventId, String username) {
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new EventNotExistsException("Event with id " + eventId + " does not exist."));
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UnauthorizedException("User with username " + username + " does not exist."));
        if(event.getRoom().getRoomUsers().stream().noneMatch(roomUser -> roomUser.getUser().equals(user))) {
            throw new UnauthorizedException("You are not one of the members of this room. Only the members can get events.");
        }

        return new BaseResponse("Event fetched successfully.", true, false, event);
    }

    public BaseResponse createEvent(CreateEventRequest createEventRequest, String username, Long roomId) {
        logger.info("Create event request received for room: {}", roomId);
        Room room = roomRepository.findById(roomId).orElseThrow(() -> new RoomNotExistsException("Room with id " + roomId + " does not exist."));
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UnauthorizedException("User with username " + username + " does not exist."));

        if (!room.getOwner().equals(user)) {
            logger.debug("User {} is not the owner of the room.", username);
            throw new UnauthorizedException("You are not the owner of this room. Only the owner can create events.");
        }

        Event event = new Event();
        event.setRoom(room);
        event.setName(createEventRequest.getName());
        event.setDescription(createEventRequest.getDescription());
        event.setStatus(EventStatusEnum.IN_PROGRESS);

        for (EventGuessOption eventGuessOption : createEventRequest.getEventGuessOptions()) {
            eventGuessOption.setEvent(event);
            event.getEventGuessOptions().add(eventGuessOption);
            for (EventGuessOptionCase eventGuessOptionCase : eventGuessOption.getEventGuessOptionCases()) {
                eventGuessOptionCase.setEventGuessOption(eventGuessOption);
            }
        }


        Event savedEvent = eventRepository.save(event);
        logger.info("Event created successfully for room: {}", roomId);
        return new BaseResponse("Event created successfully.", true, true, savedEvent);
    }

    // Lists events that are not started yet for a given room
    public BaseResponse listEvents(Long roomId, String username, Pageable pageable) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UnauthorizedException("User with username " + username + " does not exist."));
        Room room = roomRepository.findById(roomId).orElseThrow(() -> new RoomNotExistsException("Room with id " + roomId + " does not exist."));

        if (room.getRoomUsers().stream().noneMatch(roomUser -> roomUser.getUser().equals(user))) {
            throw new UnauthorizedException("You are not one of the members of this room. Only the members can list events.");
        }

        Page<Event> events = eventPagingRepository.findByStatusInAndRoom(List.of(EventStatusEnum.NOT_STARTED, EventStatusEnum.IN_PROGRESS), room, pageable);

        return new BaseResponse("Events fetched successfully.", true, false, events);
    }

    public BaseResponse listCompletedEvents(Long roomId, String username, Pageable pageable) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UnauthorizedException("User with username " + username + " does not exist."));
        Room room = roomRepository.findById(roomId).orElseThrow(() -> new RoomNotExistsException("Room with id " + roomId + " does not exist."));

        if (room.getRoomUsers().stream().noneMatch(roomUser -> roomUser.getUser().equals(user))) {
            throw new UnauthorizedException("You are not one of the members of this room. Only the members can list events.");
        }

        Page<Event> events = eventPagingRepository.findByStatusInAndRoom(List.of(EventStatusEnum.FINISHED, EventStatusEnum.CANCELLED), room, pageable);

        return new BaseResponse("Events fetched successfully.", true, false, events);
    }

    public BaseResponse finalizeEvent(FinalizeEventRequest finalizeEventRequest, String username, Long eventId) {
        logger.info("Finalize event request received for event: {}", eventId);
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new EventNotExistsException("Event with id " + eventId + " does not exist."));

        event.getEventGuessOptions().forEach(eventGuessOption -> {
            eventGuessOption.getEventGuessOptionCases().forEach(eventGuessOptionCase -> {
                if (finalizeEventRequest.getWinnerEventGuessOptionCases().contains(eventGuessOptionCase.getId())) {
                    eventGuessOptionCase.setStatus(EventGuessOptionCaseStatusEnum.WON);
                }else {
                    eventGuessOptionCase.setStatus(EventGuessOptionCaseStatusEnum.LOST);
                }
            });
        });

        event.setStatus(EventStatusEnum.FINISHED);
        Event savedEvent = eventRepository.save(event);
        logger.info("Event finalized successfully for event: {}", eventId);
        return new BaseResponse("Event finalized successfully.", true, true, savedEvent);
    }
}
