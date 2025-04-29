package com.bookstore_csa_cw.exception;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;


@Provider
public class BookNotFoundExceptionMapper implements ExceptionMapper<BookNotFoundException>{
    
    private static final Logger logger = Logger.getLogger(BookNotFoundExceptionMapper.class.getName());

    @Override
    public Response toResponse(BookNotFoundException exception) {
        logger.log(Level.SEVERE, "Book not found: " + exception.getMessage(), exception);

        Map<String, String> error = new HashMap<>();
        error.put("error", "Book Not Found");
        error.put("message", exception.getMessage());

        return Response.status(Response.Status.NOT_FOUND)
                .type(MediaType.APPLICATION_JSON)
                .entity(error)
                .build();
    }
}
