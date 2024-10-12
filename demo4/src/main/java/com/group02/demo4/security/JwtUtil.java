package com.group02.demo4.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtil {

    // Secret key used for signing the JWT tokens
    private static final SecretKey SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    // Method to generate a JWT token for a given username
    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>(); // Empty claims map for additional information
        return createToken(claims, username); // Create the token with the provided claims and subject (username)
    }

    // Method to create a JWT token with the specified claims and subject
    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims) // Set the claims (additional information) in the token
                .setSubject(subject) // Set the subject (typically the username) in the token
                .setIssuedAt(new Date(System.currentTimeMillis())) // Set the issued date to the current time
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // Set the expiration date to 10 hours from now
                .signWith(SECRET_KEY) // Sign the token using the secret key
                .compact(); // Compact the token into a string representation
    }

    // Method to extract the username from a given JWT token
    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject(); // Extract the subject (username) from the claims
    }

    // Method to extract all claims from a given JWT token
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY) // Set the secret key used to verify the token signature
                .build()
                .parseClaimsJws(token) // Parse the token and validate the signature
                .getBody(); // Retrieve the claims from the parsed token
    }

    // Method to validate the JWT token for a given username
    public Boolean validateToken(String token, String username) {
        final String extractedUsername = extractUsername(token); // Extract the username from the token
        return (extractedUsername.equals(username) && !isTokenExpired(token)); // Validate username match and check token expiry
    }

    // Method to check if the given JWT token is expired
    private Boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date()); // Compare the token expiration date with the current date
    }
}
