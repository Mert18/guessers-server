package dev.m2t.resource;

import dev.m2t.dto.BaseResponse;
import dev.m2t.model.User;
import dev.m2t.service.UserService;
import io.quarkus.security.identity.SecurityIdentity;
import io.smallrye.mutiny.Uni;
import jakarta.annotation.security.PermitAll;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import org.jboss.resteasy.reactive.NoCache;
import org.jboss.resteasy.reactive.RestResponse;

import java.util.Set;

@Path("/api/users")
public class UserResource {

    @Inject
    SecurityIdentity identity;

    @Inject
    UserService userService;

    @GET
    @Path("/me")
    @NoCache
    public User me() {
        return new User(identity);
    }
}