/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bookstore.resource;

/**
 *
 * @author ADMIN
 */

import com.bookstore.model.Customer;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.ArrayList;
import java.util.List;

@Path("/customers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CustomerResource {

    private final static List<Customer> customers = new ArrayList<>();

    @GET
    public List<Customer> getAllCustomers() {
        return customers;
    }

    @GET
    @Path("/{id}")
    public Customer getCustomerById(@PathParam("id") int id) {
        return customers.stream()
                .filter(c -> c.getId() == id)
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Customer not found"));
    }

    @POST
    public Response addCustomer(Customer customer) {
        customers.add(customer);
        return Response.status(Response.Status.CREATED).entity(customer).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateCustomer(@PathParam("id") int id, Customer updatedCustomer) {
        for (Customer c : customers) {
            if (c.getId() == id) {
                c.setName(updatedCustomer.getName());
                c.setEmail(updatedCustomer.getEmail());
                c.setPassword(updatedCustomer.getPassword());
                return Response.ok(c).build();
            }
        }
        throw new NotFoundException("Customer not found");
    }

    @DELETE
    @Path("/{id}")
    public Response deleteCustomer(@PathParam("id") int id) {
        Customer customer = customers.stream()
                .filter(c -> c.getId() == id)
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Customer not found"));
        customers.remove(customer);
        return Response.noContent().build();
    }
}


