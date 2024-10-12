package com.group02.demo4.exceptions;

public class DataNotFoundException extends RuntimeException {

    // Serial version UID for serialization (helps to verify compatibility between sender and receiver during deserialization)
    private static final long serialVersionUID = 1L;

    // Constructor that takes a message as parameter and passes it to the superclass constructor
    public DataNotFoundException(String message) {
        super(message); // Call the parent class (RuntimeException) constructor with the provided message
        // Log the exception message for debugging purposes
        System.out.println("DataNotFoundException thrown with message: " + message);
    }
}