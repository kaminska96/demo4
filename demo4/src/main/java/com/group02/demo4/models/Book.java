package com.group02.demo4.models;

import java.util.List;

import jakarta.xml.bind.annotation.XmlRootElement;

// Annotation to indicate that this class can be represented as XML
@XmlRootElement
public class Book {
    
    // Unique identifier for the book
    private long id;
    
    // Title of the book
    private String title;
    
    // ISBN number of the book, used for identification
    private String isbn;
    
    // List of authors who wrote the book
    private List<Author> authors; 
    
    // Year the book was published
    private int publicationYear;

    // Default no-argument constructor
    public Book() {}

    // Parameterized constructor to initialize all fields of the book
    public Book(long id, String title, String isbn, List<Author> authors, int publicationYear) {
        this.id = id; // Initialize the book's ID
        this.title = title; // Initialize the book's title
        this.isbn = isbn; // Initialize the book's ISBN
        this.authors = authors; // Initialize the list of authors for the book
        this.publicationYear = publicationYear; // Initialize the publication year of the book
    }

    // Getter method for the book's ID
    public long getId() {
        return id;
    }

    // Setter method for the book's ID
    public void setId(long id) {
        this.id = id;
    }

    // Getter method for the book's title
    public String getTitle() {
        return title;
    }

    // Setter method for the book's title
    public void setTitle(String title) {
        this.title = title;
    }

    // Getter method for the book's ISBN
    public String getIsbn() {
        return isbn;
    }

    // Setter method for the book's ISBN
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    // Getter method for the list of authors of the book
    public List<Author> getAuthors() {
        return authors;
    }

    // Setter method for the list of authors of the book
    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    // Getter method for the publication year of the book
    public int getPublicationYear() {
        return publicationYear;
    }

    // Setter method for the publication year of the book
    public void setPublicationYear(int publicationYear) {
        this.publicationYear = publicationYear;
    }
}
