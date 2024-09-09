package dev.m2t.guessers.service;

import dev.m2t.guessers.dto.BaseResponse;
import dev.m2t.guessers.model.RoomInvite;
import dev.m2t.guessers.model.User;
import dev.m2t.guessers.model.enums.RoomInviteStatusEnum;
import dev.m2t.guessers.repository.RoomInviteRepository;
import dev.m2t.guessers.repository.UserRepository;
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
