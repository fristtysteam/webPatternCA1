package dao;

import business.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
    void updateBookQuantity() {
        int bookID = 1;

        int rowsIncreased = bookDao.updateBookQuantity(bookID, true);
        assertEquals(1, rowsIncreased);

        int rowsDecreased = bookDao.updateBookQuantity(bookID, false);
        assertEquals(1, rowsDecreased);
    }

    @Test
    void addBook() {
        Book newBook = new Book(6,"The Book", "Matt", "The only book you will ever need", 5);
        int rowsAffected = bookDao.addBook(newBook);

        assertEquals(1, rowsAffected);

        int bookID = newBook.getBookID();
        bookDao.deleteBook(bookID);
    }

    @Test
    void deleteBook() {
        Book newBook = new Book(3,"To Be Deleted", "Author", "Description", 3);
        bookDao.addBook(newBook);

        int bookID = newBook.getBookID();
        int rowsAffected = bookDao.deleteBook(bookID);
        assertEquals(1, rowsAffected);
    }
    }
