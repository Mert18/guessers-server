package dev.m2t.guessers.service;

import dev.m2t.guessers.dto.BaseResponse;
import dev.m2t.guessers.dto.request.CreateUserRequest;
import dev.m2t.guessers.exception.ResourceAlreadyExistsException;
import dev.m2t.guessers.model.Stats;
import dev.m2t.guessers.model.User;
import dev.m2t.guessers.repository.EventRepository;
import dev.m2t.guessers.repository.RoomRepository;
import dev.m2t.guessers.repository.StatsRepository;
import dev.m2t.guessers.repository.UserRepository;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AuthenticationService {
    @Value("${keycloak.realm}")
    private String realm;

    private final Keycloak keycloak;
    private final UserRepository userRepository;
    private final RoomRepository roomRepository;
    private final EventRepository eventRepository;
    private final StatsRepository statsRepository;

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);

    public AuthenticationService(Keycloak keycloak, UserRepository userRepository, RoomRepository roomRepository, EventRepository eventRepository, StatsRepository statsRepository) {
        this.keycloak = keycloak;
        this.userRepository = userRepository;
        this.roomRepository = roomRepository;
        this.eventRepository = eventRepository;
        this.statsRepository = statsRepository;
    }

    public BaseResponse<User> createUser(CreateUserRequest createUserRequest) {
        logger.info("Creating user with username: {}", createUserRequest.getUsername().toLowerCase());
        String username = createUserRequest.getUsername().toLowerCase();
        UsersResource users = keycloak.realm(realm).users();
        if(!users.searchByUsername(username, true).isEmpty() || userRepository.findByUsername(username).isPresent()) {
            throw new ResourceAlreadyExistsException("User", "username", username);
        }

        // Save user to keycloak
        users.create(fillKeycloakUserDetails(createUserRequest));

        // Calculate Luck
        Double luck = Math.random() * 100;

        // Save user to db.
        User dbUser = new User(username, luck);
        userRepository.save(dbUser);

        logger.info("User {} created successfully.", username);
        return new BaseResponse<>("User created successfully", true, true, dbUser);
    }

    private UserRepresentation fillKeycloakUserDetails(CreateUserRequest createUserRequest) {
        UserRepresentation newUser = new UserRepresentation();
        newUser.setUsername(createUserRequest.getUsername().toLowerCase());
        newUser.setEmail(createUserRequest.getUsername().toLowerCase() + "@guessers.biz");
        newUser.setFirstName(createUserRequest.getUsername().toLowerCase());
        newUser.setLastName(createUserRequest.getUsername().toLowerCase());
        newUser.setId(createUserRequest.getUsername().toLowerCase());
        newUser.setRealmRoles(List.of("user"));
        newUser.setEnabled(true);
        newUser.setCredentials(List.of(
                new CredentialRepresentation() {{
                    setType(CredentialRepresentation.PASSWORD);
                    setValue(createUserRequest.getPassword());
                }}
        ));
        return newUser;
    }

    @Scheduled(fixedRate = 3600000)
    public void stats() {
        logger.info("Updating the app user stats");
        Long userCount = userRepository.count();
        Long roomCount = roomRepository.count();
        Long eventCount = eventRepository.count();

        Stats stats = new Stats();
        stats.setUserCount(userCount);
        stats.setRoomCount(roomCount);
        stats.setEventCount(eventCount);
        stats.setLastUpdated(LocalDateTime.now());
        statsRepository.save(stats);
        logger.info("The app user stats updated successfully.");
    }

    public BaseResponse<Stats> getStats() {
        Stats latestStats = statsRepository.findFirstByOrderByLastUpdatedDesc();

        if (latestStats != null) {
            return new BaseResponse<>("Stats retrieved successfully", true, false, latestStats);
        } else {
            return new BaseResponse<>("No stats found", false, false, null);
        }
    }
}
