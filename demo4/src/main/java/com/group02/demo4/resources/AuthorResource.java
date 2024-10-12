package com.group02.demo4.resources;

import com.group02.demo4.exceptions.DataNotFoundException;
import com.group02.demo4.models.Author;
import com.group02.demo4.models.Book;
import com.group02.demo4.services.AuthorService;
import com.group02.demo4.services.BookService;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.*;

@Path("/authors")
public class AuthorResource {

    // Service layer instances for managing authors and books
    private AuthorService authorService = new AuthorService();
    private BookService bookService = new BookService();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAuthors(@QueryParam("nationality") String nationality) {
        // Retrieve the list of all authors
        List<Author> authors = authorService.getAllAuthors();
        List<Map<String, Object>> response = new ArrayList<>();

        // If no authors are found, throw an exception
        if (authors.isEmpty()) {
            throw new DataNotFoundException("No authors found");
        }

        // Filter authors by nationality if the query parameter is provided
        if (nationality != null && !nationality.isEmpty()) {
            for (Author author : authors) {
                if (author.getNationality().equalsIgnoreCase(nationality)) {
                    // Create a response map for each author that matches the nationality
                    Map<String, Object> authorData = new HashMap<>();
                    authorData.put("id", author.getId());
                    authorData.put("name", author.getName());
                    authorData.put("nationality", author.getNationality());
                    response.add(authorData);
                }
            }
        } else {
            // Add all authors to the response if no nationality filter is provided
            for (Author author : authors) {
                Map<String, Object> authorData = new HashMap<>();
                authorData.put("id", author.getId());
                authorData.put("name", author.getName());
                authorData.put("nationality", author.getNationality());
                response.add(authorData);
            }
        }

        // If no authors match the provided nationality, throw an exception
        if (response.isEmpty()) {
            throw new DataNotFoundException("No authors found for the given nationality: " + nationality);
        }

        return Response.ok(response).build();
    }

    @GET
    @Path("/{authorId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAuthor(@PathParam("authorId") long authorId) {
        // Retrieve the author by ID
        Author author = authorService.getAuthorById(authorId);

        // If the author is not found, throw an exception
        if (author == null) {
            throw new DataNotFoundException("Author with ID " + authorId + " not found");
        }

        // Create a response map with author details
        Map<String, Object> response = new HashMap<>();
        response.put("id", author.getId());
        response.put("name", author.getName());
        response.put("nationality", author.getNationality());

        return Response.ok(response).build();
    }

    @GET
    @Path("/{authorId}/books")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAuthorWithBooks(@PathParam("authorId") long authorId) {
        // Retrieve the author by ID
        Author author = authorService.getAuthorById(authorId);

        // If the author is not found, throw an exception
        if (author == null) {
            throw new DataNotFoundException("Author with ID " + authorId + " not found");
        }

        // If the author has no books, throw an exception
        if (author.getBooks() == null || author.getBooks().isEmpty()) {
            throw new DataNotFoundException("No books found for author with ID " + authorId);
        }

        // Create a response map with the list of books written by the author
        Map<String, Object> response = new HashMap<>();
        List<Map<String, Object>> booksList = new ArrayList<>();
        for (Book book : author.getBooks()) {
            Map<String, Object> bookInfo = new HashMap<>();
            bookInfo.put("id", book.getId());
            bookInfo.put("title", book.getTitle());
            bookInfo.put("isbn", book.getIsbn());
            bookInfo.put("publicationYear", book.getPublicationYear());
            booksList.add(bookInfo);
        }
        response.put("books", booksList);

        return Response.ok(response).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"user", "admin"}) 
    public Response addAuthor(Author author) {
        // Validate that the author has a name and nationality
        if (author.getName() == null || author.getNationality() == null) {
            throw new WebApplicationException("Author name and nationality are required", Response.Status.BAD_REQUEST);
        }

        // Add the new author to the service
        authorService.addAuthor(author);
        return Response.status(Response.Status.CREATED).entity(author).build();
    }

    @PUT
    @Path("/{authorId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"user", "admin"}) 
    public Response updateAuthor(@PathParam("authorId") long authorId, Author updatedAuthor) {
        // Validate that the updated author has a name and nationality
        if (updatedAuthor.getName() == null || updatedAuthor.getNationality() == null) {
            throw new WebApplicationException("Author name and nationality are required", Response.Status.BAD_REQUEST);
        }

        // Update the author in the service
        Author author = authorService.updateAuthor(authorId, updatedAuthor);

        // If the author is not found, throw an exception
        if (author == null) {
            throw new DataNotFoundException("Author with ID " + authorId + " not found");
        }

        return Response.ok(author).build();
    }

    @PUT
    @Path("/{authorId}/books/{bookId}")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"user", "admin"}) 
    public Response addBookToAuthor(@PathParam("authorId") long authorId, @PathParam("bookId") long bookId) {
        // Retrieve the author and book by their respective IDs
        Author author = authorService.getAuthorById(authorId);
        Book book = bookService.getBookById(bookId);

        // If the author is not found, throw an exception
        if (author == null) {
            throw new DataNotFoundException("Author with ID " + authorId + " not found");
        }

        // If the book is not found, throw an exception
        if (book == null) {
            throw new DataNotFoundException("Book with ID " + bookId + " not found");
        }

        // Add the book to the author's list of books if it's not already there
        if (!author.getBooks().contains(book)) {
            author.getBooks().add(book);
        }
        // Add the author to the book's list of authors if it's not already there
        if (!book.getAuthors().contains(author)) {
            book.getAuthors().add(author);
        }

        // Update the author and book in their respective services
        authorService.updateAuthor(authorId, author);
        bookService.updateBook(bookId, book);

        // Create a response map with author details
        Map<String, Object> response = new HashMap<>();
        response.put("id", author.getId());
        response.put("name", author.getName());
        response.put("nationality", author.getNationality());

        return Response.ok(response).build();
    }    
    
    @DELETE
    @Path("/{authorId}")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("admin")
    public Response deleteAuthor(@PathParam("authorId") long authorId) {
        // Attempt to delete the author by ID
        boolean isDeleted = authorService.deleteAuthor(authorId);

        // If the author is not found, throw an exception
        if (!isDeleted) {
            throw new DataNotFoundException("Author with ID " + authorId + " not found");
        }

        // Create a response message indicating successful deletion
        Map<String, String> response = new HashMap<>();
        response.put("message", "Author with ID " + authorId + " has been successfully deleted.");

        return Response.ok(response).build();
    }
}
