package dao;

import business.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class BookDaoTest {
    private BookDaoStub bookDao;

    class BookDaoStub extends BookDao {
        public BookDaoStub() {
            super("test_library");
        }


        @BeforeEach
        void setUp() {
            bookDao = new BookDaoStub();
        }

        @Test
        void testGetAllBooks() {
            List<Book> books = bookDao.getAllBooks();
            assertNotNull(books);
            assertEquals(2, books.size());
            assertEquals("Book 1", books.get(0).getBookName());
            assertEquals("Book 2", books.get(1).getBookName());
        }
    }
}