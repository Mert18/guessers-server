package dev.m2t.resource;

import dev.m2t.dto.request.GenerateUserRequest;
import dev.m2t.model.User;
import dev.m2t.service.KeycloakService;
import io.quarkus.panache.common.Sort;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;


@ApplicationScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("/api/public")
public class PublicResource {
    @Inject
    KeycloakService keycloakService;

    @GET
    @Path("/users")
    public Response getAllUsers() {
        return Response.ok(User.listAll(Sort.by("username"))).build();
    }

    @POST
    @Path("/create-user")
    public Response createUser(GenerateUserRequest user) {
        User savedUser = keycloakService.generateUser(user);
        return Response.ok(savedUser).build();
    }
}
