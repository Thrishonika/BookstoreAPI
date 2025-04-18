/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bookstore.resource;

/**
 *
 * @author ADMIN
 */


import com.bookstore.model.Book;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.ArrayList;
import java.util.List;

@Path("/books")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BookResource {

    private final static List<Book> books = new ArrayList<>();

    @GET
    public List<Book> getAllBooks() {
        return books;
    }

    @GET
    @Path("/{id}")
    public Book getBookById(@PathParam("id") int id) {
        return books.stream()
                    .filter(book -> book.getId() == id)
                    .findFirst()
                    .orElseThrow(() -> new NotFoundException("Book not found"));
    }

    @POST
    public Response addBook(Book book) {
        books.add(book);
        return Response.status(Response.Status.CREATED).entity(book).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateBook(@PathParam("id") int id, Book updatedBook) {
        for (Book book : books) {
            if (book.getId() == id) {
                book.setTitle(updatedBook.getTitle());
                book.setAuthor(updatedBook.getAuthor());
                book.setIsbn(updatedBook.getIsbn());
                book.setPublicationYear(updatedBook.getPublicationYear());
                book.setPrice(updatedBook.getPrice());
                book.setStockQuantity(updatedBook.getStockQuantity());
                return Response.ok(book).build();
            }
        }
        throw new NotFoundException("Book not found");
    }

    @DELETE
    @Path("/{id}")
    public Response deleteBook(@PathParam("id") int id) {
        Book bookToRemove = books.stream()
                                 .filter(b -> b.getId() == id)
                                 .findFirst()
                                 .orElseThrow(() -> new NotFoundException("Book not found"));
        books.remove(bookToRemove);
        return Response.noContent().build();
    }
}

