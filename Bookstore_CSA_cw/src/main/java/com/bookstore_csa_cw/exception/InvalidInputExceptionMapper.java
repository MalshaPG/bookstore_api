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
public class InvalidInputExceptionMapper implements ExceptionMapper<InvalidInputException>{
    private static final Logger logger = Logger.getLogger(InvalidInputExceptionMapper.class.getName());

    @Override
    public Response toResponse(InvalidInputException exception) {
        logger.log(Level.SEVERE, "Invalid input: {0}", exception.getMessage());

        Map<String, String> error = new HashMap<>();
        error.put("error", "Invalid Input");
        error.put("message", exception.getMessage());

        return Response.status(Response.Status.BAD_REQUEST)
                .type(MediaType.APPLICATION_JSON)
                .entity(error)
                .build();
    }
}
