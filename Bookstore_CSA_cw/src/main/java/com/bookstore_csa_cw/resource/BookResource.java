package com.bookstore_csa_cw.resource;

import com.bookstore_csa_cw.dao.AuthorDAO;
import com.bookstore_csa_cw.dao.BookDAO;
import com.bookstore_csa_cw.model.Book;
import com.bookstore_csa_cw.model.Author;
import com.bookstore_csa_cw.exception.BookNotFoundException;
import com.bookstore_csa_cw.exception.InvalidInputException;
import com.bookstore_csa_cw.exception.AuthorNotFoundException;
import com.bookstore_csa_cw.validations.BookValidations;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.time.LocalDate;

import java.util.logging.Logger;
import java.util.logging.Level;

@Path("/books")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BookResource {
    
    private static final Logger logger = Logger.getLogger(BookResource.class.getName());

    // Get all books
    @GET
    public Response getAllBooks() {
        List<Book> books = BookDAO.getAllBooks();
        if (books.isEmpty()) {
            return Response.ok("{\"message\": \"No books found.\"}").build();
        }
        return Response.ok(books).build();
    }

    // Get book by ID
    @GET
    @Path("/{id}")
    public Response getBookById(@PathParam("id") int id) {
        Book book = BookDAO.getBookById(id);
        if (book == null) {
            throw new BookNotFoundException("Book with ID " + id + " not found.");
        }
        return Response.ok(book).build();
    }

    // Create a new book
    @POST
    public Response createBook(Book book) {
        BookValidations.validateBook(book, 
                "ISBN, title, author, valid published year and a valid authorId are required.");
        
        if (!BookDAO.isISBNUnique(book.getIsbn())) {
            throw new InvalidInputException("Book with ISBN " + book.getIsbn() + " already exists.");
        }

        Book createdBook = BookDAO.addBook(book);
        logger.log(Level.INFO, "Book added: " + createdBook);
        return Response.status(Response.Status.CREATED).entity(createdBook).build();
    }

    // Update book details
    @PUT
    @Path("/{id}")
    public Response updateBook(@PathParam("id") int id, Book updatingBook) {
        BookValidations.validateBook(updatingBook, 
                "Missing ISBN, title, author, authorId, publication year or price in the update.");

        boolean updated = BookDAO.updateBook(id, updatingBook);
        if (updated) {
            logger.log(Level.INFO, "Book updated: " + updatingBook);
            return Response.ok(updatingBook).build();
        } else {
            throw new BookNotFoundException("Book with ID " + id + " not found.");
        }
    }

    // Delete a book
    @DELETE
    @Path("/{id}")
    public Response deleteBook(@PathParam("id") int id) {
        boolean removed = BookDAO.deleteBook(id);
        if (!removed) {
            throw new BookNotFoundException("Book with ID " + id + " not found.");
        }
        logger.log(Level.WARNING, "Book deleted with ID: " + id);
        return Response.noContent().build();
    }
}
