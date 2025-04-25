
package com.bookstore.resource;

import com.bookstore.model.CartItem;
import com.bookstore.store.DataStore;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/customers/{customerId}/cart")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CartResource {

    // Get all items in the cart for a specific customer
    @GET
    public List<CartItem> getCartItems(@PathParam("customerId") int customerId) {
        return DataStore.getCart(customerId); // Fetch the cart for the given customer
    }

    // Add an item to the cart for a specific customer
    @POST
    public Response addCartItem(@PathParam("customerId") int customerId, CartItem item) {
        DataStore.addCartItem(customerId, item); // Add the cart item for the given customer
        return Response.status(Response.Status.CREATED).entity(item).build();
    }

    // Remove an item from the cart for a specific customer
    @DELETE
    @Path("/items/{bookId}")
    public Response removeCartItem(@PathParam("customerId") int customerId, @PathParam("bookId") int bookId) {
        DataStore.removeCartItem(customerId, bookId); // Remove the cart item for the given customer
        return Response.noContent().build();
    }

    // Clear the entire cart for a specific customer (if needed)
    @DELETE
    public Response clearCart(@PathParam("customerId") int customerId) {
        DataStore.clearCart(customerId); // Clear the cart for the given customer
        return Response.noContent().build();
    }
}