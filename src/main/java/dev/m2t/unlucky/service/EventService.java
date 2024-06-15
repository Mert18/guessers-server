package dev.m2t.unlucky.service;

import dev.m2t.unlucky.dto.BaseResponse;
import dev.m2t.unlucky.dto.request.CreateEventRequest;
import dev.m2t.unlucky.dto.request.FinalizeEventRequest;
import dev.m2t.unlucky.exception.EventNotExistsException;
import dev.m2t.unlucky.exception.RoomNotExistsException;
import dev.m2t.unlucky.exception.UnauthorizedException;
import dev.m2t.unlucky.model.*;
import dev.m2t.unlucky.model.enums.BetStatusEnum;
import dev.m2t.unlucky.model.enums.EventStatusEnum;
import dev.m2t.unlucky.model.enums.SlipStatusEnum;
import dev.m2t.unlucky.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventService {
    private final EventRepository eventRepository;
    private final EventPagingRepository eventPagingRepository;
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;
    private final BetSlipRepository betSlipRepository;

    private static final Logger logger = LoggerFactory.getLogger(EventService.class);

    public EventService(EventRepository eventRepository, EventPagingRepository eventPagingRepository, RoomRepository roomRepository, UserRepository userRepository, BetSlipRepository betSlipRepository) {
        this.eventRepository = eventRepository;
        this.eventPagingRepository = eventPagingRepository;
        this.roomRepository = roomRepository;
        this.userRepository = userRepository;
        this.betSlipRepository = betSlipRepository;
    }

    public BaseResponse createEvent(CreateEventRequest createEventRequest, String username, String roomId) {
        Optional<Room> room = roomRepository.findById(roomId);
        if (room.isEmpty()) {
            throw new RoomNotExistsException("Room with id " + roomId + " does not exist.");
        } else if (!room.get().getOwner().equals(username)) {
            throw new UnauthorizedException("You are not the owner of this room. Only the owner can create events.");
        } else {
            // Set option numbers for options
            for (int i = 0; i < createEventRequest.getOptions().size(); i++) {
                createEventRequest.getOptions().get(i).setOptionNumber(i);
            }
            Event event = new Event(createEventRequest.getName(), createEventRequest.getDescription(), roomId, createEventRequest.getOptions(), EventStatusEnum.NOT_STARTED);
            Event savedEvent = eventRepository.save(event);
            return new BaseResponse("Event created successfully.", true, true, savedEvent);
        }
    }

    // Lists events that are not started yet for a given room
    public BaseResponse listEvents(String roomId, String username, Pageable pageable) {
        Optional<Room> room = roomRepository.findById(roomId);
        if (room.isEmpty()) {
            throw new RoomNotExistsException("Room with id " + roomId + " does not exist.");
        } else if (!room.get().getUsers().contains(username)) {
            throw new UnauthorizedException("You are not one of the members of this room. Only the members can list events.");
        } else {
            Page<Event> events = eventPagingRepository.findByStatusAndRoomId(EventStatusEnum.NOT_STARTED, roomId, pageable);

            return new BaseResponse("Events fetched successfully.", true, false, events);
        }
    }

    public BaseResponse finalizeEvent(FinalizeEventRequest finalizeEventRequest, String username) {
        Optional<Event> event = eventRepository.findById(finalizeEventRequest.getEventId());
        Optional<Room> room = roomRepository.findById(finalizeEventRequest.getRoomId());
        logger.info("Finalize event request received for event: {}", event.get().getName());
        if (event.isEmpty()) {
            logger.info("Event with id {} does not exist.", finalizeEventRequest.getEventId());
            throw new EventNotExistsException("Event with id " + finalizeEventRequest.getEventId() + " does not exist.");
        } else if(room.isEmpty()) {
            logger.info("Room with id {} does not exist.", finalizeEventRequest.getRoomId());
            throw new RoomNotExistsException("Room with id " + finalizeEventRequest.getRoomId() + " does not exist.");
        } else if((!room.get().getOwner().equals(username))) {
            logger.info("User {} is not the owner of the room.", username);
            throw new UnauthorizedException("You are not the owner of this room. Only the owner can finalize events.");
        } else {
            logger.info("Finalizing event: {}", event.get().getName());
            List<BetSlip> betSlips = betSlipRepository.findByRoomIdAndStatus(room.get().getId(), SlipStatusEnum.IN_PROGRESS);
            for(BetSlip betSlip : betSlips) {
                for(Bet bet: betSlip.getBets()) {
                    if(bet.getEvent().getId().equals(finalizeEventRequest.getEventId())) {
                        if(finalizeEventRequest.getWinnerOptionNumbers().contains(bet.getOption().getOptionNumber()) &&
                            bet.getStatus().equals(BetStatusEnum.PENDING)
                        ) {
                            bet.setStatus(BetStatusEnum.WON);
                        }else {
                            bet.setStatus(BetStatusEnum.LOST);
                        }
                    }
                }
                betSlipRepository.save(betSlip);
            }

            Event eventToFinalize = event.get();
            eventToFinalize.setStatus(EventStatusEnum.FINISHED);
            Event savedEvent = eventRepository.save(eventToFinalize);
            return new BaseResponse("Event finalized successfully.", true, true, savedEvent);
        }
    }

    public BaseResponse getEvent(String eventId, String username) {
        Optional<Event> event = eventRepository.findById(eventId);
        if (event.isEmpty()) {
            throw new EventNotExistsException("Event with id " + eventId + " does not exist.");
        } else {
            Room room = roomRepository.findById(event.get().getRoomId()).orElse(null);
            if (room == null) {
                throw new RoomNotExistsException("Room with id " + event.get().getRoomId() + " does not exist.");
            } else if (!room.getUsers().contains(username)) {
                throw new UnauthorizedException("You are not one of the members of this room. Only the members can get events.");
            } else {
                return new BaseResponse("Event fetched successfully.", true, false, event.get());
            }
        }

    }
}
