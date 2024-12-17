package dev.m2t.guessers.service;

import dev.m2t.guessers.dto.BaseResponse;
import dev.m2t.guessers.dto.request.CreateEventRequest;
import dev.m2t.guessers.dto.request.FinalizeEventRequest;
import dev.m2t.guessers.exception.ResourceNotFoundException;
import dev.m2t.guessers.exception.UnauthorizedException;
import dev.m2t.guessers.mapper.EventReadyEventMapper;
import dev.m2t.guessers.model.*;
import dev.m2t.guessers.model.enums.EventGuessOptionCaseStatusEnum;
import dev.m2t.guessers.model.enums.EventStatusEnum;
import dev.m2t.guessers.repository.*;
import dev.m2t.guessers.model.enums.UserActionsEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EventService {
    private final RoomRepository roomRepository;
    private final EventPagingRepository eventPagingRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final ReadyEventRepository readyEventRepository;
    private final EventReadyEventMapper eventReadyEventMapper;
    private final UserActionService userActionService;
    private static final Logger logger = LoggerFactory.getLogger(EventService.class);

    public EventService(RoomRepository roomRepository, EventPagingRepository eventPagingRepository, UserRepository userRepository, EventRepository eventRepository, ReadyEventRepository readyEventRepository, EventReadyEventMapper eventReadyEventMapper, UserActionService userActionService) {
        this.roomRepository = roomRepository;
        this.eventPagingRepository = eventPagingRepository;
        this.userRepository = userRepository;
        this.eventRepository = eventRepository;
        this.readyEventRepository = readyEventRepository;
        this.eventReadyEventMapper = eventReadyEventMapper;
        this.userActionService = userActionService;
    }

    public BaseResponse<Event> getEvent(Long eventId, String username) {
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new ResourceNotFoundException("Event", "eventId", eventId));
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UnauthorizedException("User with username " + username + " does not exist."));
        if(event.getRoom().getRoomUsers().stream().noneMatch(roomUser -> roomUser.getUser().equals(user))) {
            throw new UnauthorizedException("You are not one of the members of this room. Only the members can get events.");
        }

        return new BaseResponse<>("Event fetched successfully.", true, false, event);
    }

    public BaseResponse<Event> createEvent(CreateEventRequest createEventRequest, String username, Long roomId) {
        logger.info("Create event request received for room: {}", roomId);
        Room room = roomRepository.findById(roomId).orElseThrow(() -> new ResourceNotFoundException("Room", "roomId", roomId));
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
        userActionService.saveUserAction(UserActionsEnum.CREATE_EVENT, "Event created with name: " + event.getName(), user, room);
        logger.info("Event created successfully for room: {}", roomId);
        return new BaseResponse<>("Event created successfully.", true, true, savedEvent);
    }

    // Lists events that are not started yet for a given room
    public BaseResponse<Page<Event>> listEvents(Long roomId, String username, Pageable pageable) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UnauthorizedException("User with username " + username + " does not exist."));
        Room room = roomRepository.findById(roomId).orElseThrow(() -> new ResourceNotFoundException("Room", "roomId", roomId));

        if (room.getRoomUsers().stream().noneMatch(roomUser -> roomUser.getUser().equals(user))) {
            throw new UnauthorizedException("You are not one of the members of this room. Only the members can list events.");
        }

        Page<Event> events = eventPagingRepository.findByStatusInAndRoom(List.of(EventStatusEnum.NOT_STARTED, EventStatusEnum.IN_PROGRESS, EventStatusEnum.STARTED), room, pageable);

        return new BaseResponse<>("Events fetched successfully.", true, false, events);
    }

    public BaseResponse<Page<Event>> listCompletedEvents(Long roomId, String username, Pageable pageable) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UnauthorizedException("User with username " + username + " does not exist."));
        Room room = roomRepository.findById(roomId).orElseThrow(() -> new ResourceNotFoundException("Room", "roomId", roomId));

        if (room.getRoomUsers().stream().noneMatch(roomUser -> roomUser.getUser().equals(user))) {
            throw new UnauthorizedException("You are not one of the members of this room. Only the members can list events.");
        }

        Page<Event> events = eventPagingRepository.findByStatusInAndRoom(List.of(EventStatusEnum.FINISHED, EventStatusEnum.CANCELLED), room, pageable);

        return new BaseResponse<>("Events fetched successfully.", true, false, events);
    }

    public BaseResponse<Event> finalizeEvent(FinalizeEventRequest finalizeEventRequest, Long roomId, String username, Long eventId) {
        logger.info("Finalize event request received for event: {}", eventId);
        Room room = roomRepository.findById(roomId).orElseThrow(() -> new ResourceNotFoundException("Room", "roomId", roomId));
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UnauthorizedException("User with username " + username + " does not exist."));

        if (!room.getOwner().equals(user)) {
            logger.debug("User {} is not the owner of the room.", username);
            throw new UnauthorizedException("You are not the owner of this room. Only the owner can create events.");
        }

        Event event = eventRepository.findById(eventId).orElseThrow(() -> new ResourceNotFoundException("Event", "eventId", eventId));
        if(event.getStatus() != EventStatusEnum.STARTED) {
            return new BaseResponse<>("Event could not be finalized.", false, true);
        }

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
        userActionService.saveUserAction(UserActionsEnum.END_EVENT, "Event ended with name: " + event.getName(), user, room);
        logger.info("Event finalized successfully for event: {}", eventId);
        return new BaseResponse<>("Event finalized successfully.", true, true, savedEvent);
    }

    public BaseResponse<Event> startEvent(Long eventId, Long roomId, String username) {
        logger.info("Start event request received for event: {}", eventId);
        Room room = roomRepository.findById(roomId).orElseThrow(() -> new ResourceNotFoundException("Room", "roomId", roomId));
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UnauthorizedException("User with username " + username + " does not exist."));

        if (!room.getOwner().equals(user)) {
            logger.debug("User {} is not the owner of the room.", username);
            throw new UnauthorizedException("You are not the owner of this room. Only the owner can create events.");
        }
        Event event = eventRepository.findById(eventId).orElseThrow(() -> new ResourceNotFoundException("Event", "eventId", eventId));

        event.setStatus(EventStatusEnum.STARTED);
        Event savedEvent = eventRepository.save(event);
        userActionService.saveUserAction(UserActionsEnum.START_EVENT, "Event started with name: " + event.getName(), user, room);
        logger.info("Event started successfully for event: {}", eventId);
        return new BaseResponse<>("Event started successfully.", true, true, savedEvent);
    }

    public BaseResponse createEventFromReadyEvent(Long roomId, List<String> readyEventIds, String username) {
        Room room = roomRepository.findById(roomId).orElseThrow(() -> new ResourceNotFoundException("Room", "roomId", roomId));
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UnauthorizedException("User with username " + username + " does not exist."));

        if (!room.getOwner().equals(user)) {
            logger.debug("User {} is not the owner of the room.", username);
            throw new UnauthorizedException("You are not the owner of this room. Only the owner can create events.");
        }

        List<Event> eventsToCreate = new ArrayList<>();
        for(String readyEventId : readyEventIds) {
            ReadyEvent readyEvent = readyEventRepository.findById(readyEventId).orElseThrow(() -> new ResourceNotFoundException("Ready Event", "id", readyEventId));

            Event event = eventReadyEventMapper.toEvent(readyEvent);
            event.setRoom(room);

            eventsToCreate.add(event);
        }


        eventRepository.saveAll(eventsToCreate);

        return new BaseResponse<>("Event created successfully from ready event.", true, true);
    }
}
