package dev.m2t.unlucky.service;

import org.springframework.stereotype.Service;

@Service
public class EventService {
//    private final EventRepository eventRepository;
//    private final EventPagingRepository eventPagingRepository;
//    private final RoomRepository roomRepository;
//    private final UserRepository userRepository;
//    private final BetSlipRepository betSlipRepository;
//
//    private static final Logger logger = LoggerFactory.getLogger(EventService.class);
//
//    public EventService(EventRepository eventRepository, EventPagingRepository eventPagingRepository, RoomRepository roomRepository, UserRepository userRepository, BetSlipRepository betSlipRepository) {
//        this.eventRepository = eventRepository;
//        this.eventPagingRepository = eventPagingRepository;
//        this.roomRepository = roomRepository;
//        this.userRepository = userRepository;
//        this.betSlipRepository = betSlipRepository;
//    }
//
//    public BaseResponse createEvent(CreateEventRequest createEventRequest, String username, Long roomId) {
//        logger.info("Create event request received for room: {}", roomId);
//        Optional<Room> room = roomRepository.findById(roomId);
//        if (room.isEmpty()) {
//            logger.debug("Room with id {} does not exist.", roomId);
//            throw new RoomNotExistsException("Room with id " + roomId + " does not exist.");
//        } else if (!room.get().getOwner().getUsername().equals(username)) {
//            logger.debug("User {} is not the owner of the room.", username);
//            throw new UnauthorizedException("You are not the owner of this room. Only the owner can create events.");
//        } else {
//            // Set option numbers for options
//            for (int i = 0; i < createEventRequest.getOptions().size(); i++) {
//                createEventRequest.getOptions().get(i).setOptionNumber(i);
//            }
//            Event event = new Event(createEventRequest.getName(), createEventRequest.getDescription(), roomId, createEventRequest.getOptions(), EventStatusEnum.NOT_STARTED);
//            Event savedEvent = eventRepository.save(event);
//            logger.info("Event created successfully for room: {}", roomId);
//            return new BaseResponse("Event created successfully.", true, true, savedEvent);
//        }
//    }
//
//    // Lists events that are not started yet for a given room
//    public BaseResponse listEvents(String roomId, String username, Pageable pageable) {
//        Optional<Room> room = roomRepository.findById(roomId);
//        if (room.isEmpty()) {
//            throw new RoomNotExistsException("Room with id " + roomId + " does not exist.");
//        } else if (!room.get().getUsers().contains(username)) {
//            throw new UnauthorizedException("You are not one of the members of this room. Only the members can list events.");
//        } else {
//            Page<Event> events = eventPagingRepository.findByStatusInAndRoomId(List.of(EventStatusEnum.NOT_STARTED, EventStatusEnum.IN_PROGRESS), roomId, pageable);
//
//            return new BaseResponse("Events fetched successfully.", true, false, events);
//        }
//    }
//
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
