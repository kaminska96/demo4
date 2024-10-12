package com.group02.demo4.services;

import com.group02.demo4.models.Book;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookService {

    // In-memory storage for books, simulating a database
    private static Map<Long, Book> books = new HashMap<>();
    // Counter to generate unique IDs for books
    private static long idCounter = 1;

    // Constructor for BookService
    public BookService() {
        // No initialization logic needed for this example
    }

    // Method to retrieve all books from the in-memory storage
    public List<Book> getAllBooks() {
        // Return a list of all books currently in the map
        return new ArrayList<>(books.values());
    }

    // Method to retrieve a book by its unique ID
    public Book getBookById(long bookId) {
        // Retrieve the book from the map by its ID
        return books.get(bookId);
    }

    // Method to add a new book to the in-memory storage
    public void addBook(Book book) {
        // Set a unique ID for the book and increment the counter
        book.setId(idCounter++);
        // Add the book to the map with its unique ID as the key
        books.put(book.getId(), book);
    }

    // Method to update an existing book in the in-memory storage
    public Book updateBook(long bookId, Book updatedBook) {
        // Retrieve the existing book from the map by its ID
        Book existingBook = books.get(bookId);
        // If the book does not exist, return null to indicate failure
        if (existingBook == null) {
            return null;
        }
        // Set the ID of the updated book to match the existing book's ID
        updatedBook.setId(bookId);
        // Update the map with the new book details
        books.put(bookId, updatedBook);
        return updatedBook;
    }

    // Method to delete a book from the in-memory storage by its unique ID
    public boolean deleteBook(long bookId) {
        // Remove the book from the map and return true if the book existed, false otherwise
        return books.remove(bookId) != null;
    }
}
