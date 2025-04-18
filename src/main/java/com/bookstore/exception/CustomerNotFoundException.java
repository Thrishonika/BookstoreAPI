/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bookstore.exception;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.*;
/**
 *
 * @author ADMIN
 */
class CustomerNotFoundException extends RuntimeException {}
class InvalidInputException extends RuntimeException {}
class OutOfStockException extends RuntimeException {}
class CartNotFoundException extends RuntimeException {}

@Provider
class CustomerNotFoundMapper implements ExceptionMapper<CustomerNotFoundException> {
    public Response toResponse(CustomerNotFoundException ex) {
        return Response.status(Response.Status.NOT_FOUND)
                .entity("{\"error\":\"Customer not found\"}").build();
    }
}
