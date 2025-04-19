package com.bookstore.resource;

import com.bookstore.model.CartItem;
import com.bookstore.store.DataStore;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/customers/{customerId}/cart")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CartResource {

    // Get all items in the cart for a specific customer
    @GET
    public List<CartItem> getCartItems(@PathParam("customerId") int customerId) {
        // You should filter cart items by customerId if needed
        return DataStore.cart; // Currently returns all cart items
    }

    // Add an item to the cart for a specific customer
    @POST
    public Response addCartItem(@PathParam("customerId") int customerId, CartItem item) {
        // Add the cart item logic here, maybe associate it with customerId if needed
        DataStore.cart.add(item);
        return Response.status(Response.Status.CREATED).entity(item).build();
    }

    // Remove an item from the cart for a specific customer
    @DELETE
    @Path("/items/{bookId}")
    public Response removeCartItem(@PathParam("customerId") int customerId, @PathParam("bookId") int bookId) {
        CartItem item = DataStore.cart.stream()
                .filter(ci -> ci.getBook().getId() == bookId)
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Item not found in cart"));
        DataStore.cart.remove(item);
        return Response.noContent().build();
    }

    // Clear the entire cart for a specific customer (if needed)
    @DELETE
    public Response clearCart(@PathParam("customerId") int customerId) {
        DataStore.cart.clear(); // This removes all items from the cart
        return Response.noContent().build();
    }
}




