package dev.m2t.service;

import dev.m2t.dto.UserDto;
import dev.m2t.dto.request.GenerateUserRequest;
import dev.m2t.exception.UsernameAlreadyExistsException;
import dev.m2t.model.User;
import dev.m2t.model.converter.UserDtoConverter;
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
import java.util.Locale;
import java.util.Random;

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

    @Inject
    UserDtoConverter userDtoConverter;

    @Transactional
    public UserDto generateUser(GenerateUserRequest user) {
        // Fill in user details
        String identityNumber = randomStringGenerator(true, 3);

        UsersResource users = keycloak.realm(realm).users();

        String generatedName = randomStringGenerator(false, 3);
        while(!users.searchByUsername(generatedName, true).isEmpty()) {
            generatedName = randomStringGenerator(false, 3);
        }


        // Calculate Luck
        Double luck = calculateLuck();
        Double balance = luck * luck * 100;
        // Save user to db.
        User dbUser = new User(generatedName, identityNumber, balance, luck, user.getWantedDollars(), user.getWantedName());
        dbUser.persist();

        UserRepresentation newUser = fillInUserDetails(identityNumber, generatedName);
        // Save user to keycloak
        users.create(newUser);

        Log.info("User " + generatedName + " created successfully. Identity number: " + identityNumber);
        return userDtoConverter.convertToDto(dbUser);
    }

    private Double calculateLuck() {
        return Math.random() * 100;
    }

    public static String randomStringGenerator(boolean isIdentityNumber, int length) {
        String characters;
        if(isIdentityNumber) {
            characters = "0123456789";
        }else {
            characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        }

        StringBuilder result = new StringBuilder(3);
        Random rnd = new Random();

        for (int i = 0; i < length; i++) {
            result.append(characters.charAt(rnd.nextInt(characters.length())));
        }

        return result.toString();
    }

    private UserRepresentation fillInUserDetails(String identityNumber, String generatedName) {
        UserRepresentation newUser = new UserRepresentation();
        newUser.setUsername(generatedName);
        newUser.setId(generatedName);
        newUser.setRealmRoles(List.of("user"));
        newUser.setEnabled(true);
        newUser.setCredentials(List.of(
                new CredentialRepresentation() {{
                    setType(CredentialRepresentation.PASSWORD);
                    setValue(identityNumber);
                }}
        ));
        return newUser;
    }
}