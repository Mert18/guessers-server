package dev.m2t.unlucky.service;

import dev.m2t.unlucky.dto.BaseResponse;
import dev.m2t.unlucky.dto.SingleGuessDto;
import dev.m2t.unlucky.dto.request.CreateGuessPaperRequest;
import dev.m2t.unlucky.dto.request.ListGuessPapersRequest;
import dev.m2t.unlucky.exception.EventNotExistsException;
import dev.m2t.unlucky.exception.RoomNotExistsException;
import dev.m2t.unlucky.exception.UnauthorizedException;
import dev.m2t.unlucky.exception.UsernameNotExistsException;
import dev.m2t.unlucky.model.*;
import dev.m2t.unlucky.model.enums.GuessPaperStatusEnum;
import dev.m2t.unlucky.repository.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GuessPaperService {
    private final GuessPaperRepository guessPaperRepository;
    private final GuessPaperPagingRepository guessPaperPagingRepository;
    private final UserRepository userRepository;
    private final RoomRepository roomRepository;
    private final EventRepository eventRepository;
    private static final Logger logger = LoggerFactory.getLogger(GuessPaperService.class);

    public GuessPaperService(GuessPaperRepository guessPaperRepository, GuessPaperPagingRepository guessPaperPagingRepository, UserRepository userRepository, RoomRepository roomRepository, EventRepository eventRepository) {
        this.guessPaperRepository = guessPaperRepository;
        this.guessPaperPagingRepository = guessPaperPagingRepository;
        this.userRepository = userRepository;
        this.roomRepository = roomRepository;
        this.eventRepository = eventRepository;
    }


    public BaseResponse createGuessPaper(CreateGuessPaperRequest createGuessPaperRequest, String username) {
        logger.info("Creating guess paper for user {}.", username);
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotExistsException("User not found."));
        Room room = roomRepository.findById(createGuessPaperRequest.getRoomId()).orElseThrow(() -> new RoomNotExistsException("Room not found."));

        if (room.getRoomUsers().stream().noneMatch(roomUser -> roomUser.getUser().equals(user))) {
            throw new UnauthorizedException("You are not one of the members of this room. Only the members can list events.");
        }

        GuessPaper guessPaper = new GuessPaper();
        guessPaper.setUser(user);
        guessPaper.setRoom(room);
        guessPaper.setStake(createGuessPaperRequest.getStake());
        guessPaper.setStatus(GuessPaperStatusEnum.IN_PROGRESS);

        // Map SingleGuessDto to SingleGuess and add to GuessPaper
        List<SingleGuess> singleGuesses = createGuessPaperRequest.getGuesses().stream().map(singleGuessDto -> {
            SingleGuess singleGuess = new SingleGuess();
            Event event = eventRepository.findById(singleGuessDto.getEventId()).orElseThrow(() -> new EventNotExistsException("Event with id " + singleGuessDto.getEventId() + " not exists."));
            EventGuessOption ego = event.getEventGuessOptions().stream().filter(eventGuessOption -> eventGuessOption.getId().equals(singleGuessDto.getEventGuessOptionId())).findFirst().orElseThrow(() -> new EventNotExistsException("Event guess option with id " + singleGuessDto.getEventGuessOptionId() + " not exists."));
            EventGuessOptionCase egoc = ego.getEventGuessOptionCases().stream().filter(eventGuessOptionCase -> eventGuessOptionCase.getId().equals(singleGuessDto.getEventGuessOptionCaseId())).findFirst().orElseThrow(() -> new EventNotExistsException("Event guess option case with id " + singleGuessDto.getEventGuessOptionCaseId() + " not exists."));

            singleGuess.setEvent(event);
            singleGuess.setEventGuessOption(ego);
            singleGuess.setEventGuessOptionCase(egoc);
            singleGuess.setGuessPaper(guessPaper); // Associate guess with guess paper
            return singleGuess;
        }).collect(Collectors.toList());
        guessPaper.getGuesses().addAll(singleGuesses);

        guessPaper.setTotalOdd(singleGuesses.stream().mapToDouble(singleGuess -> singleGuess.getEventGuessOptionCase().getOdds()).reduce(1, (a, b) -> a * b));
        guessPaper.setWins(guessPaper.getTotalOdd() * guessPaper.getStake());

        logger.info("Saving guess paper for user {}.", username);
        GuessPaper savedGuessPaper = guessPaperRepository.save(guessPaper);
        logger.info("Guess paper with id {} created successfully.", savedGuessPaper.getId());

        return new BaseResponse("Guess paper created successfully.", true, true, savedGuessPaper);
    }

    public BaseResponse listSelfGuessPapers(ListGuessPapersRequest listGuessPapersRequest, String username, Pageable pageable) {
        logger.info("Listing guess papers for user {}.", username);
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotExistsException("User not found."));

        return new BaseResponse("Guess papers retrieved successfully.", true, false, guessPaperPagingRepository.findAllByUser(user, pageable));
    }

    public BaseResponse listRoomGuessPapers(ListGuessPapersRequest listGuessPapersRequest, String username, Long roomId, Pageable pageable) {
        logger.info("Listing guess papers for user {}.", username);
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotExistsException("User not found."));
        Room room = roomRepository.findById(roomId).orElseThrow(() -> new RoomNotExistsException("Room not found."));
        if (room.getRoomUsers().stream().noneMatch(roomUser -> roomUser.getUser().equals(user))) {
            throw new UnauthorizedException("You are not one of the members of this room. Only the members can list events.");
        }
        return new BaseResponse("Guess papers retrieved successfully.", true, false, guessPaperPagingRepository.findAllByRoom(room, pageable));
    }
}
