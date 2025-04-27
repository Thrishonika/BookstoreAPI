package com.bookstore.resource;
import com.bookstore.exception.AuthorNotFoundException;
import com.bookstore.exception.BookNotFoundException;
import com.bookstore.exception.InvalidInputException;
import com.bookstore.model.Book;
import com.bookstore.store.DataStore;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


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
            throw new BookNotFoundException("Book not found");
        }
        return book;
    }

    @POST
    public Response addBook(Book book) {
         validateBook(book);
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
            throw new BookNotFoundException("Book not found");
        }
        validateBook(updatedBook);
        updatedBook.setId(id);
        DataStore.books.put(id, updatedBook);
        return Response.ok(updatedBook).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteBook(@PathParam("id") int id) {
        Book removed = DataStore.books.remove(id);
        if (removed == null) {
            throw new BookNotFoundException("Book not found");
        }
        return Response.noContent().build();
    }
    // New method to validate Book input
    private void validateBook(Book book) {
        if (book.getTitle() == null || book.getTitle().trim().isEmpty()) {
            throw new InvalidInputException("Book title must not be empty.");
        }
        
        if (book.getPrice() < 0) {
            throw new InvalidInputException("Book price must not be negative.");
        }
        
        if (book.getPublicationYear() > java.time.Year.now().getValue()) {
            throw new InvalidInputException("Publication year cannot be in the future.");
        }
         // New validation: Check if the authorId exists
        if (!DataStore.authors.containsKey(book.getAuthorId())) {
            throw new AuthorNotFoundException("Book author must exist.");
        }
    }

}