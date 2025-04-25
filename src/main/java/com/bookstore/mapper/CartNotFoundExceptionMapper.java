
package com.bookstore.mapper;

import com.bookstore.exception.CartNotFoundException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class CartNotFoundExceptionMapper implements ExceptionMapper<CartNotFoundException> {

    @Override
    public Response toResponse(CartNotFoundException exception) {
        return Response.status(Response.Status.NOT_FOUND) // Or another appropriate status code
                .entity(new ErrorMessage("Cart Not Found", exception.getMessage()))
                .type(javax.ws.rs.core.MediaType.APPLICATION_JSON)
                .build();
    }

    // (Inner class ErrorMessage - same as in BookNotFoundExceptionMapper)
    private static class ErrorMessage {
        private final String error;
        private final String message;

        public ErrorMessage(String error, String message) {
            this.error = error;
            this.message = message;
        }

        public String getError() { return error; }
        public String getMessage() { return message; }
    }
}

