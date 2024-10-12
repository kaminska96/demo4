package com.group02.demo4.exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class DataNotFoundExceptionMapper implements ExceptionMapper<DataNotFoundException> {

    @Override
    public Response toResponse(DataNotFoundException exception) {
        // Create an ErrorMessage object containing the exception details
        ErrorMessage errorMessage = new ErrorMessage(exception.getMessage(), 404, "http://myDocs.org");
        
        // Build and return a Response object with status 404 (NOT FOUND) and the error message as the entity
        return Response.status(Response.Status.NOT_FOUND)
                .entity(errorMessage)
                .build();
    }
}
