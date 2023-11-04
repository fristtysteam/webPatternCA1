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

    /**
     * @author playerzer0-ui
     */
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

    /**
     * @author CheePheng
     */
    @Test
    void getBookByID() throws SQLException {
        Dao.mock = true;
        Book expectedBook = new Book(1, "titleA", "me", "desc", 10);

        Connection dbConn = mock(Connection.class);
        PreparedStatement ps = mock(PreparedStatement.class);
        ResultSet rs = mock(ResultSet.class);

        when(dbConn.prepareStatement("SELECT * FROM books WHERE bookID = ?")).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);

        when(rs.next()).thenReturn(true, false);
        when(rs.getInt("bookID")).thenReturn(expectedBook.getBookID());
        when(rs.getString("bookName")).thenReturn(expectedBook.getBookName());
        when(rs.getString("author")).thenReturn(expectedBook.getAuthor());
        when(rs.getString("description")).thenReturn(expectedBook.getDescription());
        when(rs.getInt("quantity")).thenReturn(expectedBook.getQuantity());

        BookDao bookDao = new BookDao(dbConn);
        Book result = bookDao.getBookByID(1);
        verify(ps).setInt(1,1);

        Dao.mock = false;
        assertEquals(expectedBook, result);
    }

    /**
     * @author playerzer0-ui
     */
    @Test
    void updateBookQuantity() throws SQLException {
        Dao.mock = true;

        String query =  "UPDATE books set quantity = CASE\n" +
                "WHEN quantity + (?) < 0 THEN 0\n" +
                "ELSE quantity + (?) END where bookID = ?";

        // Create mock objects
        Connection dbConn = mock(Connection.class);
        PreparedStatement ps = mock(PreparedStatement.class);

        when(dbConn.prepareStatement(query)).thenReturn(ps);
        when(ps.executeUpdate()).thenReturn(1);

        BookDao bookDao = new BookDao(dbConn);
        int result = bookDao.updateBookQuantity(1, 1);
        verify(ps).setInt(1,1);
        verify(ps).setInt(2,1);

        Dao.mock = false;
        assertEquals(1, result);
    }

    /**
     * @author playerzer0-ui
     */
    @Test
    void addBook() throws SQLException {
        Book expectedBook = new Book(1, "titleA", "me", "desc", 10);
        Dao.mock = true;

        String query = "INSERT INTO books (bookName, author, description, quantity) VALUES (?, ?, ?, ?)";

        // Create mock objects
        Connection dbConn = mock(Connection.class);
        PreparedStatement ps = mock(PreparedStatement.class);

        when(dbConn.prepareStatement(query)).thenReturn(ps);
        when(ps.executeUpdate()).thenReturn(1);

        BookDao bookDao = new BookDao(dbConn);
        int result = bookDao.addBook(expectedBook);
        verify(ps).setString(1, expectedBook.getBookName());
        verify(ps).setString(2, expectedBook.getAuthor());
        verify(ps).setString(3, expectedBook.getDescription());
        verify(ps).setInt(4, expectedBook.getQuantity());

        assertEquals(1, result);
    }

    /**
     * @author Marco
     */
    @Test
    void deleteBook() throws SQLException{
        Dao.mock = true;

        // Create mock objects
        Connection dbConn = mock(Connection.class);
        PreparedStatement ps = mock(PreparedStatement.class);
        //ResultSet rs = mock(ResultSet.class);

        when(dbConn.prepareStatement("DELETE FROM books WHERE bookID = ?")).thenReturn(ps);
        when(ps.executeUpdate()).thenReturn(1);
        //when(rs.next()).thenReturn(true, false);

        BookDao bookDao = new BookDao(dbConn);
        int result = bookDao.deleteBook(1);
        verify(ps, times(1)).executeUpdate();

        Dao.mock = false;
        assertEquals(1, result);
    }

}