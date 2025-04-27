
package com.bookstore.resource;

import com.bookstore.exception.CartNotFoundException;
import com.bookstore.exception.InvalidInputException;
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
        List<CartItem> cartItems = DataStore.getCart(customerId);
        if (cartItems == null || cartItems.isEmpty()) {
            throw new CartNotFoundException("Cart not found for customer ID: " + customerId);
        }
        return cartItems;
    }

    // Add an item to the cart for a specific customer
    @POST
    @Path("/items")
    public Response addCartItem(@PathParam("customerId") int customerId, CartItem item) {
         validateCartItem(item);
        DataStore.addCartItem(customerId, item); // Add the cart item for the given customer
        return Response.status(Response.Status.CREATED).entity(item).build();
    }
    @PUT
    @Path("/items/{bookId}")
    public Response updateCartItem(
        @PathParam("customerId") int customerId,
        @PathParam("bookId") int bookId,
        CartItem updatedItem
    ) {
        validateCartItem(updatedItem);
        DataStore.updateCartItem(customerId, bookId, updatedItem);
        return Response.ok(updatedItem).build();
    }


    // Remove an item from the cart for a specific customer
    @DELETE
    @Path("/items/{bookId}")
    public Response removeCartItem(@PathParam("customerId") int customerId, @PathParam("bookId") int bookId) {
        List<CartItem> cartItems = DataStore.getCart(customerId);
        if (cartItems == null || cartItems.isEmpty()) {
            throw new CartNotFoundException("Cannot remove item. Cart not found for customer ID: " + customerId);
        }
        DataStore.removeCartItem(customerId, bookId);
        return Response.noContent().build();
    }

    // Clear the entire cart for a specific customer (if needed)
    @DELETE
    public Response clearCart(@PathParam("customerId") int customerId) {
        DataStore.clearCart(customerId); // Clear the cart for the given customer
        return Response.noContent().build();
    }
    //  Validation logic for CartItem
    private void validateCartItem(CartItem item) {
        if (item == null) {
            throw new InvalidInputException("Cart item cannot be null.");
        }
        if (item.getBookId() <= 0) {
            throw new InvalidInputException("Invalid book ID.");
        }
        if (item.getQuantity() <= 0) {
            throw new InvalidInputException("Quantity must be greater than zero.");
        }
    }
}