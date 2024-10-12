package com.group02.demo4.services;

import com.group02.demo4.models.Author;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AuthorService {

    private static Map<Long, Author> authors = new HashMap<>();
    private static long authorIdCounter = 1;

    public AuthorService() {
        // Log initialization of the AuthorService
        System.out.println("AuthorService initialized");
    }

    public List<Author> getAllAuthors() {
        // Log retrieval of all authors
        System.out.println("Retrieving all authors. Total count: " + authors.size());
        return new ArrayList<>(authors.values());
    }

    public Author getAuthorById(long authorId) {
        // Log retrieval of author by ID
        System.out.println("Retrieving author with ID: " + authorId);
        return authors.get(authorId);
    }

    public void addAuthor(Author author) {
        // Log addition of a new author
        author.setId(authorIdCounter++);
        authors.put(author.getId(), author);
        System.out.println("Added new author with ID: " + author.getId() + ", Name: " + author.getName());
    }

    public Author updateAuthor(long authorId, Author updatedAuthor) {
        // Log update request for an author
        System.out.println("Updating author with ID: " + authorId);
        Author existingAuthor = authors.get(authorId);
        if (existingAuthor == null) {
            // Log if the author does not exist
            System.out.println("Author with ID: " + authorId + " not found");
            return null;
        }

        // Log preservation of existing books if updated author has no books
        if (updatedAuthor.getBooks() == null) {
            System.out.println("Preserving existing books for author with ID: " + authorId);
            updatedAuthor.setBooks(existingAuthor.getBooks());
        }
        updatedAuthor.setId(authorId);
        authors.put(authorId, updatedAuthor);

        // Log successful update
        System.out.println("Author with ID: " + authorId + " updated successfully");
        return updatedAuthor;
    }

    public boolean deleteAuthor(long authorId) {
        // Log deletion request for an author
        System.out.println("Deleting author with ID: " + authorId);
        boolean isDeleted = authors.remove(authorId) != null;
        if (isDeleted) {
            // Log successful deletion
            System.out.println("Author with ID: " + authorId + " deleted successfully");
        } else {
            // Log if the author does not exist
            System.out.println("Author with ID: " + authorId + " not found");
        }
        return isDeleted;
    }
}
