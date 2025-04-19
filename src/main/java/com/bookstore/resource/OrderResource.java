
package com.bookstore.resource;

/**
 *
 * @author ADMIN
 */

import com.bookstore.model.Order;
import com.bookstore.store.DataStore;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.time.LocalDate;
import java.util.Map;

@Path("/orders")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrderResource {

    @GET
    public Map<Integer, Order> getAllOrders() {
        return DataStore.orders; // Return orders from DataStore (Map)
    }

    @GET
    @Path("/{id}")
    public Order getOrderById(@PathParam("id") int id) {
        Order order = DataStore.orders.get(id);
        if (order == null) {
            throw new NotFoundException("Order not found");
        }
        return order;
    }

    @POST
    public Response placeOrder(Order order) {
        int newId = DataStore.orders.size() + 1;  // Generate a new ID based on the current size of the orders Map
        order.setId(newId);
        order.setOrderDate(LocalDate.now());
        DataStore.orders.put(newId, order); // Use put to store the order in the Map
        return Response.status(Response.Status.CREATED).entity(order).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteOrder(@PathParam("id") int id) {
        Order order = DataStore.orders.remove(id);
        if (order == null) {
            throw new NotFoundException("Order not found");
        }
        return Response.noContent().build();
    }
}
