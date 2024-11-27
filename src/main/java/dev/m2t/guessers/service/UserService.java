package dev.m2t.guessers.service;

import dev.m2t.guessers.dto.BaseResponse;
import dev.m2t.guessers.dto.request.ChangePasswordRequest;
import dev.m2t.guessers.model.RoomInvite;
import dev.m2t.guessers.model.User;
import dev.m2t.guessers.model.enums.RoomInviteStatusEnum;
import dev.m2t.guessers.repository.RoomInviteRepository;
import dev.m2t.guessers.repository.UserRepository;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Value("${keycloak.realm}")
    private String realm;

    private final Keycloak keycloak;
    private final UserRepository userRepository;
    private final RoomInviteRepository roomInviteRepository;

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public UserService(UserRepository userRepository, RoomInviteRepository roomInviteRepository, Keycloak keycloak) {
        this.userRepository = userRepository;
        this.roomInviteRepository = roomInviteRepository;
        this.keycloak = keycloak;
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

    public BaseResponse changePassword(ChangePasswordRequest changePasswordRequest) {
        logger.info("Changing password for user: {}", changePasswordRequest.getUsername());
        List<UserRepresentation> users =  keycloak.realm(realm).users().searchByUsername(changePasswordRequest.getUsername(), true);
        if(users.isEmpty()) {
            throw new UsernameNotFoundException("User with username " + changePasswordRequest.getUsername() + " not found.");
        }

        UserRepresentation user = users.get(0);

        keycloak.realm(realm).users().get(user.getId()).resetPassword(new CredentialRepresentation() {{
            setType(CredentialRepresentation.PASSWORD);
            setValue(changePasswordRequest.getPassword());
        }});

        return new BaseResponse("Password changed successfully", true, false);

    }
}
