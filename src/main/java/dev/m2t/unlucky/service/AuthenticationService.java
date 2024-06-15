package dev.m2t.unlucky.service;

import dev.m2t.unlucky.dto.BaseResponse;
import dev.m2t.unlucky.dto.request.CreateUserRequest;
import dev.m2t.unlucky.exception.UsernameAlreadyExistsException;
import dev.m2t.unlucky.model.User;
import dev.m2t.unlucky.repository.UserRepository;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthenticationService {
    @Value("${keycloak.realm}")
    private String realm;

    private final Keycloak keycloak;
    private final UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);

    public AuthenticationService(Keycloak keycloak, UserRepository userRepository) {
        this.keycloak = keycloak;
        this.userRepository = userRepository;
    }

    public BaseResponse createUser(CreateUserRequest createUserRequest) {
        logger.debug("Creating user with username: {}", createUserRequest.getUsername());
        String username = createUserRequest.getUsername();
        UsersResource users = keycloak.realm(realm).users();
        if(!users.searchByUsername(username, true).isEmpty()) {
            logger.debug("Username already exists: {}", username);
            throw new UsernameAlreadyExistsException("Username already exists: " + username);
        }

        // Calculate Luck
        Double luck = Math.random() * 100;
        Double balance = luck * luck * 100;

        // Save user to db.
        User dbUser = new User(username, balance, luck);
        userRepository.save(dbUser);
        logger.debug("User {} saved to db", username);

        // Save user to keycloak
        users.create(fillKeycloakUserDetails(createUserRequest));
        logger.debug("User {} saved to keycloak", username);

        logger.info("User {} created successfully.", username);
        return new BaseResponse("User created successfully", true, true, dbUser);
    }

    private UserRepresentation fillKeycloakUserDetails(CreateUserRequest createUserRequest) {
        UserRepresentation newUser = new UserRepresentation();
        newUser.setUsername(createUserRequest.getUsername());
        newUser.setEmail(createUserRequest.getUsername() + "@unlucky.biz");
        newUser.setFirstName(createUserRequest.getUsername());
        newUser.setLastName(createUserRequest.getUsername());
        newUser.setId(createUserRequest.getUsername());
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
}
