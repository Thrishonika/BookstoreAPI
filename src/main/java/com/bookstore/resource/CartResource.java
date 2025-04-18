/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bookstore.resource;

/**
 *
 * @author ADMIN
 */

import com.bookstore.model.CartItem;
import com.bookstore.model.Book;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.ArrayList;
import java.util.List;

@Path("/cart")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CartResource {

    private final static List<CartItem> cart = new ArrayList<>();

    @GET
    public List<CartItem> getCartItems() {
        return cart;
    }

    @POST
    public Response addCartItem(CartItem item) {
        cart.add(item);
        return Response.status(Response.Status.CREATED).entity(item).build();
    }

    @DELETE
    @Path("/{bookId}")
    public Response removeCartItem(@PathParam("bookId") int bookId) {
        CartItem item = cart.stream()
                .filter(ci -> ci.getBook().getId() == bookId)
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Item not found in cart"));
        cart.remove(item);
        return Response.noContent().build();
    }

    @DELETE
    public Response clearCart() {
        cart.clear();
        return Response.noContent().build();
    }
}


