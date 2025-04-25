
package com.bookstore.mapper;

import com.bookstore.exception.OutOfStockException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class OutOfStockExceptionMapper implements ExceptionMapper<OutOfStockException> {

    @Override
    public Response toResponse(OutOfStockException exception) {
        return Response.status(Response.Status.BAD_REQUEST) // Or another appropriate status code
                .entity(new ErrorMessage("Out of Stock", exception.getMessage()))
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