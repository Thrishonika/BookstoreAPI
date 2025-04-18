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
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.ArrayList;
import java.util.List;

@Path("/authors")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthorResource {

    private final static List<Author> authors = new ArrayList<>();

    @GET
    public List<Author> getAllAuthors() {
        return authors;
    }

    @GET
    @Path("/{id}")
    public Author getAuthorById(@PathParam("id") int id) {
        return authors.stream()
                .filter(a -> a.getId() == id)
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Author not found"));
    }

    @POST
    public Response addAuthor(Author author) {
        authors.add(author);
        return Response.status(Response.Status.CREATED).entity(author).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateAuthor(@PathParam("id") int id, Author updatedAuthor) {
        for (Author a : authors) {
            if (a.getId() == id) {
                a.setName(updatedAuthor.getName());
                a.setBiography(updatedAuthor.getBiography());
                return Response.ok(a).build();
            }
        }
        throw new NotFoundException("Author not found");
    }

    @DELETE
    @Path("/{id}")
    public Response deleteAuthor(@PathParam("id") int id) {
        Author author = authors.stream()
                .filter(a -> a.getId() == id)
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Author not found"));
        authors.remove(author);
        return Response.noContent().build();
    }
}


