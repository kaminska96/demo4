package com.group02.demo4.exceptions;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class GenericExceptionMapper implements ExceptionMapper<Throwable> {

    @Override
    public Response toResponse(Throwable exception) {
        // Create an ErrorMessage object with details about the exception
        // The error message contains the exception message, an HTTP status code, and a link to documentation
        ErrorMessage errorMessage = new ErrorMessage(exception.getMessage(), 500, "http://myDocs.org");
        
        // Build and return a Response object with status 500 (Internal Server Error)
        // The response includes the error message entity in JSON format
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(errorMessage) // Set the response entity to the error message
                .type(MediaType.APPLICATION_JSON) // Set the response content type to JSON
                .build(); // Build the response
    }
}
