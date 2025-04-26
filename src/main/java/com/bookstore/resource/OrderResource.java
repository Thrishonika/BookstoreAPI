
package com.bookstore.resource;

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
            throw new OutOfStockException("Order not found");
        }
        return Response.ok(order).build();
    }

    @POST
    public Response placeOrder(@PathParam("customerId") int customerId, List<CartItem> cartItems) {
        if (cartItems == null || cartItems.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Cart items cannot be empty").build();
        }
        
        Order order = DataStore.createOrder(customerId, cartItems);
        return Response.status(Response.Status.CREATED).entity(order).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteOrder(@PathParam("id") int id) {
        Order order = DataStore.orders.remove(id);
        if (order == null) {
            throw new OutOfStockException("Order not found");
        }
        return Response.noContent().build();
    }
}
