package com.group02.demo4.models;

// Class representing a user in the system
public class User {
    // Username of the user (unique identifier)
    private String username;
    
    // Hashed password of the user
    private String password;
    
    // Role of the user (e.g., 'admin', 'user')
    private String role;

    // Constructor to initialize the user's attributes
    public User(String username, String password, String role) {
        this.username = username; // Initialize the username
        this.password = password; // Initialize the hashed password
        this.role = role; // Initialize the user's role
    }

    // Getter method for the username
    public String getUsername() {
        return username;
    }

    // Getter method for the password (hashed value)
    public String getPassword() {
        return password;
    }

    // Getter method for the user's role
    public String getRole() {
        return role;
    }
}
