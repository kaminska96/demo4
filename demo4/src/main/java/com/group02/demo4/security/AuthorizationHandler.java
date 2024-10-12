package com.group02.demo4.security;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import com.group02.demo4.models.User;

@Provider
public class AuthorizationHandler implements ContainerRequestFilter {

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        // Extract the request path and method type from the incoming request
        String path = requestContext.getUriInfo().getPath();
        String method = requestContext.getMethod();

        // Allow unauthenticated access to login and registration endpoints
        if (path.equals("auth/login") || path.equals("auth/registration")) {
            return; // Skip authorization checks for these endpoints
        } 
        
        // Allow guest access for GET requests
        if (method.equalsIgnoreCase("GET")) {
            System.out.println("Guest access allowed for GET");
            return; // No authentication required for GET requests
        }

        // Retrieve the authenticated user from the request context
        User user = (User) requestContext.getProperty("authenticatedUser");
        if (user == null) {
            // Abort request with unauthorized response if user is not authenticated
            abortWithUnauthorized(requestContext, "Unauthorized access.");
            return;
        }

        // Check the role of the authenticated user
        String role = user.getRole();
        System.out.println(method + ": " + path + ": " + role); // Log the request method, path, and user role

        // Restrict POST requests to admin role, except for login endpoint
        if (method.equalsIgnoreCase("POST") && !path.equals("auth/login")) {
            if (!role.equals("admin")) {
                // Abort request if user is not an admin
                abortWithUnauthorized(requestContext, "Only admin can perform POST requests.");
            }
        } 
        // Restrict PUT and DELETE requests to users with 'admin' or 'user' role
        else if (method.equalsIgnoreCase("PUT") || method.equalsIgnoreCase("DELETE")) {
            if (!role.equals("admin") && !role.equals("user")) {
                // Abort request if user does not have sufficient permissions
                abortWithUnauthorized(requestContext, "Insufficient permissions.");
            }
        }
    }

    // Method to abort the request with a forbidden response
    private void abortWithUnauthorized(ContainerRequestContext requestContext, String message) {
        requestContext.abortWith(Response.status(Response.Status.FORBIDDEN) // Set response status to 403 Forbidden
                                          .entity(message) // Include the error message in the response body
                                          .build()); // Build and return the response
    }
}