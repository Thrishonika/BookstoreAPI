
package com.bookstore.resource;

import com.bookstore.exception.BookNotFoundException;
import com.bookstore.exception.CustomerNotFoundException;
import com.bookstore.exception.InvalidInputException;
import com.bookstore.exception.OutOfStockException;
import com.bookstore.model.Order;
import com.bookstore.model.CartItem;
import com.bookstore.store.DataStore;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/customers/{customerId}/orders")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrderResource {

    @GET
    public Response getAllOrders() {
        return Response.ok(DataStore.orders).build();
    }

    @GET
    @Path("/{id}")
    public Response getOrderById(@PathParam("id") int id) {
        Order order = DataStore.orders.get(id);
        if (order == null) {
            throw new CustomerNotFoundException("Order with ID " + id + " not found.");
        }
        return Response.ok(order).build();
    }

    @POST
    public Response placeOrder(@PathParam("customerId") int customerId, List<CartItem> cartItems) {
        validateCartItems(cartItems);

        // Check stock before creating order
        for (CartItem item : cartItems) {
            int bookId = item.getBookId();
            int quantity = item.getQuantity();

            int availableStock = DataStore.getStockForBook(bookId);  // Assume you have a way to get stock
            if (availableStock == -1) {
                throw new BookNotFoundException("Book with ID " + bookId + " not found.");
            }

            if (availableStock < quantity) {
                throw new OutOfStockException("Not enough stock for Book ID " + bookId);
            }
        }

        Order order = DataStore.createOrder(customerId, cartItems);
        return Response.status(Response.Status.CREATED).entity(order).build();
    }


    @DELETE
    @Path("/{id}")
    public Response deleteOrder(@PathParam("id") int id) {
        Order order = DataStore.orders.remove(id);
        if (order == null) {
            throw new CustomerNotFoundException("Order with ID " + id + " not found.");
        }
        return Response.noContent().build();
    }

    //  Validate cart items before placing an order
    private void validateCartItems(List<CartItem> cartItems) {
        if (cartItems == null || cartItems.isEmpty()) {
            throw new InvalidInputException("Cart items must not be empty.");
        }
        
        for (CartItem item : cartItems) {
            if (item.getBookId() <= 0) {
                throw new InvalidInputException("Invalid book ID in cart item.");
            }
            if (item.getQuantity() <= 0) {
                throw new InvalidInputException("Quantity must be greater than zero.");
            }
        }
    }
}
