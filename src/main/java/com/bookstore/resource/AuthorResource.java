
package com.bookstore.resource;

/**
 *
 * @author ADMIN
 */

import com.bookstore.exception.AuthorNotFoundException;
import com.bookstore.model.Author;
import com.bookstore.model.Book;
import com.bookstore.store.DataStore;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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
            throw new AuthorNotFoundException("Author not found");
        }
        return author;
    }
    @GET
    @Path("/{id}/books")
    public Response getBooksByAuthorId(@PathParam("id") int id) {
        Author author = DataStore.authors.get(id);
        if (author == null) {
            throw new AuthorNotFoundException("Author not found");
        }

        // Filter books by authorId
        List<Book> booksByAuthor = new ArrayList<>();
        for (Book book : DataStore.books.values()) {
            if (book.getAuthorId() == id) {
                booksByAuthor.add(book);
            }
        }

    return Response.ok(booksByAuthor).build();
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
            throw new AuthorNotFoundException("Author not found");
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
            throw new AuthorNotFoundException("Author not found");
        }
        return Response.noContent().build();
    }

    // Dummy ID generator method (for now, you can improve it later)
    private int generateNewId() {
        return DataStore.authors.size() + 1;
    }
}