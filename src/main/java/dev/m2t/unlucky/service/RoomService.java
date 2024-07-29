package dev.m2t.unlucky.service;

import dev.m2t.unlucky.dto.BaseResponse;
import dev.m2t.unlucky.dto.request.CreateRoomRequest;
import dev.m2t.unlucky.model.Room;
import dev.m2t.unlucky.model.RoomUser;
import dev.m2t.unlucky.model.User;
import dev.m2t.unlucky.repository.RoomPagingRepository;
import dev.m2t.unlucky.repository.RoomRepository;
import dev.m2t.unlucky.repository.RoomUserRepository;
import dev.m2t.unlucky.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RoomService {
    private final RoomRepository roomRepository;
    private final RoomPagingRepository roomPagingRepository;
    private final UserRepository userRepository;
    private final RoomUserRepository roomUserRepository;

    public RoomService(RoomRepository roomRepository, RoomPagingRepository roomPagingRepository, UserRepository userRepository, RoomUserRepository roomUserRepository) {
        this.roomRepository = roomRepository;
        this.roomPagingRepository = roomPagingRepository;
        this.userRepository = userRepository;
        this.roomUserRepository = roomUserRepository;
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

//    public BaseResponse acceptRoomInvite(String roomId, String username) {
//        User user = userRepository.findByUsername(username);
//        Room room = roomRepository.findById(roomId).orElse(null);
//        if (room == null) {
//            return new BaseResponse("Room with id " + roomId + " does not exist.", false, false, null);
//        } else if (room.getUsers().contains(username)) {
//            return new BaseResponse("You are already a member of this room.", false, false);
//        } else if (!room.getPendingUserInvites().contains(username)) {
//            return new BaseResponse("You are not invited to this room.", false, false);
//        } else if (user == null) {
//            return new BaseResponse("User with username " + username + " does not exist.", false, false, null);
//        } else {
//            room.getUsers().add(username);
//            room.getPendingUserInvites().remove(username);
//            room.getUserCorrectPredictions().put(username, 0);
//            user.getPendingRoomInvites().remove(roomId);
//            user.getRooms().add(roomId);
//            userRepository.save(user);
//            Room savedRoom = roomRepository.save(room);
//            return new BaseResponse("You have joined the room successfully.", true, true, savedRoom);
//        }
//    }
//
//    public BaseResponse leaveRoom(JoinRoomRequest joinRoomRequest, String username) {
//        Room room = roomRepository.findById(joinRoomRequest.getRoomId()).orElse(null);
//        if (room == null) {
//            return new BaseResponse("Room with id " + joinRoomRequest.getRoomId() + " does not exist.", false, false, null);
//        } else if (!room.getUsers().contains(username)) {
//            return new BaseResponse("You are not a member of this room.", false, false);
//        } else {
//            room.getUsers().remove(username);
//            Room savedRoom = roomRepository.save(room);
//            return new BaseResponse("You have left the room successfully.", true, false, savedRoom);
//        }
//    }
//
//    public BaseResponse deleteRoom(JoinRoomRequest joinRoomRequest, String username) {
//        Room room = roomRepository.findById(joinRoomRequest.getRoomId()).orElse(null);
//        if (room == null) {
//            return new BaseResponse("Room with id " + joinRoomRequest.getRoomId() + " does not exist.", false, false, null);
//        } else if (!room.getOwner().equals(username)) {
//            return new BaseResponse("You are not the owner of this room.", false, false);
//        } else {
//            roomRepository.delete(room);
//            return new BaseResponse("Room deleted successfully.", true, false);
//        }
//    }
//
//    public BaseResponse inviteUser(InviteUserRequest inviteUserRequest, String username, String roomId) {
//        Room room = roomRepository.findById(roomId).orElse(null);
//        User user = userRepository.findByUsername(inviteUserRequest.getUsername());
//        if (room == null) {
//            return new BaseResponse("Room with id " + roomId + " does not exist.", false, false, null);
//        } else if (!room.getOwner().equals(username)) {
//            return new BaseResponse("You are not the owner of this room. Only the owner can invite users.", false, false);
//        } else if (room.getUsers().contains(inviteUserRequest.getUsername())) {
//            return new BaseResponse("User is already a member of this room.", false, false);
//        } else if (room.getPendingUserInvites().contains(inviteUserRequest.getUsername())) {
//            return new BaseResponse("User is already invited to this room.", false, false);
//        } else if (user == null) {
//            return new BaseResponse("User with username " + inviteUserRequest.getUsername() + " does not exist.", false, false, null);
//        } else {
//            room.getPendingUserInvites().add(inviteUserRequest.getUsername());
//            user.getPendingRoomInvites().add(roomId);
//            userRepository.save(user);
//            Room savedRoom = roomRepository.save(room);
//            return new BaseResponse("User invited successfully.", true, true, savedRoom);
//        }
//    }
//
//    public BaseResponse rejectRoomInvite(String roomId, String username) {
//        Room room = roomRepository.findById(roomId).orElse(null);
//        User user = userRepository.findByUsername(username);
//        if (room == null) {
//            return new BaseResponse("Room with id " + roomId + " does not exist.", false, false, null);
//        } else if (!room.getPendingUserInvites().contains(username)) {
//            return new BaseResponse("You are not invited to this room.", false, false);
//        } else {
//            room.getPendingUserInvites().remove(username);
//            user.getPendingRoomInvites().remove(roomId);
//            userRepository.save(user);
//            Room savedRoom = roomRepository.save(room);
//            return new BaseResponse("Room invite rejected successfully.", true, true, savedRoom);
//        }
//    }
//
//    public BaseResponse listSelfRooms(String username) {
//        ListRoomsResponse listRoomsResponse = new ListRoomsResponse();
//        List<Room> rooms = roomRepository.findByOwnerOrUsersContaining(username);
//        if (rooms != null && !rooms.isEmpty()) {
//            listRoomsResponse.setRooms(rooms);
//        }
//        return new BaseResponse("Rooms fetched successfully.", true, false, listRoomsResponse);
//    }
//
//    public BaseResponse isOwner(String roomId, String username) {
//        Room room = roomRepository.findById(roomId).orElse(null);
//        if (room == null) {
//            return new BaseResponse("Room with id " + roomId + " does not exist.", false, false, null);
//        } else if (room.getOwner().equals(username)) {
//            return new BaseResponse("You are the owner of this room.", true, false, new IsRoomOwnerResponse(true));
//        } else {
//            return new BaseResponse("You are not the owner of this room.", false, false, new IsRoomOwnerResponse(false));
//        }
//    }
//
//    public BaseResponse listRoomBetSlips(String roomId, String username, Pageable pageable) {
//        Room room = roomRepository.findById(roomId).orElse(null);
//        if (room == null) {
//            return new BaseResponse("Room with id " + roomId + " does not exist.", false, false, null);
//        } else if (!room.getUsers().contains(username)) {
//            return new BaseResponse("You are not a member of this room.", false, false);
//        } else {
//            return new BaseResponse("Bet slips fetched successfully.", true, false, betSlipPagingRepository.findAllByRoomIdAndDateAfter(roomId, LocalDateTime.now().minusHours(6), pageable));
//        }
//    }
//
//    public BaseResponse getRoom(String roomId, String username) {
//        User user = userRepository.findByUsername(username);
//        Room room = roomRepository.findById(roomId).orElse(null);
//        if (room == null) {
//            return new BaseResponse("Room with id " + roomId + " does not exist.", false, false, null);
//        } else if (!room.getUsers().contains(username)) {
//            return new BaseResponse("You are not a member of this room.", false, false);
//        } else if (user == null) {
//            return new BaseResponse("User with username " + username + " does not exist.", false, false, null);
//        } else {
//            return new BaseResponse("Room fetched successfully.", true, false, room);
//        }
//    }
//
//    public BaseResponse getRoomMetadata(String roomId, String username) {
//        Room room = roomRepository.findById(roomId).orElse(null);
//        if (room == null) {
//            return new BaseResponse("Room with id " + roomId + " does not exist.", false, false, null);
//        } else if (!room.getUsers().contains(username)) {
//            return new BaseResponse("You are not a member of this room.", false, false);
//        } else {
//            RoomMetadataResponse roomMetadataResponse = new RoomMetadataResponse();
//            roomMetadataResponse.setRoom(room);
//            roomMetadataResponse.setOwner(room.getOwner().equals(username));
//
//            List<Map.Entry<String, Integer>> list = new ArrayList<>(room.getUserCorrectPredictions().entrySet());
//            list.sort(Map.Entry.comparingByValue());
//            Map<String, Integer> sortedMap = new LinkedHashMap<>();
//            for (Map.Entry<String, Integer> entry : list) {
//                sortedMap.put(entry.getKey(), entry.getValue());
//            }
//
//            roomMetadataResponse.setRankPredictions(sortedMap);
//            List<User> users = userRepository.findByUsernameIn(room.getUsers());
//            // Return most rich three people by their balance, return List<User> instead of List<Map.Entry<String, Double>>
//            roomMetadataResponse.setRiches(users.stream()
//                    .sorted((u1, u2) -> u2.getBalance().compareTo(u1.getBalance()))
//                    .limit(3).collect(Collectors.toList()));
//
//            return new BaseResponse("Room metadata fetched successfully.", true, false, roomMetadataResponse);
//        }
//    }
//
//    public BaseResponse listPublicRooms(Pageable pageable) {
//        ListPublicRoomsResponse listPublicRoomsResponse = new ListPublicRoomsResponse();
//        Page<Room> rooms = roomPagingRepository.findAllByIsPublic(pageable, true);
//        if (rooms != null && !rooms.isEmpty()) {
//            listPublicRoomsResponse.setRooms(rooms);
//        }
//        return new BaseResponse("Public rooms fetched successfully.", true, false, listPublicRoomsResponse);
//    }
//
//    public BaseResponse joinPublicRoom(String username, String roomId) {
//        User user = userRepository.findByUsername(username);
//        Room room = roomRepository.findById(roomId).orElse(null);
//        if (room == null) {
//            return new BaseResponse("Room with id " + roomId + " does not exist.", false, false, null);
//        } else if (room.getUsers().contains(username)) {
//            return new BaseResponse("You are already a member of this room.", false, false);
//        }else if (user == null) {
//            return new BaseResponse("User with username " + username + " does not exist.", false, false, null);
//        } else {
//            room.getUsers().add(username);
//            room.getUserCorrectPredictions().put(username, 0);
//            user.getRooms().add(roomId);
//            userRepository.save(user);
//            Room savedRoom = roomRepository.save(room);
//            return new BaseResponse("You have joined the room successfully.", true, true, savedRoom);
//        }
//    }
}
