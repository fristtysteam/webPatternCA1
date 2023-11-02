package dao;

import business.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class BookDaoTest {

    private BookDao bookDao;

    @BeforeEach
    void setUp() {
        bookDao = new BookDao("testLibrary");
    }

    @Test
    void getAllBooks() {
        List<Book> books = bookDao.getAllBooks();

        assertFalse(books.isEmpty());
    }

    @Test
    void getBookByID() {
        // Replace '1' with a valid book ID from your test database
        int bookID = 1;
        Book book = bookDao.getBookByID(bookID);

        assertNotNull(book);
        assertEquals(bookID, book.getBookID());
    }

    @Test
    void getBookByIDMoreThanDatabase() {
        // Replace '1' with a valid book ID from your test database
        int bookID = 100;
        Book book = bookDao.getBookByID(bookID);

        assertEquals(null, book);
    }

    @Test
    void updateBookQuantity() {
        int bookID = 1;

        int rowsIncreased = bookDao.updateBookQuantity(bookID, true);
        assertEquals(1, rowsIncreased);

        int rowsDecreased = bookDao.updateBookQuantity(bookID, false);
        assertEquals(1, rowsDecreased);
    }

    @Test
    void addBook() {
        Book newBook = new Book(6, "The Book", "Matt", "The only book you will ever need", 5);
        int rowsAffected = bookDao.addBook(newBook);

        assertEquals(1, rowsAffected);

        int bookID = newBook.getBookID();
        bookDao.deleteBook(bookID);
    }

    @Test
    void addBookNegativeQuantity() {
        Book newBook = new Book(6, "The Book", "Matt", "The only book you will ever need", -1000);
        int rowsAffected = bookDao.addBook(newBook);

        assertEquals(0, rowsAffected);
    }

    @Test
    void deleteBook() {
        Book newBook = new Book(6, "How To Solve 100% Of Your Problem In Life", "Matt", "You get jeri discord", 5);
       bookDao.addBook(newBook);
        int rowAffected = bookDao.deleteBook(6);
       bookDao.updateIncrement("books", 6);

       assertEquals(1,rowAffected);
    }


    @Test
    void deleteBookThatDoesntExist() {
        int rowAffected = bookDao.deleteBook(100);
        assertEquals(0,rowAffected);
    }
}