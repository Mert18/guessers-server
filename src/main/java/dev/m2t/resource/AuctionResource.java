package dev.m2t.resource;

import dev.m2t.service.AuctionService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
@Path("/api/auction")
public class AuctionResource {
    @Inject
    AuctionService auctionService;

    @GET
    @Path("/active")
    public Response getActiveAuction() {
        return Response.ok(auctionService.getActiveAuction()).build();
    }
}
