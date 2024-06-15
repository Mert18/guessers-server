package dev.m2t.unlucky.service;

import dev.m2t.unlucky.dto.BaseResponse;
import dev.m2t.unlucky.dto.request.CreateRoomRequest;
import dev.m2t.unlucky.dto.request.InviteUserRequest;
import dev.m2t.unlucky.dto.request.JoinRoomRequest;
import dev.m2t.unlucky.dto.response.IsRoomOwnerResponse;
import dev.m2t.unlucky.dto.response.ListRoomsResponse;
import dev.m2t.unlucky.model.Room;
import dev.m2t.unlucky.model.User;
import dev.m2t.unlucky.repository.BetSlipPagingRepository;
import dev.m2t.unlucky.repository.RoomRepository;
import dev.m2t.unlucky.repository.UserRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomService {
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;
    private final BetSlipPagingRepository betSlipPagingRepository;

    public RoomService(RoomRepository roomRepository, UserRepository userRepository, BetSlipPagingRepository betSlipPagingRepository) {
        this.roomRepository = roomRepository;
        this.userRepository = userRepository;
        this.betSlipPagingRepository = betSlipPagingRepository;
    }

    public BaseResponse createRoom(CreateRoomRequest createRoomRequest, String owner) {
        User user = userRepository.findByUsername(owner);
        if(user == null) {
            return new BaseResponse("User with username "+ owner + " does not exist.", false, false, null);
        }

        Room room = new Room();
        room.setName(createRoomRequest.getName());
        room.setDescription(createRoomRequest.getDescription());
        room.setUsers(List.of(owner));
        room.setOwner(owner);
        Room savedRoom = roomRepository.save(room);

        user.getRooms().add(savedRoom.getId());
        userRepository.save(user);
        return new BaseResponse("Room "+ savedRoom.getName() + " created successfully.", true,  true, savedRoom);
    }

    public BaseResponse acceptRoomInvite(String roomId, String username) {
        User user = userRepository.findByUsername(username);
        Room room = roomRepository.findById(roomId).orElse(null);
        if(room == null) {
            return new BaseResponse("Room with id "+ roomId + " does not exist.", false, false, null);
        }else if(room.getUsers().contains(username)){
            return new BaseResponse("You are already a member of this room.", false, false);
        }else if(!room.getPendingUserInvites().contains(username)) {
            return new BaseResponse("You are not invited to this room.", false, false);
        }else if (user == null) {
            return new BaseResponse("User with username "+ username + " does not exist.", false, false, null);
        }else {
            room.getUsers().add(username);
            room.getPendingUserInvites().remove(username);
            user.getPendingRoomInvites().remove(roomId);
            user.getRooms().add(roomId);
            userRepository.save(user);
            Room savedRoom = roomRepository.save(room);
            return new BaseResponse("You have joined the room successfully.", true, false, savedRoom);
        }
    }

    public BaseResponse leaveRoom(JoinRoomRequest joinRoomRequest, String username) {
        Room room = roomRepository.findById(joinRoomRequest.getRoomId()).orElse(null);
        if(room == null) {
            return new BaseResponse("Room with id "+ joinRoomRequest.getRoomId() + " does not exist.", false, false, null);
        }else if(!room.getUsers().contains(username)){
            return new BaseResponse("You are not a member of this room.", false, false);
        }else {
            room.getUsers().remove(username);
            Room savedRoom = roomRepository.save(room);
            return new BaseResponse("You have left the room successfully.", true, false, savedRoom);
        }
    }

    public BaseResponse deleteRoom(JoinRoomRequest joinRoomRequest, String username) {
        Room room = roomRepository.findById(joinRoomRequest.getRoomId()).orElse(null);
        if(room == null) {
            return new BaseResponse("Room with id "+ joinRoomRequest.getRoomId() + " does not exist.", false, false, null);
        }else if(!room.getOwner().equals(username)){
            return new BaseResponse("You are not the owner of this room.", false, false);
        }else {
            roomRepository.delete(room);
            return new BaseResponse("Room deleted successfully.", true, false);
        }
    }

    public BaseResponse inviteUser(InviteUserRequest inviteUserRequest, String username, String roomId) {
        Room room = roomRepository.findById(roomId).orElse(null);
        User user = userRepository.findByUsername(inviteUserRequest.getUsername());
        if(room == null) {
            return new BaseResponse("Room with id "+ roomId + " does not exist.", false, false, null);
        }else if(!room.getOwner().equals(username)){
            return new BaseResponse("You are not the owner of this room. Only the owner can invite users.", false, false);
        }else if(room.getUsers().contains(inviteUserRequest.getUsername())) {
            return new BaseResponse("User is already a member of this room.", false, false);
        }else if(room.getPendingUserInvites().contains(inviteUserRequest.getUsername())) {
            return new BaseResponse("User is already invited to this room.", false, false);
        }else if(user == null) {
            return new BaseResponse("User with username "+ inviteUserRequest.getUsername() + " does not exist.", false, false, null);
        }
        else {
            room.getPendingUserInvites().add(inviteUserRequest.getUsername());
            user.getPendingRoomInvites().add(roomId);
            userRepository.save(user);
            Room savedRoom = roomRepository.save(room);
            return new BaseResponse("User invited successfully.", true, false, savedRoom);
        }
    }

    public BaseResponse rejectRoomInvite(String roomId, String username) {
        Room room = roomRepository.findById(roomId).orElse(null);
        User user = userRepository.findByUsername(username);
        if(room == null) {
            return new BaseResponse("Room with id "+ roomId + " does not exist.", false, false, null);
        }else if(!room.getPendingUserInvites().contains(username)){
            return new BaseResponse("You are not invited to this room.", false, false);
        }else {
            room.getPendingUserInvites().remove(username);
            user.getPendingRoomInvites().remove(roomId);
            userRepository.save(user);
            Room savedRoom = roomRepository.save(room);
            return new BaseResponse("Room invite rejected successfully.", true, false, savedRoom);
        }
    }

    public BaseResponse listSelfRooms(String username) {
        ListRoomsResponse listRoomsResponse = new ListRoomsResponse();
        List<Room> rooms = roomRepository.findByOwnerOrUsersContaining(username);
        if (rooms != null && !rooms.isEmpty()) {
            listRoomsResponse.setRooms(rooms);
        }
        return new BaseResponse("Rooms fetched successfully.", true, false, listRoomsResponse);
    }

    public BaseResponse isOwner(String roomId, String username) {
        Room room = roomRepository.findById(roomId).orElse(null);
        if(room == null) {
            return new BaseResponse("Room with id "+ roomId + " does not exist.", false, false, null);
        }else if(room.getOwner().equals(username)) {
            return new BaseResponse("You are the owner of this room.", true, false, new IsRoomOwnerResponse(true));
        }else {
            return new BaseResponse("You are not the owner of this room.", false, false, new IsRoomOwnerResponse(false));
        }
    }

    public BaseResponse listRoomBetSlips(String roomId, String username, Pageable pageable) {
        Room room = roomRepository.findById(roomId).orElse(null);
        if(room == null) {
            return new BaseResponse("Room with id "+ roomId + " does not exist.", false, false, null);
        }else if(!room.getUsers().contains(username)){
            return new BaseResponse("You are not a member of this room.", false, false);
        } else {
            return new BaseResponse("Bet slips fetched successfully.", true, false, betSlipPagingRepository.findAllByRoomId(roomId, pageable));
        }
    }

    public BaseResponse getRoom(String roomId, String username) {
        User user = userRepository.findByUsername(username);
        Room room = roomRepository.findById(roomId).orElse(null);
        if(room == null) {
            return new BaseResponse("Room with id "+ roomId + " does not exist.", false, false, null);
        }else if(!room.getUsers().contains(username)){
            return new BaseResponse("You are not a member of this room.", false, false);
        }else if(user == null) {
            return new BaseResponse("User with username "+ username + " does not exist.", false, false, null);
        } else {
            return new BaseResponse("Room fetched successfully.", true, false, room);
        }
    }
}
