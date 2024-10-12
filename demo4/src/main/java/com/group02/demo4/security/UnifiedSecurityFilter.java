package com.group02.demo4.security;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.StringTokenizer;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import org.mindrot.jbcrypt.BCrypt;

import com.group02.demo4.db.UserDB;
import com.group02.demo4.models.User;

@Provider
public class UnifiedSecurityFilter implements ContainerRequestFilter {

    // Constants for authorization headers
    private static final String AUTHORIZATION_HEADER_KEY = "Authorization";
    private static final String BASIC_AUTH_PREFIX = "Basic ";
    private static final String BEARER_AUTH_PREFIX = "Bearer ";
    
    // Utility class for JWT operations
    private JwtUtil jwtUtil = new JwtUtil();

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        // Get the request path and authentication header
        String path = requestContext.getUriInfo().getPath();
        List<String> authHeader = requestContext.getHeaders().get(AUTHORIZATION_HEADER_KEY);

        // Allow unauthenticated access to login and registration endpoints
        if (path.equals("auth/login") || path.equals("auth/registration")) {
            return;
        }

        // Allow guest access for GET requests
        if (requestContext.getMethod().equalsIgnoreCase("GET")) {
            System.out.println("Guest access allowed for GET");
            return;
        }

        // If no authentication header is present, abort with an unauthorized response
        if (authHeader == null || authHeader.isEmpty()) {
            abortWithUnauthorized(requestContext, "No authentication provided.");
            return;
        }

        // Extract the authentication token from the header
        String authToken = authHeader.get(0);
        if (authToken.startsWith(BASIC_AUTH_PREFIX)) {
            // Handle Basic Authentication
            handleBasicAuth(requestContext, authToken.replaceFirst(BASIC_AUTH_PREFIX, ""));
        } else if (authToken.startsWith(BEARER_AUTH_PREFIX)) {
            // Handle JWT Authentication
            handleJwtAuth(requestContext, authToken.replaceFirst(BEARER_AUTH_PREFIX, ""));
        } else {
            // Abort if the authentication method is not recognized
            abortWithUnauthorized(requestContext, "Invalid authentication method.");
            return;
        }

        // Check if the authenticated user is present in the request context
        User authenticatedUser = (User) requestContext.getProperty("authenticatedUser");
        if (authenticatedUser == null) {
            // Abort if authentication failed
            abortWithUnauthorized(requestContext, "Authentication failed.");
        } else {
            // Check access rights based on the user's role
            checkAccessRights(requestContext, authenticatedUser);
        }
    }

    // Handle Basic Authentication by decoding the credentials and verifying them
    private void handleBasicAuth(ContainerRequestContext requestContext, String authToken) throws IOException {
        // Decode the Base64-encoded credentials
        byte[] decodedBytes = Base64.getDecoder().decode(authToken);
        String decodedString = new String(decodedBytes);
        StringTokenizer tokenizer = new StringTokenizer(decodedString, ":");

        // Extract username and password from the decoded string
        String username = tokenizer.hasMoreTokens() ? tokenizer.nextToken() : "";
        String password = tokenizer.hasMoreTokens() ? tokenizer.nextToken() : "";

        // Retrieve the user from the database and verify the password
        User user = UserDB.getUser(username);
        if (user != null && BCrypt.checkpw(password, user.getPassword())) {
            // Set the authenticated user in the request context
            requestContext.setProperty("authenticatedUser", user);
        } else {
            // Abort if credentials are invalid
            abortWithUnauthorized(requestContext, "Invalid credentials.");
        }
    }

    // Handle JWT Authentication by validating the token and extracting the username
    private void handleJwtAuth(ContainerRequestContext requestContext, String token) {
        try {
            // Extract the username from the JWT token
            String username = jwtUtil.extractUsername(token);
            User user = UserDB.getUser(username);

            // Validate the token and ensure the user exists
            if (user != null && jwtUtil.validateToken(token, username)) {
                // Set the authenticated user in the request context
                requestContext.setProperty("authenticatedUser", user);
            } else {
                // Abort if the token is invalid
                abortWithUnauthorized(requestContext, "Invalid token.");
            }
        } catch (Exception e) {
            // Abort if there is an error during token validation
            abortWithUnauthorized(requestContext, "Token validation error.");
        }
    }

    // Check access rights based on the authenticated user's role
    private void checkAccessRights(ContainerRequestContext requestContext, User authenticatedUser) {
        // Get the HTTP method of the request
        String method = requestContext.getMethod();
        // Get the role of the authenticated user
        String role = authenticatedUser.getRole();

        // Allow all authenticated users to perform GET requests
        if (method.equalsIgnoreCase("GET")) {
            return;
        }

        // Allow users with 'user' or 'admin' role to perform PUT requests
        if (method.equalsIgnoreCase("PUT") && (role.equals("user") || role.equals("admin"))) {
            return;
        }

        // Restrict POST requests to 'admin' role, except for login
        String path = requestContext.getUriInfo().getPath();
        if (method.equalsIgnoreCase("POST") && !path.equals("/auth/login") && !role.equals("admin")) {
            abortWithUnauthorized(requestContext, "Admin role required for POST requests.");
        }

        // Restrict DELETE requests to 'admin' role
        if (method.equalsIgnoreCase("DELETE") && !role.equals("admin")) {
            abortWithUnauthorized(requestContext, "Admin role required for DELETE requests.");
        }
    }

    // Abort the request with an unauthorized response
    private void abortWithUnauthorized(ContainerRequestContext requestContext, String message) {
        requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
                                          .entity(message)
                                          .build());
    }
}
