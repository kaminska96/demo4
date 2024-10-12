package com.group02.demo4.models;

import java.util.List;

import javax.json.bind.annotation.JsonbTransient;
import jakarta.xml.bind.annotation.XmlRootElement;

// Annotation to indicate that this class can be represented as XML
@XmlRootElement
public class Author {
    
    // Unique identifier for the author
    private long id;
    
    // Name of the author
    private String name;
    
    // Nationality of the author
    private String nationality;
    
    // List of books written by the author
    // This field is marked as transient to avoid being included in JSON serialization
    @JsonbTransient
    private List<Book> books;

    // Default no-argument constructor
    public Author() {}

    // Parameterized constructor to initialize all fields of the author
    public Author(long id, String name, String nationality, List<Book> books) {
        this.id = id; // Initialize the author's ID
        this.name = name; // Initialize the author's name
        this.nationality = nationality; // Initialize the author's nationality
        this.books = books; // Initialize the list of books authored by the author
    }

    // Getter method for the author's ID
    public long getId() {
        return id;
    }

    // Setter method for the author's ID
    public void setId(long id) {
        this.id = id;
    }

    // Getter method for the author's name
    public String getName() {
        return name;
    }

    // Setter method for the author's name
    public void setName(String name) {
        this.name = name;
    }

    // Getter method for the author's nationality
    public String getNationality() {
        return nationality;
    }

    // Setter method for the author's nationality
    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    // Getter method for the list of books authored by the author
    public List<Book> getBooks() {
        return books;
    }

    // Setter method for the list of books authored by the author
    public void setBooks(List<Book> books) {
        this.books = books;
    }
}
