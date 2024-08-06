package dev.m2t.unlucky.service;

import dev.m2t.unlucky.dto.BaseResponse;
import dev.m2t.unlucky.dto.request.CreateEventRequest;
import dev.m2t.unlucky.exception.RoomNotExistsException;
import dev.m2t.unlucky.exception.UnauthorizedException;
import dev.m2t.unlucky.model.*;
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

//    private final BetSlipRepository betSlipRepository;


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

            logger.debug("Processing EventGuessOption: {}", eventGuessOption.getName());

            for (EventGuessOptionCase eventGuessOptionCase : eventGuessOption.getEventGuessOptionCases()) {
                eventGuessOptionCase.setEventGuessOption(eventGuessOption);
                logger.debug("Processing EventGuessOptionCase: {}", eventGuessOptionCase.getName());
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

//    public BaseResponse finalizeEvent(FinalizeEventRequest finalizeEventRequest, String username) {
//        Optional<Event> event = eventRepository.findById(finalizeEventRequest.getEventId());
//        Optional<Room> room = roomRepository.findById(finalizeEventRequest.getRoomId());
//        logger.info("Finalize event request received for event: {}", event.get().getName());
//        if (event.isEmpty()) {
//            logger.info("Event with id {} does not exist.", finalizeEventRequest.getEventId());
//            throw new EventNotExistsException("Event with id " + finalizeEventRequest.getEventId() + " does not exist.");
//        } else if(room.isEmpty()) {
//            logger.info("Room with id {} does not exist.", finalizeEventRequest.getRoomId());
//            throw new RoomNotExistsException("Room with id " + finalizeEventRequest.getRoomId() + " does not exist.");
//        } else if((!room.get().getOwner().equals(username))) {
//            logger.info("User {} is not the owner of the room.", username);
//            throw new UnauthorizedException("You are not the owner of this room. Only the owner can finalize events.");
//        } else {
//            logger.info("Finalizing event: {}", event.get().getName());
//            List<BetSlip> betSlips = betSlipRepository.findByRoomIdAndStatus(room.get().getId(), SlipStatusEnum.IN_PROGRESS);
//
//            evaluateBets(finalizeEventRequest, betSlips);
//
//            evaluateBetSlips(betSlips);
//
//            Event eventToFinalize = event.get();
//            eventToFinalize.setStatus(EventStatusEnum.FINISHED);
//            Event savedEvent = eventRepository.save(eventToFinalize);
//            return new BaseResponse("Event finalized successfully.", true, true, savedEvent);
//        }
//    }
//
//    private void evaluateBets(FinalizeEventRequest finalizeEventRequest, List<BetSlip> betSlips) {
//        for(BetSlip betSlip : betSlips) {
//            for(Bet bet: betSlip.getBets()) {
//                if(bet.getEvent().getId().equals(finalizeEventRequest.getEventId())) {
//                    if(finalizeEventRequest.getWinnerOptionNumbers().contains(bet.getOption().getOptionNumber()) &&
//                            bet.getStatus().equals(BetStatusEnum.PENDING)
//                    ) {
//                        bet.setStatus(BetStatusEnum.WON);
//                    }else {
//                        bet.setStatus(BetStatusEnum.LOST);
//                    }
//                }
//            }
//            betSlipRepository.save(betSlip);
//        }
//    }
//
//    private void evaluateBetSlips(List<BetSlip> betSlips) {
//        for (BetSlip betSlip : betSlips) {
//            boolean won = true;
//            // Check if any bet.status is PENDING
//            for (Bet bet : betSlip.getBets()) {
//                if (bet.getStatus().equals(BetStatusEnum.PENDING)) {
//                    betSlip.setStatus(SlipStatusEnum.IN_PROGRESS);
//                    won = false;
//                    break;
//                } else if (bet.getStatus().equals(BetStatusEnum.LOST)) {
//                    betSlip.setStatus(SlipStatusEnum.LOST);
//                    won = false;
//                    break;
//                }else if(bet.getStatus().equals(BetStatusEnum.WON)) {
//                    Room room = roomRepository.findById(betSlip.getRoomId()).orElse(null);
//                    if(room == null) {
//                        throw new RoomNotExistsException("Room with id " + betSlip.getRoomId() + " does not exist.");
//                    } else {
//                        room.getUserCorrectPredictions().put(betSlip.getUsername(), room.getUserCorrectPredictions().getOrDefault(betSlip.getUsername(), 0) + 1);
//
//                        roomRepository.save(room);
//                    }
//                }
//            }
//
//            if(!won && (betSlip.getStatus().equals(SlipStatusEnum.LOST) || !won && betSlip.getStatus().equals(SlipStatusEnum.IN_PROGRESS))) {
//                betSlipRepository.save(betSlip);
//                continue;
//            }else {
//                betSlip.setStatus(SlipStatusEnum.WON);
//                User user1 = userRepository.findByUsername(betSlip.getUsername());
//                user1.setBalance(user1.getBalance() + betSlip.getStakes() * betSlip.getTotalOdds());
//                userRepository.save(user1);
//            }
//            betSlipRepository.save(betSlip);
//        }
//    }
//    public BaseResponse getEvent(String eventId, String username) {
//        Optional<Event> event = eventRepository.findById(eventId);
//        if (event.isEmpty()) {
//            throw new EventNotExistsException("Event with id " + eventId + " does not exist.");
//        } else {
//            Room room = roomRepository.findById(event.get().getRoomId()).orElse(null);
//            if (room == null) {
//                throw new RoomNotExistsException("Room with id " + event.get().getRoomId() + " does not exist.");
//            } else if (!room.getUsers().contains(username)) {
//                throw new UnauthorizedException("You are not one of the members of this room. Only the members can get events.");
//            } else {
//                return new BaseResponse("Event fetched successfully.", true, false, event.get());
//            }
//        }
//
//    }
//
//    public BaseResponse startEvent(String eventId, String username) {
//        Optional<Event> event = eventRepository.findById(eventId);
//        if (event.isEmpty()) {
//            throw new EventNotExistsException("Event with id " + eventId + " does not exist.");
//        } else {
//            Room room = roomRepository.findById(event.get().getRoomId()).orElse(null);
//            if (room == null) {
//                throw new RoomNotExistsException("Room with id " + event.get().getRoomId() + " does not exist.");
//            } else if (!room.getOwner().equals(username)) {
//                throw new UnauthorizedException("You are not the owner of this room. Only the owner can start events.");
//            } else {
//                Event eventToStart = event.get();
//                eventToStart.setStatus(EventStatusEnum.IN_PROGRESS);
//                Event savedEvent = eventRepository.save(eventToStart);
//                return new BaseResponse("Event started successfully.", true, true, savedEvent);
//            }
//        }
//    }
}
