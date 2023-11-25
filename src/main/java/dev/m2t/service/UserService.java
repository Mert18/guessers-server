package dev.m2t.service;

import dev.m2t.dto.BaseResponse;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.keycloak.admin.client.Keycloak;

@ApplicationScoped
public class UserService {
    @Inject
    Keycloak keycloak;

    public BaseResponse generateUser() {
        BaseResponse baseResponse = new BaseResponse(
            0001,
            "User generated successfully",
            true
        );
        // Give me a random number between 1000 and 100000
        // int randomNumber = (int) (Math.random() * 100000) + 1000;
        return baseResponse;
    }
}
