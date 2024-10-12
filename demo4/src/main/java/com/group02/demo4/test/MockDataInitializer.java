package com.group02.demo4.test;

import com.group02.demo4.services.AuthorService;
import com.group02.demo4.services.BookService;
import com.group02.demo4.models.Author;
import com.group02.demo4.models.Book;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MockDataInitializer {

    // Method to initialize mock data for authors and books
    public static void initializeMockData() {
        BookService bookService = new BookService();
        AuthorService authorService = new AuthorService();

        // Create a list of authors with basic information
        Author author1 = new Author(1, "Lesya Ukrainka", "Ukraine", new ArrayList<>());
        Author author2 = new Author(2, "Ivan Franko", "Ukraine", new ArrayList<>());
        Author author3 = new Author(3, "Taras Shevchenko", "Ukraine", new ArrayList<>());
        Author author4 = new Author(4, "Oksana Zabuzhko", "Ukraine", new ArrayList<>());
        Author author5 = new Author(5, "Lina Kostenko", "Ukraine", new ArrayList<>());
        Author author6 = new Author(6, "Vasyl Stus", "Ukraine", new ArrayList<>());
        Author author7 = new Author(7, "Mykola Khvylovy", "Ukraine", new ArrayList<>());
        Author author8 = new Author(8, "Volodymyr Vynnychenko", "Ukraine", new ArrayList<>());
        Author author9 = new Author(9, "Vasyl Symonenko", "Ukraine", new ArrayList<>());
        Author author10 = new Author(10, "Olena Pchilka", "Ukraine", new ArrayList<>());
        Author author11 = new Author(11, "Grigoriy Skovoroda", "Ukraine", new ArrayList<>());
        Author author12 = new Author(12, "Yurii Andrukhovych", "Ukraine", new ArrayList<>());
        Author author13 = new Author(13, "Serhiy Zhadan", "Ukraine", new ArrayList<>());
        Author author14 = new Author(14, "Pavlo Tychyna", "Ukraine", new ArrayList<>());
        Author author15 = new Author(15, "Mykhailo Kotsiubynsky", "Ukraine", new ArrayList<>());
        Author author16 = new Author(16, "Ivan Nechuy-Levytsky", "Ukraine", new ArrayList<>());
        Author author17 = new Author(17, "Panasy Myrny", "Ukraine", new ArrayList<>());
        Author author18 = new Author(18, "Marko Vovchok", "Ukraine", new ArrayList<>());
        Author author19 = new Author(19, "Oles Honchar", "Ukraine", new ArrayList<>());
        Author author20 = new Author(20, "Mykola Kulish", "Ukraine", new ArrayList<>());

        // Create a list of books and associate them with authors
        List<Book> books = Arrays.asList(
            new Book(1, "Forest Song", "12345", List.of(author1), 1911),
            new Book(2, "Stone Host", "67890", List.of(author1), 1912),
            new Book(3, "Zakhar Berkut", "11111", List.of(author2), 1883),
            new Book(4, "Crossroads", "22222", List.of(author2), 1899),
            new Book(5, "Kobzar", "33333", List.of(author3), 1840),
            new Book(6, "Haydamaky", "44444", List.of(author3), 1841),
            new Book(7, "Fieldwork in Ukrainian Sex", "55555", List.of(author4), 1996),
            new Book(8, "Museum of Abandoned Secrets", "66666", List.of(author4), 2009),
            new Book(9, "Marusya Churai", "77777", List.of(author5), 1979),
            new Book(10, "In the Orchards", "88888", List.of(author5), 1958),
            new Book(11, "Winter Trees", "99999", List.of(author6), 1970),
            new Book(12, "Selected Poems", "10101", List.of(author6), 1985),
            new Book(13, "The Blue Leaf Palms", "20202", List.of(author7), 1922),
            new Book(14, "At the Crossroads", "30303", List.of(author7), 1927),
            new Book(15, "Black Council", "40404", List.of(author8), 1909),
            new Book(16, "On the Edge", "50505", List.of(author8), 1916),
            new Book(17, "Lyric Poems", "60606", List.of(author9), 1962),
            new Book(18, "Pavlo Zahrebelny", "70707", List.of(author9), 1965),
            new Book(19, "The Village", "80808", List.of(author10), 1890),
            new Book(20, "Poems", "90909", List.of(author10), 1913),
            new Book(21, "Skovoroda’s Fables", "11212", List.of(author11), 1795),
            new Book(22, "Dialogues", "12121", List.of(author11), 1775),
            new Book(23, "Perverzion", "13131", List.of(author12), 1996),
            new Book(24, "Twelve Circles", "14141", List.of(author12), 2003),
            new Book(25, "Mesopotamia", "15151", List.of(author13), 2014),
            new Book(26, "Antenna", "16161", List.of(author13), 2021),
            new Book(27, "Wind from the South", "17171", List.of(author14), 1920),
            new Book(28, "Cherry Blossoms", "18181", List.of(author14), 1925),
            new Book(29, "Shadows of Forgotten Ancestors", "19191", List.of(author15), 1911),
            new Book(30, "Fata Morgana", "20202", List.of(author15), 1902),
            new Book(31, "Clouds", "21212", List.of(author16), 1874),
            new Book(32, "Kaidashev Family", "23232", List.of(author16), 1875),
            new Book(33, "Do Not Cry, Seraphim!", "24242", List.of(author17), 1883),
            new Book(34, "Marusya", "25252", List.of(author17), 1871),
            new Book(35, "Marusya", "26262", List.of(author18), 1859),
            new Book(36, "The Cossack’s Revenge", "27272", List.of(author18), 1863),
            new Book(37, "Man and Weapon", "28282", List.of(author19), 1946),
            new Book(38, "Cathedral", "29292", List.of(author19), 1968),
            new Book(39, "Pathetic Sonata", "30303", List.of(author20), 1929),
            new Book(40, "Myna Mazaylo", "31313", List.of(author20), 1929),
            new Book(41, "Three Rings", "32323", List.of(author4), 1994),
            new Book(42, "Ariel", "33333", List.of(author1), 1899),
            new Book(43, "Apostles of Freedom", "34343", List.of(author2), 1905),
            new Book(44, "Goya’s Terrible Paintings", "35353", List.of(author6), 1968),
            new Book(45, "A Wreath of Sonnets", "36363", List.of(author5), 1971),
            new Book(46, "Songs of the Morning", "37373", List.of(author3), 1842),
            new Book(47, "The Stormy Years", "38383", List.of(author13), 2005),
            new Book(48, "Wild West Wind", "39393", List.of(author17), 1955),
            new Book(49, "A Strange Woman", "40303", List.of(author19), 1973),
            new Book(50, "Fruits of the Passion", "41414", List.of(author10), 1990)
        );

        // Add books to the book service and associate each book with its authors
        for (Book book : books) {
            bookService.addBook(book);
            for (Author author : book.getAuthors()) {
                // Add the book to the author's list of books
                author.getBooks().add(book);
            }
        }

        // Create a list of authors and add them to the author service
        List<Author> authors = Arrays.asList(author1, author2, author3, author4, author5, author6, author7, author8, author9, author10, author11, author12, author13, author14, author15, author16, author17, author18, author19, author20);
        for (Author author : authors) {
            authorService.addAuthor(author);
        }
    }
}
