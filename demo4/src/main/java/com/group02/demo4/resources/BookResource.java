package com.group02.demo4.resources;

import com.group02.demo4.exceptions.DataNotFoundException;
import com.group02.demo4.models.Book;
import com.group02.demo4.models.Author;
import com.group02.demo4.services.BookService;
import com.group02.demo4.services.AuthorService;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.*;

@Path("/books")
public class BookResource {

    // Service layer instances for managing books and authors
    private BookService bookService = new BookService();
    private AuthorService authorService = new AuthorService();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBooks(@QueryParam("year") Integer year) {
        // Retrieve the list of all books from the service
        List<Book> books = bookService.getAllBooks();
        if (books.isEmpty()) {
            // Throw exception if no books are found
            throw new DataNotFoundException("No books found");
        }

        List<Map<String, Object>> response = new ArrayList<>();

        // Filter books by the provided publication year if the parameter is given
        if (year != null) {
            for (Book book : books) {
                if (book.getPublicationYear() == year) {
                    // Create a response map for each book that matches the year filter
                    Map<String, Object> bookData = new HashMap<>();
                    bookData.put("id", book.getId());
                    bookData.put("title", book.getTitle());
                    bookData.put("isbn", book.getIsbn());
                    bookData.put("publicationYear", book.getPublicationYear());
                    response.add(bookData);
                }
            }
        } else {
            // Add all books to the response if no year filter is provided
            for (Book book : books) {
                Map<String, Object> bookData = new HashMap<>();
                bookData.put("id", book.getId());
                bookData.put("title", book.getTitle());
                bookData.put("isbn", book.getIsbn());
                bookData.put("publicationYear", book.getPublicationYear());
                response.add(bookData);
            }
        }

        // Throw exception if no books match the provided year filter
        if (response.isEmpty()) {
            throw new DataNotFoundException("No books found for the given year: " + year);
        }

        return Response.ok(response).build();
    }

    @GET
    @Path("/{bookId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBook(@PathParam("bookId") long bookId) {
        // Retrieve the book by its ID
        Book book = bookService.getBookById(bookId);

        if (book == null) {
            // Throw exception if the book is not found
            throw new DataNotFoundException("Book with ID " + bookId + " not found");
        }

        // Create a response map with the book details
        Map<String, Object> response = new HashMap<>();
        response.put("id", book.getId());
        response.put("title", book.getTitle());
        response.put("isbn", book.getIsbn());
        response.put("publicationYear", book.getPublicationYear());

        // Add a link to get the list of authors for the book
        Map<String, String> authorListLink = new HashMap<>();
        authorListLink.put("rel", "authors");
        authorListLink.put("href", "/demo4/api/books/" + book.getId() + "/authors");

        response.put("links", Collections.singletonList(authorListLink));

        return Response.ok(response).build();
    }

    @GET
    @Path("/{bookId}/authors")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBookWithAuthors(@PathParam("bookId") long bookId) {
        // Retrieve the book by its ID
        Book book = bookService.getBookById(bookId);

        if (book == null) {
            // Throw exception if the book is not found
            throw new DataNotFoundException("Book with ID " + bookId + " not found");
        }

        if (book.getAuthors() == null || book.getAuthors().isEmpty()) {
            // Throw exception if no authors are associated with the book
            throw new DataNotFoundException("No authors found for book with ID " + bookId);
        }

        // Create a response map with the list of authors of the book
        Map<String, Object> response = new HashMap<>();
        List<Map<String, Object>> authorsList = new ArrayList<>();
        for (Author author : book.getAuthors()) {
            Map<String, Object> authorInfo = new HashMap<>();
            authorInfo.put("id", author.getId());
            authorInfo.put("name", author.getName());
            authorInfo.put("nationality", author.getNationality());
            authorsList.add(authorInfo);
        }
        response.put("authors", authorsList);

        return Response.ok(response).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"user", "admin"})
    public Response addBook(Book book) {
        // Validate that the book has both a title and an ISBN
        if (book.getTitle() == null || book.getIsbn() == null) {
            throw new WebApplicationException("Book title and ISBN are required", Response.Status.BAD_REQUEST);
        }

        // Add the new book to the service
        bookService.addBook(book);
        return Response.status(Response.Status.CREATED).entity(book).build();
    }

    @PUT
    @Path("/{bookId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"user", "admin"})
    public Response updateBook(@PathParam("bookId") long bookId, Book updatedBook) {
        // Validate that the updated book has both a title and an ISBN
        if (updatedBook.getTitle() == null || updatedBook.getIsbn() == null) {
            throw new WebApplicationException("Book title and ISBN are required", Response.Status.BAD_REQUEST);
        }

        // Update the book in the service
        Book book = bookService.updateBook(bookId, updatedBook);

        if (book == null) {
            // Throw exception if the book is not found
            throw new DataNotFoundException("Book with ID " + bookId + " not found");
        }

        return Response.ok(book).build();
    }

    @PUT
    @Path("/{bookId}/authors/{authorId}")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({"user", "admin"})
    public Response addAuthorToBook(@PathParam("bookId") long bookId, @PathParam("authorId") long authorId) {
        // Retrieve the book and author by their respective IDs
        Book book = bookService.getBookById(bookId);
        Author author = authorService.getAuthorById(authorId);

        if (book == null) {
            // Throw exception if the book is not found
            throw new DataNotFoundException("Book with ID " + bookId + " not found");
        }

        if (author == null) {
            // Throw exception if the author is not found
            throw new DataNotFoundException("Author with ID " + authorId + " not found");
        }

        // Add the author to the book's list of authors if not already present
        if (!book.getAuthors().contains(author)) {
            book.getAuthors().add(author);
        }
        // Add the book to the author's list of books if not already present
        if (!author.getBooks().contains(book)) {
            author.getBooks().add(book);
        }

        // Update both book and author in their respective services
        bookService.updateBook(bookId, book);
        authorService.updateAuthor(authorId, author);

        // Create a response map with updated book details
        Map<String, Object> response = new HashMap<>();
        response.put("id", book.getId());
        response.put("title", book.getTitle());
        response.put("isbn", book.getIsbn());
        response.put("publicationYear", book.getPublicationYear());

        return Response.ok(response).build();
    }

    @DELETE
    @Path("/{bookId}")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("admin")
    public Response deleteBook(@PathParam("bookId") long bookId) {
        // Attempt to delete the book by ID
        boolean isDeleted = bookService.deleteBook(bookId);

        if (!isDeleted) {
            // Throw exception if the book is not found
            throw new DataNotFoundException("Book with ID " + bookId + " not found");
        }

        // Create a response message indicating successful deletion
        Map<String, String> response = new HashMap<>();
        response.put("message", "Book with ID " + bookId + " has been successfully deleted.");

        return Response.ok(response).build();
    }

}
