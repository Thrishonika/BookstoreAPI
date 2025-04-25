
package com.bookstore.mapper;

import com.bookstore.exception.CustomerNotFoundException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class CustomerNotFoundExceptionMapper implements ExceptionMapper<CustomerNotFoundException> {

    @Override
    public Response toResponse(CustomerNotFoundException exception) {
        return Response.status(Response.Status.NOT_FOUND)
                .entity(new ErrorMessage("Customer Not Found", exception.getMessage()))
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