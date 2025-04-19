/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bookstore.resource;

import com.bookstore.model.Customer;
import com.bookstore.store.DataStore;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.NotFoundException;
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
            throw new NotFoundException("Customer not found");
        }
        return customer;
    }

    @POST
    public Response addCustomer(Customer customer) {
        int id = generateNewId(); // Handle ID generation
        customer.setId(id);        // Assuming `Customer` has a `setId` method
        DataStore.customers.put(id, customer);
        return Response.status(Response.Status.CREATED).entity(customer).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateCustomer(@PathParam("id") int id, Customer updatedCustomer) {
        Customer existingCustomer = DataStore.customers.get(id);
        if (existingCustomer != null) {
            existingCustomer.setName(updatedCustomer.getName());
            existingCustomer.setEmail(updatedCustomer.getEmail());
            existingCustomer.setPassword(updatedCustomer.getPassword());
            return Response.ok(existingCustomer).build();
        }
        throw new NotFoundException("Customer not found");
    }

    @DELETE
    @Path("/{id}")
    public Response deleteCustomer(@PathParam("id") int id) {
        Customer customer = DataStore.customers.remove(id);
        if (customer == null) {
            throw new NotFoundException("Customer not found");
        }
        return Response.noContent().build();
    }

    // Generate a new unique ID (simple logic here)
    private int generateNewId() {
        return DataStore.customers.size() + 1; // Just a simple example
    }
}

