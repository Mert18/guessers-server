package dev.m2t.service;

import dev.m2t.dto.request.GenerateUserRequest;
import dev.m2t.exception.UsernameAlreadyExistsException;
import dev.m2t.model.User;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;

import java.util.List;

@ApplicationScoped
public class KeycloakService {
    @ConfigProperty(name = "keycloak.grant.type")
    String grantType;
    @ConfigProperty(name = "keycloak.server.url")
    String serverUrl;
    @ConfigProperty(name = "keycloak.realm.master")
    String masterRealm;
    @ConfigProperty(name = "keycloak.admin.username")
    String adminUsername;
    @ConfigProperty(name = "keycloak.admin.password")
    String adminPassword;
    @ConfigProperty(name = "keycloak.admin.client.id")
    String adminClientId;

    @ConfigProperty(name = "keycloak.realm.name")
    String realm;

    @Inject
    Keycloak keycloak;

    @Transactional
    public User generateUser(GenerateUserRequest user) {
        // Fill in user details
        String password = generatePassword();
        UserRepresentation newUser = fillInUserDetails(user, password);

        Log.info("roles: " + keycloak.realm(realm).roles().list());
        UsersResource users = keycloak.realm(realm).users();
        if(!users.searchByUsername(user.getUsername(), true).isEmpty()) {
            Log.error("Username " + user.getUsername() + " already exists");
            throw new UsernameAlreadyExistsException("Username already exists");
        }

        // Calculate Luck
        Double luck = calculateLuck();
        Double balance = luck * luck * 100;

        // Save user to db.
        User dbUser = new User();
        dbUser.setUsername(user.getUsername());
        dbUser.setLuckPercentage(luck);
        dbUser.setBalance(balance);
        dbUser.persist();

        // Save user to keycloak
        users.create(newUser);

        Log.info("User " + user.getUsername() + " created successfully. Password: " + password);

        return dbUser;
    }

    private Double calculateLuck() {
        return Math.random() * 100;
    }

    private UserRepresentation fillInUserDetails(GenerateUserRequest user, String password) {
        UserRepresentation newUser = new UserRepresentation();
        newUser.setUsername(user.getUsername());
        newUser.setId(user.getUsername());
        newUser.setRealmRoles(List.of("user"));
        newUser.setEnabled(true);
        newUser.setCredentials(List.of(
                new CredentialRepresentation() {{
                    setType(CredentialRepresentation.PASSWORD);
                    setValue(password);
                }}
        ));
        return newUser;
    }

    private String generatePassword() {
        return "password";
    }
}