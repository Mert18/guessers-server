package dev.m2t.resource;

import dev.m2t.dto.BaseResponse;
import dev.m2t.service.UserService;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import org.jboss.resteasy.reactive.RestResponse;

@Path("/api/public")
public class PublicResource {

    @Inject
    UserService userService;

    @GET
    @Path("/generate-user")
    public RestResponse<BaseResponse> generateUser() {
        return RestResponse.ResponseBuilder
                .ok(userService.generateUser())
                .status(RestResponse.Status.CREATED)
                .build();
    }
}
