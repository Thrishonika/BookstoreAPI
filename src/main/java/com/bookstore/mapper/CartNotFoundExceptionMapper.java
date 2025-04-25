
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

   public static class ErrorMessage {
        private String error;
        private String message;

        public ErrorMessage() {}  // Required for JSON-B

        public ErrorMessage(String error, String message) {
            this.error = error;
            this.message = message;
        }

        public String getError() { return error; }
        public String getMessage() { return message; }

        public void setError(String error) { this.error = error; }
        public void setMessage(String message) { this.message = message; }
    }
}