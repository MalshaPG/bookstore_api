package com.bookstore_csa_cw.resource;

import com.bookstore_csa_cw.dao.AuthorDAO;
import com.bookstore_csa_cw.dao.BookDAO;
import com.bookstore_csa_cw.exception.AuthorNotFoundException;
import com.bookstore_csa_cw.exception.InvalidInputException;
import com.bookstore_csa_cw.model.Author;
import com.bookstore_csa_cw.model.Book;
import com.bookstore_csa_cw.validations.AuthorValidations;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/authors")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthorResource {

    private static final Logger logger = Logger.getLogger(AuthorResource.class.getName());
    
    // Add new author
    @POST
    public Response addNewAuthor(Author author) {
        //Input validations
        AuthorValidations.validateAuthor(author,
                "First name, last name, and biography all are required." );

        AuthorDAO.addAuthor(author);
        logger.log(Level.INFO, "Author added : " + author);
        return Response.status(Response.Status.CREATED).entity(author).build();     
    }

    // Get all authors
    @GET
    public Response getAllAuthors() {
        return Response.ok(AuthorDAO.getAllAuthors()).build();
    }

    // Get author by ID
    @GET
    @Path("/{authorId}")
    public Response getAuthorById(@PathParam("authorId") int id) {
        Author author = AuthorDAO.getAuthorById(id);
        if (author == null) {
            throw new AuthorNotFoundException("Author with ID " + id + " not found.");
        }
        return Response.ok(author).build();
    }

    // Update an author's details
    @PUT
    @Path("/{authorId}")
    public Response updateAuthorDetails(@PathParam("authorId") int authorId, Author updatingAuthor) {
        //Input validations
        AuthorValidations.validateAuthor(updatingAuthor, 
                "Missing first name, last name, or biography when updateing.");
        
        boolean updated = AuthorDAO.updateAuthor(authorId, updatingAuthor);
        if (updated) {
            logger.log(Level.INFO, "Author updated: at authorId: " + authorId);
            return Response.ok(AuthorDAO.getAuthorById(authorId)).build();
        }
        throw new AuthorNotFoundException("Author with ID " + authorId + " not found.");
    }

    // Delete an author
    @DELETE
    @Path("/{authorId}")
    public Response deleteBook(@PathParam("authorId") int id) {
        boolean deleted = AuthorDAO.deleteAuthor(id);
        if (!deleted) {
            throw new AuthorNotFoundException("Author with ID " + id + " not found.");
        }
        logger.log(Level.WARNING, "Author deleted with ID: " + id);
        return Response.noContent().build();
    }

    // Get all the books of an author using the authorId
    @GET
    @Path("/{authorId}/books")
    public Response getAuthorsBooks(@PathParam("authorId") int id) {
        Author author = AuthorDAO.getAuthorById(id);
        if (author == null) {
            throw new AuthorNotFoundException("Author with ID " + id + " not found.");
        }

        List<Book> authorBooks = BookDAO.getBooksByAuthorId(id);
        return Response.ok(authorBooks).build();
    }
}
