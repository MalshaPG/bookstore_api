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
public class AuthorNotFoundExceptionMapper implements ExceptionMapper<AuthorNotFoundException>{
    
    private static final Logger logger = Logger.getLogger(AuthorNotFoundExceptionMapper.class.getName());

   
    @Override
    public Response toResponse(AuthorNotFoundException exception) {
        logger.log(Level.SEVERE, "Author not found: " + exception.getMessage(), exception);

        Map<String, String> error = new HashMap<>();
        error.put("error", "Author Not Found");
        error.put("message", exception.getMessage());

        return Response.status(Response.Status.NOT_FOUND)
                .type(MediaType.APPLICATION_JSON)
                .entity(error)
                .build();
    }   
}
