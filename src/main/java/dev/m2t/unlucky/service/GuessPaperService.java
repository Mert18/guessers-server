package dev.m2t.unlucky.service;

import dev.m2t.unlucky.dto.BaseResponse;
import dev.m2t.unlucky.dto.request.CreateGuessPaperRequest;
import dev.m2t.unlucky.dto.request.ListGuessPapersRequest;
import dev.m2t.unlucky.exception.RoomNotExistsException;
import dev.m2t.unlucky.exception.UnauthorizedException;
import dev.m2t.unlucky.exception.UsernameNotExistsException;
import dev.m2t.unlucky.model.GuessPaper;
import dev.m2t.unlucky.model.Room;
import dev.m2t.unlucky.model.User;
import dev.m2t.unlucky.model.enums.GuessPaperStatusEnum;
import dev.m2t.unlucky.repository.GuessPaperPagingRepository;
import dev.m2t.unlucky.repository.GuessPaperRepository;
import dev.m2t.unlucky.repository.RoomRepository;
import dev.m2t.unlucky.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class GuessPaperService {
    private final GuessPaperRepository guessPaperRepository;
    private final GuessPaperPagingRepository guessPaperPagingRepository;
    private final UserRepository userRepository;
    private final RoomRepository roomRepository;
    private static final Logger logger = LoggerFactory.getLogger(GuessPaperService.class);

    public GuessPaperService(GuessPaperRepository guessPaperRepository, GuessPaperPagingRepository guessPaperPagingRepository, UserRepository userRepository, RoomRepository roomRepository) {
        this.guessPaperRepository = guessPaperRepository;
        this.guessPaperPagingRepository = guessPaperPagingRepository;
        this.userRepository = userRepository;
        this.roomRepository = roomRepository;
    }


    public BaseResponse createGuessPaper(CreateGuessPaperRequest createGuessPaperRequest, String username) {
        logger.info("Creating guess paper for user {}.", username);
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotExistsException("User not found."));
        Room room = roomRepository.findById(createGuessPaperRequest.getRoomId()).orElseThrow(() -> new RoomNotExistsException("Room not found."));

        if (room.getRoomUsers().stream().noneMatch(roomUser -> roomUser.getUser().equals(user))) {
            throw new UnauthorizedException("You are not one of the members of this room. Only the members can list events.");
        }

        // Handle inputs
        GuessPaper guessPaper = new GuessPaper();
        guessPaper.setGuesses(createGuessPaperRequest.getGuesses());
        guessPaper.setStatus(GuessPaperStatusEnum.IN_PROGRESS);
        guessPaper.setStake(createGuessPaperRequest.getStake());
        guessPaper.setCreatedOn(LocalDateTime.now());
        guessPaper.setRoom(room);
        guessPaper.setUser(user);

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
