/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bookstore.resource;

/**
 *
 * @author ADMIN
 */

import com.bookstore.model.Order;
import com.bookstore.model.CartItem;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Path("/orders")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrderResource {

    private final static List<Order> orders = new ArrayList<>();
    private static int currentId = 1;

    @GET
    public List<Order> getAllOrders() {
        return orders;
    }

    @GET
    @Path("/{id}")
    public Order getOrderById(@PathParam("id") int id) {
        return orders.stream()
                .filter(o -> o.getId() == id)
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Order not found"));
    }

    @POST
    public Response placeOrder(Order order) {
        order.setId(currentId++);
        order.setOrderDate(LocalDate.now());
        orders.add(order);
        return Response.status(Response.Status.CREATED).entity(order).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteOrder(@PathParam("id") int id) {
        Order order = orders.stream()
                .filter(o -> o.getId() == id)
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Order not found"));
        orders.remove(order);
        return Response.noContent().build();
    }
}


