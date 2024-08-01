package dev.m2t.unlucky.service;

import dev.m2t.unlucky.dto.BaseResponse;
import dev.m2t.unlucky.dto.request.CreateRoomRequest;
import dev.m2t.unlucky.dto.request.JoinRoomRequest;
import dev.m2t.unlucky.dto.response.ListPublicRoomsResponse;
import dev.m2t.unlucky.exception.RoomNotExistsException;
import dev.m2t.unlucky.exception.RoomUserNotFoundException;
import dev.m2t.unlucky.exception.UsernameNotExistsException;
import dev.m2t.unlucky.model.Room;
import dev.m2t.unlucky.model.RoomInvite;
import dev.m2t.unlucky.model.RoomUser;
import dev.m2t.unlucky.model.User;
import dev.m2t.unlucky.model.enums.RoomInviteStatusEnum;
import dev.m2t.unlucky.repository.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RoomService {
    private final RoomRepository roomRepository;
    private final RoomPagingRepository roomPagingRepository;
    private final UserRepository userRepository;
    private final RoomUserRepository roomUserRepository;
    private RoomInviteRepository roomInviteRepository;

    public RoomService(RoomRepository roomRepository, RoomPagingRepository roomPagingRepository, UserRepository userRepository, RoomUserRepository roomUserRepository, RoomInviteRepository roomInviteRepository) {
        this.roomRepository = roomRepository;
        this.roomPagingRepository = roomPagingRepository;
        this.userRepository = userRepository;
        this.roomUserRepository = roomUserRepository;
        this.roomInviteRepository = roomInviteRepository;
    }

    public BaseResponse createRoom(CreateRoomRequest createRoomRequest, String owner) {
        Optional<User> user = userRepository.findByUsername(owner);
        if (user.isEmpty()) {
            return new BaseResponse("User with username " + owner + " does not exist.", false, false, null);
        }

        // Create Room
        Room room = new Room();
        room.setName(createRoomRequest.getName());
        room.setDescription(createRoomRequest.getDescription());
        room.setPublic(createRoomRequest.getIsPublic());
        room.setRoomUsers(new ArrayList<>());
        room.setOwner(user.get());

        // Create RoomUser (owner)
        RoomUser roomUser = new RoomUser();
        roomUser.setUser(user.get());
        roomUser.setRoom(room);
        roomUser.setBalance(1000.0);
        roomUser.setOwner(true);
        roomUser.setScore(0);

        room.getRoomUsers().add(roomUser);
        Room savedRoom = roomRepository.save(room);

        return new BaseResponse("Room " + savedRoom.getName() + " created successfully.", true, true, savedRoom);
    }

    public BaseResponse acceptRoomInvite(Long roomId, String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotExistsException("User with username " + username + " does not exist."));
        Room room = roomRepository.findById(roomId).orElseThrow(() -> new RoomNotExistsException("Room with id " + roomId + " does not exist."));

        if(isUserInvitedToRoom(user, room)) {
            RoomInvite invite = roomInviteRepository.findByRoomAndUser(room, user).get();
            invite.setStatus(RoomInviteStatusEnum.ACCEPTED);
            roomInviteRepository.save(invite);

            RoomUser roomUser = new RoomUser();
            roomUser.setUser(user);
            roomUser.setRoom(room);
            roomUser.setBalance(1000.0);
            roomUser.setOwner(false);
            roomUser.setScore(0);

            room.getRoomUsers().add(roomUser);
            roomRepository.save(room);

            return new BaseResponse("You have joined the room successfully.", true, true, room);
        } else {
            return new BaseResponse("You are not invited to this room.", false, false);
        }
    }

    public BaseResponse leaveRoom(Long roomId, String username) {
        Room room = roomRepository.findById(roomId).orElseThrow(() -> new RoomNotExistsException("Room with id " + roomId + " does not exist."));
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotExistsException("User with username " + username + " does not exist."));
        RoomUser roomUser = roomUserRepository.findByRoomAndUser(room, user).orElseThrow(() -> new RoomUserNotFoundException("User is not part of the room."));

        roomUserRepository.delete(roomUser);

        return new BaseResponse("You have left the room successfully.", true, false, room);

    }

    public BaseResponse deleteRoom(Long roomId, String username) {
        Room room = roomRepository.findById(roomId).orElseThrow(() -> new RoomNotExistsException("Room with id " + roomId + " does not exist."));
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotExistsException("User with username " + username + " does not exist."));
        if(room.getOwner().equals(user)){
            roomRepository.delete(room);
            return new BaseResponse("Room deleted successfully.", true, false);
        } else {
            return new BaseResponse("You are not the owner of this room.", false, false);
        }
    }

    public BaseResponse inviteUser(String invitedUsername, Long roomId, String username) {
        Room room = roomRepository.findById(roomId).orElseThrow(() -> new RoomNotExistsException("Room with id " + roomId + " does not exist."));
        User invitedUser = userRepository.findByUsername(invitedUsername).orElseThrow(() -> new UsernameNotExistsException("User with username " + invitedUsername + " does not exist."));
        User ownerUser = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotExistsException("User with username " + username + " does not exist."));

        if(room.getOwner().equals(ownerUser)){
            RoomInvite invite = new RoomInvite();
            invite.setRoom(room);
            invite.setUser(invitedUser);
            invite.setStatus(RoomInviteStatusEnum.PENDING);
            roomInviteRepository.save(invite);

            return new BaseResponse("User invited successfully.", true, true);
        } else {
            return new BaseResponse("You are not the owner of this room.", false, false);
        }
    }

    public BaseResponse rejectRoomInvite(Long roomId, String username) {
        Room room = roomRepository.findById(roomId).orElseThrow(() -> new RoomNotExistsException("Room with id " + roomId + " does not exist."));
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotExistsException("User with username " + username + " does not exist."));

        // Or we should just set as REJECTED?
        RoomInvite invite = roomInviteRepository.findByRoomAndUser(room, user).get();
        roomInviteRepository.delete(invite);

        return new BaseResponse("You have rejected the room invite.", true, false);
    }

    public BaseResponse listSelfRooms(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotExistsException("User with username " + username + " does not exist."));
        List<RoomUser> rooms = user.getRooms();
        return new BaseResponse("Rooms fetched successfully.", true, false, rooms);
    }

    public BaseResponse isOwner(Long roomId, String username) {
        Room room = roomRepository.findById(roomId).orElseThrow(() -> new RoomNotExistsException("Room with id " + roomId + " does not exist."));
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotExistsException("User with username " + username + " does not exist."));

        if(room.getOwner().equals(user)){
            return new BaseResponse("You are the owner of this room.", true, false);
        } else {
            return new BaseResponse("You are not the owner of this room.", false, false);
        }
    }

    public BaseResponse getRoom(Long roomId, String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotExistsException("User with username " + username + " does not exist."));
        Room room = roomRepository.findById(roomId).orElseThrow(() -> new RoomNotExistsException("Room with id " + roomId + " does not exist."));

        if(room.getRoomUsers().contains(user)){
            return new BaseResponse("Room fetched successfully.", true, false, room);
        } else {
            return new BaseResponse("You are not a member of this room.", false, false);
        }
    }

    public BaseResponse listPublicRooms(Pageable pageable) {
        ListPublicRoomsResponse listPublicRoomsResponse = new ListPublicRoomsResponse();
        Page<Room> rooms = roomPagingRepository.findAllByIsPublic(pageable, true);
        if (rooms != null && !rooms.isEmpty()) {
            listPublicRoomsResponse.setRooms(rooms);
        }
        return new BaseResponse("Public rooms fetched successfully.", true, false, listPublicRoomsResponse);
    }

    public BaseResponse joinPublicRoom(String username, Long roomId) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotExistsException("User with username " + username + " does not exist."));
        Room room = roomRepository.findById(roomId).orElseThrow(() -> new RoomNotExistsException("Room with id " + roomId + " does not exist."));

        RoomUser roomUser = new RoomUser();
        roomUser.setUser(user);
        roomUser.setRoom(room);
        roomUser.setBalance(1000.0);
        roomUser.setOwner(false);
        roomUser.setScore(0);

        room.getRoomUsers().add(roomUser);
        roomRepository.save(room);

        return new BaseResponse("You have joined the room successfully.", true, true, room);
    }

    public boolean isUserInvitedToRoom(User user, Room room) {
        Optional<RoomInvite> invite = roomInviteRepository.findByRoomAndUser(room, user);

        return invite.isPresent() && invite.get().getStatus() == RoomInviteStatusEnum.PENDING;
    }
}
