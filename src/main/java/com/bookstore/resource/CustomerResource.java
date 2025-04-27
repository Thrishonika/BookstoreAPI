
package com.bookstore.resource;

import com.bookstore.exception.CustomerNotFoundException;
import com.bookstore.exception.InvalidInputException;
import com.bookstore.model.Customer;
import com.bookstore.store.DataStore;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("/customers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CustomerResource {

    @GET
    public List<Customer> getAllCustomers() {
        return new ArrayList<>(DataStore.customers.values());
    }

    @GET
    @Path("/{id}")
    public Customer getCustomerById(@PathParam("id") int id) {
        Customer customer = DataStore.customers.get(id);
        if (customer == null) {
            throw new CustomerNotFoundException("Customer not found");
        }
        return customer;
    }

    @POST
    public Response addCustomer(Customer customer) {
        validateCustomer(customer);
        int id = generateNewId(); // Handle ID generation
        customer.setId(id);        // Assuming `Customer` has a `setId` method
        DataStore.customers.put(id, customer);
        return Response.status(Response.Status.CREATED).entity(customer).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateCustomer(@PathParam("id") int id, Customer updatedCustomer) {
        Customer existingCustomer = DataStore.customers.get(id);
        validateCustomer(updatedCustomer);
        if (existingCustomer != null) {
            existingCustomer.setName(updatedCustomer.getName());
            existingCustomer.setEmail(updatedCustomer.getEmail());
            existingCustomer.setPassword(updatedCustomer.getPassword());
            return Response.ok(existingCustomer).build();
        }
        throw new CustomerNotFoundException("Customer not found");
    }

    @DELETE
    @Path("/{id}")
    public Response deleteCustomer(@PathParam("id") int id) {
        Customer customer = DataStore.customers.remove(id);
        if (customer == null) {
            throw new CustomerNotFoundException("Customer not found");
        }
        return Response.noContent().build();
    }

    // Generate a new unique ID (simple logic here)
    private int generateNewId() {
        return DataStore.customers.size() + 1; // Just a simple example
    }
    //  Validate Customer input
    private void validateCustomer(Customer customer) {
        if (customer.getName() == null || customer.getName().trim().isEmpty()) {
            throw new InvalidInputException("Customer name must not be empty.");
        }
        if (customer.getEmail() == null || customer.getEmail().trim().isEmpty()) {
            throw new InvalidInputException("Customer email must not be empty.");
        }
        
    }
}