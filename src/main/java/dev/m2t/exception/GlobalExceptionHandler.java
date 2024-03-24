package dev.m2t.exception;


import dev.m2t.dto.BaseResponse;
import io.quarkus.logging.Log;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class GlobalExceptionHandler implements ExceptionMapper<Exception> {

    @Override
    public Response toResponse(Exception exception) {
        return mapExceptionToResponse(exception);
    }

    private Response mapExceptionToResponse(Exception exception) {
        if (exception instanceof WebApplicationException) {
            Response originalErrorResponse = ((WebApplicationException) exception).getResponse();
            return Response.fromResponse(originalErrorResponse)
                    .entity(new BaseResponse<>(500, exception.getMessage(), false))
                    .build();
        }
        else if (exception instanceof IllegalArgumentException) {
            return Response.status(400).entity(new BaseResponse<>(400, exception.getMessage(), false)).build();
        }
        else if (exception instanceof NoActiveAuctionsException) {
            return Response.status(404).entity(new BaseResponse<>(404, exception.getMessage(), false)).build();
        }
        else {
            Log.error("Internal server error", exception);
            return Response.serverError().entity(new BaseResponse<>(500, "Internal server error.", false)).build();
        }
    }
}