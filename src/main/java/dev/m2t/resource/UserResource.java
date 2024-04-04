package dev.m2t.resource;

import dev.m2t.dto.request.UserBalanceRequest;
import dev.m2t.service.UserService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("/api/user")
public class UserResource {
    @Inject
    private final UserService userService;

    public UserResource(UserService userService) {
        this.userService = userService;
    }

    @POST
    @Path("/balance")
    public Response getUserBalance(UserBalanceRequest userBalanceRequest) {
        return Response.ok(userService.getUserBalance(userBalanceRequest)).build();
    }
}
