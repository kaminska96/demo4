package com.group02.demo4.resources;

import com.group02.demo4.models.User;
import com.group02.demo4.db.UserDB;
import com.group02.demo4.security.JwtUtil;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.mindrot.jbcrypt.BCrypt;

@Path("/auth")
public class AuthResource {

    // Utility class instance for generating and validating JWT tokens
    private JwtUtil jwtUtil = new JwtUtil();

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(Credentials credentials) {
        // Retrieve user from the database by username
        User user = UserDB.getUser(credentials.getUsername());

        // Check if the user exists and if the provided password matches the stored hashed password
        if (user == null || !BCrypt.checkpw(credentials.getPassword(), user.getPassword())) {
            // If authentication fails, return an unauthorized response
            return Response.status(Status.UNAUTHORIZED).entity("Invalid credentials").build();
        }

        // Generate a JWT token for the authenticated user
        String token = jwtUtil.generateToken(user.getUsername());

        // Return the generated token in the response
        return Response.ok(new JwtResponse(token)).build();
    }
    
    @POST
    @Path("/registration")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response register(Credentials credentials) {
        // Check if a user with the given username already exists in the database
        if (UserDB.getUser(credentials.getUsername()) != null) {
            // If the username is taken, return a bad request response
            return Response.status(Response.Status.BAD_REQUEST).entity("Username already exists").build();
        }

        // Hash the user's password using BCrypt before storing it
        String hashedPassword = BCrypt.hashpw(credentials.getPassword(), BCrypt.gensalt());

        // Create a new user object with the provided username and hashed password
        User newUser = new User(credentials.getUsername(), hashedPassword, "user");
        // Add the new user to the database
        UserDB.addUser(newUser);

        // Return a response indicating successful registration
        return Response.status(Response.Status.CREATED).entity("User successfully registered").build();
    }

    // Class representing the response containing a JWT token
    public static class JwtResponse {
        private String token;

        // Constructor to initialize the token
        public JwtResponse(String token) {
            this.token = token;
        }

        // Getter for the token
        public String getToken() {
            return token;
        }

        // Setter for the token
        public void setToken(String token) {
            this.token = token;
        }
    }

    // Class representing user credentials (username and password)
    public static class Credentials {
        private String username;
        private String password;

        // Default no-argument constructor
        public Credentials() {
        }

        // Getter for the username
        public String getUsername() {
            return username;
        }

        // Setter for the username
        public void setUsername(String username) {
            this.username = username;
        }

        // Getter for the password
        public String getPassword() {
            return password;
        }

        // Setter for the password
        public void setPassword(String password) {
            this.password = password;
        }
    }
}
