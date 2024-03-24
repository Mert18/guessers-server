package dev.m2t.resource;

import dev.m2t.dto.request.GenerateItemRequest;
import dev.m2t.dto.request.UpdateItemRequest;
import dev.m2t.model.Item;
import dev.m2t.service.ItemService;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.lang.reflect.InvocationTargetException;

@ApplicationScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("/api/item")
//@RolesAllowed("admin")
public class ItemResource {
    @Inject
    ItemService itemService;

    @GET
    @Path("/list")
    public Response getAllItems() {
        return Response.ok(Item.listAll(Sort.by("startingPrice"))).build();
    }

    @POST
    @Path("/create")
    public Response createItem(GenerateItemRequest generateItemRequest) {
        return Response.ok(itemService.createItem(generateItemRequest)).build();
    }


    @PUT
    @Path("/update")
    public Response updateItem(UpdateItemRequest updateItemRequest) throws InvocationTargetException, IllegalAccessException {
        return Response.ok(itemService.updateItem(updateItemRequest)).build();
    }
}
