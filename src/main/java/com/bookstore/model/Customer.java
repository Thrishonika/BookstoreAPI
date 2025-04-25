package com.bookstore.model;
/**
 *
 * @author ADMIN
 */
public class Customer {
      private int id;
      private String name;
      private String email;
      private String password;


    public Customer() {}
    public Customer(int id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    @Override
    public String toString() {
    return "Customer{" +
           "id=" + id +
           ", name='" + name + '\'' +
           ", email='" + email + '\'' +
           '}';
    // (Do not include password here for security reasons)
}

}

/*import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.*;

// Data Models
class Customer {
    public int id;
    public String name;
    public String email;
    public String password;

    public Customer(int id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }
}

class CartItem {
    public int bookId;
    public int quantity;

    public CartItem(int bookId, int quantity) {
        this.bookId = bookId;
        this.quantity = quantity;
    }
}

class Order {
    public int id;
    public int customerId;
    public List<CartItem> items;
    public Date orderDate;

    public Order(int id, int customerId, List<CartItem> items) {
        this.id = id;
        this.customerId = customerId;
        this.items = items;
        this.orderDate = new Date();
    }
}

// Exception Classes
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

@Provider
class InvalidInputMapper implements ExceptionMapper<InvalidInputException> {
    public Response toResponse(InvalidInputException ex) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity("{\"error\":\"Invalid input\"}").build();
    }
}

@Provider
class OutOfStockMapper implements ExceptionMapper<OutOfStockException> {
    public Response toResponse(OutOfStockException ex) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity("{\"error\":\"Book out of stock\"}").build();
    }
}

@Provider
class CartNotFoundMapper implements ExceptionMapper<CartNotFoundException> {
    public Response toResponse(CartNotFoundException ex) {
        return Response.status(Response.Status.NOT_FOUND)
                .entity("{\"error\":\"Cart not found\"}").build();
    }
}

// Resource Classes
@Path("/customers")
class CustomerResource {
    private static Map<Integer, Customer> customers = new HashMap<>();
    private static int idCounter = 1;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createCustomer(Customer customer) {
        customer.id = idCounter++;
        customers.put(customer.id, customer);
        return Response.status(Response.Status.CREATED).entity(customer).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<Customer> getCustomers() {
        return customers.values();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Customer getCustomer(@PathParam("id") int id) {
        Customer c = customers.get(id);
        if (c == null) throw new CustomerNotFoundException();
        return c;
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateCustomer(@PathParam("id") int id, Customer updated) {
        if (!customers.containsKey(id)) throw new CustomerNotFoundException();
        updated.id = id;
        customers.put(id, updated);
        return Response.ok(updated).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteCustomer(@PathParam("id") int id) {
        if (customers.remove(id) == null) throw new CustomerNotFoundException();
        return Response.noContent().build();
    }
}

@Path("/customers/{customerId}/cart")
class CartResource {
    private static Map<Integer, List<CartItem>> carts = new HashMap<>();

    @POST
    @Path("items")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addItem(@PathParam("customerId") int customerId, CartItem item) {
        List<CartItem> cart = carts.computeIfAbsent(customerId, k -> new ArrayList<>());
        cart.add(item);
        return Response.status(Response.Status.CREATED).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<CartItem> getCart(@PathParam("customerId") int customerId) {
        return carts.getOrDefault(customerId, new ArrayList<>());
    }

    @PUT
    @Path("items/{bookId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateItem(@PathParam("customerId") int customerId,
                               @PathParam("bookId") int bookId,
                               CartItem newItem) {
        List<CartItem> cart = carts.get(customerId);
        if (cart == null) throw new CartNotFoundException();
        for (CartItem item : cart) {
            if (item.bookId == bookId) {
                item.quantity = newItem.quantity;
                return Response.ok().build();
            }
        }
        throw new InvalidInputException();
    }

    @DELETE
    @Path("items/{bookId}")
    public Response removeItem(@PathParam("customerId") int customerId,
                               @PathParam("bookId") int bookId) {
        List<CartItem> cart = carts.get(customerId);
        if (cart == null) throw new CartNotFoundException();
        cart.removeIf(item -> item.bookId == bookId);
        return Response.noContent().build();
    }
}

@Path("/customers/{customerId}/orders")
class OrderResource {
    private static Map<Integer, List<Order>> orders = new HashMap<>();
    private static int orderCounter = 1;

    @POST
    public Response placeOrder(@PathParam("customerId") int customerId) {
        List<CartItem> cart = CartResource.carts.get(customerId);
        if (cart == null || cart.isEmpty()) throw new CartNotFoundException();

        List<Order> customerOrders = orders.computeIfAbsent(customerId, k -> new ArrayList<>());
        Order order = new Order(orderCounter++, customerId, new ArrayList<>(cart));
        customerOrders.add(order);
        cart.clear();
        return Response.status(Response.Status.CREATED).entity(order).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Order> getOrders(@PathParam("customerId") int customerId) {
        return orders.getOrDefault(customerId, new ArrayList<>());
    }

    @GET
    @Path("{orderId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Order getOrder(@PathParam("customerId") int customerId,
                          @PathParam("orderId") int orderId) {
        List<Order> customerOrders = orders.get(customerId);
        if (customerOrders != null) {
            for (Order order : customerOrders) {
                if (order.id == orderId) return order;
            }
        }
        throw new WebApplicationException(Response.Status.NOT_FOUND);
    }
}*/
