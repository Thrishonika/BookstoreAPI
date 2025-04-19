
package com.bookstore.resource;
import com.bookstore.model.Book;
import com.bookstore.store.DataStore;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;


import java.util.List;
import java.util.ArrayList;

@Path("/books")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BookResource {

    // Method to generate a new unique ID based on the size of the books map
    private int generateNewId() {
        return DataStore.books.size() + 1;  // Generate a new ID based on the current size of the books map
    }

    @GET
    public List<Book> getAllBooks() {
        return new ArrayList<>(DataStore.books.values());
    }

    @GET
    @Path("/{id}")
    public Book getBookById(@PathParam("id") int id) {
        Book book = DataStore.books.get(id);
        if (book == null) {
            throw new NotFoundException("Book not found");
        }
        return book;
    }

    @POST
    public Response addBook(Book book) {
        int id = generateNewId(); // Call the method to generate the ID
        book.setId(id);  // Set the ID of the book
        DataStore.books.put(id, book);  // Add the book to the DataStore
        return Response.status(Response.Status.CREATED).entity(book).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateBook(@PathParam("id") int id, Book updatedBook) {
        Book existingBook = DataStore.books.get(id);
        if (existingBook == null) {
            throw new NotFoundException("Book not found");
        }
        updatedBook.setId(id);
        DataStore.books.put(id, updatedBook);
        return Response.ok(updatedBook).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteBook(@PathParam("id") int id) {
        Book removed = DataStore.books.remove(id);
        if (removed == null) {
            throw new NotFoundException("Book not found");
        }
        return Response.noContent().build();
    }
}


