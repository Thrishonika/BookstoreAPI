/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bookstore.resource;

/**
 *
 * @author ADMIN
 */

import com.bookstore.model.Author;
import com.bookstore.store.DataStore;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.ArrayList;
import java.util.List;

@Path("/authors")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthorResource {

    @GET
    public List<Author> getAllAuthors() {
        return new ArrayList<>(DataStore.authors.values());
    }

    @GET
    @Path("/{id}")
    public Author getAuthorById(@PathParam("id") int id) {
        Author author = DataStore.authors.get(id);
        if (author == null) {
            throw new NotFoundException("Author not found");
        }
        return author;
    }

    @POST
    public Response addAuthor(Author author) {
        int id = generateNewId(); // You'll need to implement this method
        author.setId(id);
        DataStore.authors.put(id, author);
        return Response.status(Response.Status.CREATED).entity(author).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateAuthor(@PathParam("id") int id, Author updatedAuthor) {
        Author existingAuthor = DataStore.authors.get(id);
        if (existingAuthor == null) {
            throw new NotFoundException("Author not found");
        }

        updatedAuthor.setId(id);
        DataStore.authors.put(id, updatedAuthor);
        return Response.ok(updatedAuthor).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteAuthor(@PathParam("id") int id) {
        Author removed = DataStore.authors.remove(id);
        if (removed == null) {
            throw new NotFoundException("Author not found");
        }
        return Response.noContent().build();
    }

    // Dummy ID generator method (for now, you can improve it later)
    private int generateNewId() {
        return DataStore.authors.size() + 1;
    }
}


