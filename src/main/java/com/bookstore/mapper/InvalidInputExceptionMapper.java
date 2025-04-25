
package com.bookstore.mapper;

import com.bookstore.exception.InvalidInputException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class InvalidInputExceptionMapper implements ExceptionMapper<InvalidInputException> {

    @Override
    public Response toResponse(InvalidInputException exception) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(new ErrorMessage("Invalid Input", exception.getMessage()))
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