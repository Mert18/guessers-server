package dev.m2t.guessers.service;

import dev.m2t.guessers.dto.BaseResponse;
import dev.m2t.guessers.dto.request.CreateRoomRequest;
import dev.m2t.guessers.dto.response.ListPublicRoomsResponse;
import dev.m2t.guessers.dto.response.RoomRanksResponse;
import dev.m2t.guessers.exception.ResourceNotFoundException;
import dev.m2t.guessers.model.*;
import dev.m2t.guessers.model.enums.RoomInviteStatusEnum;
import dev.m2t.guessers.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class RoomService {
    private final RoomRepository roomRepository;
    private final RoomPagingRepository roomPagingRepository;
    private final UserRepository userRepository;
    private final RoomUserRepository roomUserRepository;
    private final RoomInviteRepository roomInviteRepository;
    private final RoomUserPagingRepository roomUserPagingRepository;
    private final LendLogRepository lendLogRepository;
    private static final Logger logger = LoggerFactory.getLogger(RoomService.class);

    public RoomService(RoomRepository roomRepository, RoomPagingRepository roomPagingRepository, UserRepository userRepository, RoomUserRepository roomUserRepository, RoomInviteRepository roomInviteRepository, RoomUserPagingRepository roomUserPagingRepository, LendLogRepository lendLogRepository) {
        this.roomRepository = roomRepository;
        this.roomPagingRepository = roomPagingRepository;
        this.userRepository = userRepository;
        this.roomUserRepository = roomUserRepository;
        this.roomInviteRepository = roomInviteRepository;
        this.roomUserPagingRepository = roomUserPagingRepository;
        this.lendLogRepository = lendLogRepository;
    }

    public BaseResponse createRoom(CreateRoomRequest createRoomRequest, String owner) {
        logger.info("Create room request received for owner: {}", owner);
        Optional<User> user = userRepository.findByUsername(owner);
        if (user.isEmpty()) {
            return new BaseResponse("User with username " + owner + " does not exist.", false, false, null);
        }

        // Create Room
        Room room = new Room();
        room.setName(createRoomRequest.getName());
        room.setPublic(createRoomRequest.getPublico());
        room.setRoomUsers(new ArrayList<>());
        room.setOwner(user.get());
        room.setBorderless(createRoomRequest.getBorderless());

        // Create RoomUser (owner)
        RoomUser roomUser = new RoomUser();
        roomUser.setUser(user.get());
        roomUser.setRoom(room);
        roomUser.setBalance(1000.0);
        roomUser.setOwner(true);
        roomUser.setScore(0);

        room.getRoomUsers().add(roomUser);
        Room savedRoom = roomRepository.save(room);
        logger.info("Room {} created successfully.", savedRoom.getName());
        return new BaseResponse("Room " + savedRoom.getName() + " created successfully.", true, true, savedRoom);
    }

    public BaseResponse acceptRoomInvite(Long roomId, String username) {
        logger.info("Accept room invite request received for room: {} and user: {}", roomId, username);
        User user = userRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("User", "username", username));
        Room room = roomRepository.findById(roomId).orElseThrow(() -> new ResourceNotFoundException("Room", "id", roomId));

        if(isUserInvitedToRoom(user, room)) {
            RoomInvite invite = roomInviteRepository.findByRoomAndUser(room, user).get();
            invite.setStatus(RoomInviteStatusEnum.ACCEPTED);
            roomInviteRepository.save(invite);

            RoomUser roomUser = new RoomUser();
            roomUser.setUser(user);
            roomUser.setRoom(room);
            if(room.isBorderless()) {
                roomUser.setBalance(0.0);
            } else {
                roomUser.setBalance(1000.0);
            }
            roomUser.setOwner(false);
            roomUser.setScore(0);
            room.getRoomUsers().add(roomUser);
            roomRepository.save(room);
            logger.info("User {} joined room {} successfully.", username, room.getName());
            return new BaseResponse("You have joined the room successfully.", true, true, room);
        } else {
            return new BaseResponse("You are not invited to this room.", false, false);
        }
    }

    public BaseResponse leaveRoom(Long roomId, String username) {
        logger.info("Leave room request received for room: {} and user: {}", roomId, username);
        Room room = roomRepository.findById(roomId).orElseThrow(() -> new ResourceNotFoundException("Room", "id", roomId));
        User user = userRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("User", "username", username));
        RoomUser roomUser = roomUserRepository.findByRoomAndUser(room, user).orElseThrow(() -> new ResourceNotFoundException("User", "user", user));

        roomUserRepository.delete(roomUser);
        logger.info("User {} left room {} successfully.", username, room.getName());
        return new BaseResponse("You have left the room successfully.", true, false, room);

    }

    public BaseResponse deleteRoom(Long roomId, String username) {
        logger.info("Delete room request received for room: {} and user: {}", roomId, username);
        Room room = roomRepository.findById(roomId).orElseThrow(() -> new ResourceNotFoundException("Room", "id", roomId));
        User user = userRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("User", "username", username));
        if(room.getOwner().equals(user)){
            roomRepository.delete(room);
            logger.info("Room {} deleted successfully.", room.getName());
            return new BaseResponse("Room deleted successfully.", true, false);
        } else {
            return new BaseResponse("You are not the owner of this room.", false, false);
        }
    }

    public BaseResponse inviteUser(String invitedUsername, Long roomId, String username) {
        logger.info("Invite user request received for room: {} and user: {}", roomId, username);
        Room room = roomRepository.findById(roomId).orElseThrow(() -> new ResourceNotFoundException("Room", "id", roomId));
        User invitedUser = userRepository.findByUsername(invitedUsername).orElseThrow(() -> new ResourceNotFoundException("User", "username", invitedUsername));
        User ownerUser = userRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("User", "username", username));

        if(room.getOwner().equals(ownerUser)){
            RoomInvite invite = new RoomInvite();
            invite.setRoom(room);
            invite.setUser(invitedUser);
            invite.setStatus(RoomInviteStatusEnum.PENDING);
            roomInviteRepository.save(invite);
            logger.info("User {} invited to room {} successfully.", invitedUsername, room.getName());
            return new BaseResponse("User invited successfully.", true, true);
        } else {
            return new BaseResponse("You are not the owner of this room.", false, false);
        }
    }

    public BaseResponse rejectRoomInvite(Long roomId, String username) {
        logger.info("Reject room invite request received for room: {} and user: {}", roomId, username);
        Room room = roomRepository.findById(roomId).orElseThrow(() -> new ResourceNotFoundException("Room", "id", roomId));
        User user = userRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("User", "username", username));

        // Or we should just set as REJECTED?
        RoomInvite invite = roomInviteRepository.findByRoomAndUser(room, user).get();
        roomInviteRepository.delete(invite);
        logger.info("User {} rejected room {} invite successfully.", username, room.getName());
        return new BaseResponse("You have rejected the room invite.", true, false);
    }

    public BaseResponse listSelfRooms(String username, Pageable pageable) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("User", "username", username));
        Page<RoomUser> rooms = roomUserPagingRepository.findAllByUser(user, pageable);

        rooms.forEach(roomUser -> roomUser.setMemberCount(roomUser.getRoom().getRoomUsers().size()));
        return new BaseResponse("Rooms fetched successfully.", true, false, rooms);
    }

    public BaseResponse getRoom(Long roomId, String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("User", "username", username));
        Room room = roomRepository.findById(roomId).orElseThrow(() -> new ResourceNotFoundException("Room", "id", roomId));

        if(room.getRoomUsers().stream().anyMatch(roomUser -> roomUser.getUser().equals(user))) {
            return new BaseResponse("Room fetched successfully.", true, false, room);
        }

        return new BaseResponse("You are not a member of this room.", false, false);
    }

    public BaseResponse listPublicRooms(Pageable pageable, String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("User", "username", username));

        ListPublicRoomsResponse listPublicRoomsResponse = new ListPublicRoomsResponse();
        Page<Room> rooms = roomPagingRepository.findAllPublicRoomsExcludingUser(user.getId(), pageable);
        if (rooms != null && !rooms.isEmpty()) {
            listPublicRoomsResponse.setRooms(rooms);
        }
        return new BaseResponse("Public rooms fetched successfully.", true, false, listPublicRoomsResponse);
    }

    public BaseResponse joinPublicRoom(String username, Long roomId) {
        logger.info("Join public room request received for room: {} and user: {}", roomId, username);
        User user = userRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("User", "username", username));
        Room room = roomRepository.findById(roomId).orElseThrow(() -> new ResourceNotFoundException("Room", "id", roomId));

        Optional<RoomUser> existRoomUser = roomUserRepository.findByRoomAndUser(room, user);
        if(existRoomUser.isPresent()){
            return new BaseResponse("You are already a member of this room.", false, false);
        }
        RoomUser roomUser = new RoomUser();
        roomUser.setUser(user);
        roomUser.setRoom(room);
        roomUser.setBalance(1000.0);
        roomUser.setOwner(false);
        roomUser.setScore(0);

        room.getRoomUsers().add(roomUser);
        roomRepository.save(room);
        logger.info("User {} joined room {} successfully.", username, room.getName());
        return new BaseResponse("You have joined the room successfully.", true, true, room);
    }

    public boolean isUserInvitedToRoom(User user, Room room) {
        Optional<RoomInvite> invite = roomInviteRepository.findByRoomAndUser(room, user);

        return invite.isPresent() && invite.get().getStatus() == RoomInviteStatusEnum.PENDING;
    }

    public BaseResponse searchRooms(String query, Pageable pageable, String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("User", "username", username));
        Page<Room> searchResultRooms = roomPagingRepository.findRoomsExcludingUser(user.getId(), query, pageable);
        return new BaseResponse("Rooms fetched successfully.", true, false, searchResultRooms);
    }

    public BaseResponse getSelfRoomUser(Long roomId, String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("User", "username", username));
        Room room = roomRepository.findById(roomId).orElseThrow(() -> new ResourceNotFoundException("Room", "id", roomId));
        RoomUser roomUser = roomUserRepository.findByRoomAndUser(room, user).orElseThrow(() -> new ResourceNotFoundException("Room User", "user", user.getUsername()));
        return new BaseResponse("Room user fetched successfully.", true, false, roomUser);
    }

    public BaseResponse getRoomRanks(Long roomId, String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("User", "username", username));
        Room room = roomRepository.findById(roomId).orElseThrow(() -> new ResourceNotFoundException("Room", "id", roomId));

        RoomRanksResponse roomRanksResponse = new RoomRanksResponse();
        if(room.getRoomUsers().stream().noneMatch(roomUser -> roomUser.getUser().equals(user))) {
            return new BaseResponse("You are not a member of this room.", false, false);
        }

        List<RoomUser> roomUsers = roomUserRepository.findAllByRoom(room);

        roomRanksResponse.setRoomId(roomId);
        roomRanksResponse.setRoomName(room.getName());
        List<RoomUser> roomUsersSortedByCorrectPredictions = roomUsers.stream()
                .sorted(Comparator.comparing(RoomUser::getScore).reversed())
                .limit(3)
                .collect(Collectors.toList());
        List<RoomUser> roomUsersSortedByBalance = roomUsers.stream()
                .sorted(Comparator.comparing(RoomUser::getBalance).reversed())
                .limit(3)
                .collect(Collectors.toList());

        roomRanksResponse.setRankedByCorrectPredictions(roomUsersSortedByCorrectPredictions);
        roomRanksResponse.setRankedByBalance(roomUsersSortedByBalance);
        roomRanksResponse.setUserCount(roomUsers.size());
        return new BaseResponse("Room ranks fetched successfully.", true, false, roomRanksResponse);
    }

    public BaseResponse giveToken(Long roomId, List<String> roomUserIds, Double amount, String username) {
        logger.info("Give token request received for room: {} and user: {}", roomId, username);
        Room room = roomRepository.findById(roomId).orElseThrow(() -> new ResourceNotFoundException("Room", "id", roomId));

        if(room.isBorderless()) {
            return new BaseResponse("This room is borderless. You cannot give tokens in borderless rooms.", false, true);
        }

        User user = userRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("User", "username", username));
        RoomUser roomUser = roomUserRepository.findByRoomAndUser(room, user).orElseThrow(() -> new ResourceNotFoundException("Room User", "user", user.getUsername()));

        if(!roomUser.getOwner()) {
            return new BaseResponse("You are not the owner of this room.", false, false);
        }

        List<LendLog> lendLogs = new ArrayList<>();
        List<Long> roomUserIdsLong = roomUserIds.stream().map(Long::parseLong).collect(Collectors.toList());
        List<RoomUser> roomUsers = roomUserRepository.findAllByIdIn(roomUserIdsLong);
        for (RoomUser ru : roomUsers) {
            ru.setBalance(ru.getBalance() + amount);
            LendLog lendLog = new LendLog();
            lendLog.setAmount(amount);
            lendLog.setCreatedOn(LocalDateTime.now());
            lendLog.setRoom(room);
            lendLog.setRoomUser(ru);
            lendLogs.add(lendLog);
        }

        roomUserRepository.saveAll(roomUsers);
        lendLogRepository.saveAll(lendLogs);
        logger.info("Tokens given successfully for room {}.", room.getName());
        return new BaseResponse("Tokens given successfully.", true, false);
    }

    public BaseResponse getRoomUsers(Long roomId, String username) {
        Room room = roomRepository.findById(roomId).orElseThrow(() -> new ResourceNotFoundException("Room", "id", roomId));
        User user = userRepository.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("User", "username", username));
        roomUserRepository.findByRoomAndUser(room, user).orElseThrow(() -> new ResourceNotFoundException("Room User", "user", user.getUsername()));

        List<RoomUser> roomUsers = roomUserRepository.findAllByRoom(room);
        return new BaseResponse("Room users fetched successfully.", true, false, roomUsers);
    }
}
