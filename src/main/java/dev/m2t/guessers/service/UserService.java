package dev.m2t.guessers.service;

import dev.m2t.guessers.dto.BaseResponse;
import dev.m2t.guessers.model.RoomInvite;
import dev.m2t.guessers.model.User;
import dev.m2t.guessers.model.enums.RoomInviteStatusEnum;
import dev.m2t.guessers.repository.RoomInvitePagingRepository;
import dev.m2t.guessers.repository.RoomInviteRepository;
import dev.m2t.guessers.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoomInvitePagingRepository roomInvitePagingRepository;

    public UserService(UserRepository userRepository, RoomInvitePagingRepository roomInvitePagingRepository) {
        this.userRepository = userRepository;
        this.roomInvitePagingRepository = roomInvitePagingRepository;
    }

    public BaseResponse getPendingUserInvites(String username, Pageable pageable) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User with username " + username + " not found."));

        Page<RoomInvite> pendingUserInvites = roomInvitePagingRepository.findAllByUserAndStatus(user, RoomInviteStatusEnum.PENDING, pageable);
        return new BaseResponse<>("User invites retrieved successfully", true, false, pendingUserInvites);
    }

    public BaseResponse getUser(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User with username " + username + " not found."));
        return new BaseResponse<>("User retrieved successfully", true, false, user);
    }
}
