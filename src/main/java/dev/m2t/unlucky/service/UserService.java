package dev.m2t.unlucky.service;

import dev.m2t.unlucky.dto.BaseResponse;
import dev.m2t.unlucky.exception.RoomNotExistsException;
import dev.m2t.unlucky.model.Room;
import dev.m2t.unlucky.model.RoomInvite;
import dev.m2t.unlucky.model.RoomUser;
import dev.m2t.unlucky.model.User;
import dev.m2t.unlucky.model.enums.RoomInviteStatusEnum;
import dev.m2t.unlucky.repository.RoomInviteRepository;
import dev.m2t.unlucky.repository.RoomRepository;
import dev.m2t.unlucky.repository.RoomUserRepository;
import dev.m2t.unlucky.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoomInviteRepository roomInviteRepository;

    public UserService(UserRepository userRepository, RoomInviteRepository roomInviteRepository) {
        this.userRepository = userRepository;
        this.roomInviteRepository = roomInviteRepository;
    }

    public BaseResponse getPendingUserInvites(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User with username " + username + " not found."));

        List<RoomInvite> pendingUserInvites = roomInviteRepository.findAllByUserAndStatus(user, RoomInviteStatusEnum.PENDING);
        return new BaseResponse<>("User invites retrieved successfully", true, false, pendingUserInvites);
    }

    public BaseResponse getUser(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User with username " + username + " not found."));
        return new BaseResponse<>("User retrieved successfully", true, false, user);
    }
}
