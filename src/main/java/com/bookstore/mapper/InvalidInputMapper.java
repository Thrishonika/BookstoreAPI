/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bookstore.mapper;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;

/**
 *
 * @author ADMIN
 */

@Provider
class InvalidInputMapper implements ExceptionMapper<InvalidInputException> {
    public Response toResponse(InvalidInputException ex) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity("{\"error\":\"Invalid input\"}").build();
    }
}
