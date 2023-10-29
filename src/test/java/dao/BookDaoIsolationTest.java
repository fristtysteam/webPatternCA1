package dao;

import business.Book;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

class BookDaoIsolationTest {

    @Test
    void getAllBooks() throws SQLException {
        Dao.mock = true;
        Book bookA = new Book(1, "titleA", "me", "desc", 10);
        Book bookB = new Book(2, "titleA", "me", "desc", 10);
        List<Book> books = new ArrayList<>();
        books.add(bookA);
        books.add(bookB);

        // Create mock objects
        Connection dbConn = mock(Connection.class);
        PreparedStatement ps = mock(PreparedStatement.class);
        ResultSet rs = mock(ResultSet.class);

        when(dbConn.prepareStatement("SELECT * FROM books")).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);

        when(rs.next()).thenReturn(true, true, false);
        //fill results
        when(rs.getInt("bookID")).thenReturn(bookA.getBookID(), bookB.getBookID());
        when(rs.getString("bookName")).thenReturn(bookA.getBookName(), bookB.getBookName());
        when(rs.getString("author")).thenReturn(bookA.getAuthor(), bookB.getAuthor());
        when(rs.getString("description")).thenReturn(bookA.getDescription(), bookB.getDescription());
        when(rs.getInt("quantity")).thenReturn(bookA.getQuantity(), bookB.getQuantity());

        BookDao bookDao = new BookDao(dbConn);
        List<Book> result = bookDao.getAllBooks();

        Dao.mock = false;
        assertEquals(books, result);
    }

    @Test
    void getBookByID() {
    }

    @Test
    void updateBookQuantity() {
    }
}