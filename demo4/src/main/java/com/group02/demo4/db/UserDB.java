package com.group02.demo4.db;

import org.mindrot.jbcrypt.BCrypt;
import java.util.HashMap;
import java.util.Map;
import com.group02.demo4.models.User;

public class UserDB {
    // Static map to store user credentials, acting as an in-memory database
    private static Map<String, User> userCredentials = new HashMap<>();

    // Method to get the user credentials map
    public static Map<String, User> get() {
        return userCredentials;
    }

    // Initialize the in-memory database with default users
    public static void initialize() {
        // Create 10 default users with alternating roles ('user' and 'admin')
        for (int i = 1; i <= 10; i++) {
            String username = "user" + i;  // Generate username (e.g., user1, user2, etc.)
            String password = "password" + i;  // Generate password (e.g., password1, password2, etc.)
            String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());  // Hash the password using BCrypt
            String role = (i % 2 == 0) ? "admin" : "user";  // Assign role: 'admin' for even users, 'user' for odd users
            userCredentials.put(username, new User(username, hashedPassword, role));  // Add user to the map
        }

        // Create a default admin user with a specific password
        String adminPassword = "adminPassword";
        String adminHashedPassword = BCrypt.hashpw(adminPassword, BCrypt.gensalt());  // Hash the admin password
        userCredentials.put("admin", new User("admin", adminHashedPassword, "admin"));  // Add admin user to the map
    }

    // Method to retrieve a user by username
    public static User getUser(String username) {
        return userCredentials.get(username);  // Return the user object if it exists in the map
    }
    
    // Method to add a new user to the in-memory database
    public static void addUser(User user) {
        userCredentials.put(user.getUsername(), user);  // Add the user to the map
    }
}