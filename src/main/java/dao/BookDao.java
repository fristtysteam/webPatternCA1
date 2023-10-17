package dao;

import business.Book;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class Bookdao extends Dao implements BookDaoInterface {

    public Bookdao(String dbName) {
        super(dbName);
    }

    @Override
    public List<Book> getAllBooks() throws SQLException {
        String query = "SELECT * FROM books";
        ResultSet rs = ps.executeQuery(query);
        List<Book> books = new ArrayList<>();
        while (rs.next()) {
            Book book = new Book();
            book.setBookID(rs.getInt("bookID"));
            book.setBookName(rs.getString("bookName"));
            book.setAuthor(rs.getString("author"));
            book.setDescription(rs.getString("description"));
            book.setQuantity(rs.getInt("quantity"));
            books.add(book);
        }
        return books;
    }

    @Override
    public Book getBookByID(int bookID) throws SQLException {
        String query = "SELECT * FROM books WHERE bookID = ?";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setInt(1, bookID);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            Book book = new Book();
            book.setBookID(rs.getInt("bookID"));
            book.setBookName(rs.getString("bookName"));
            book.setAuthor(rs.getString("author"));
            book.setDescription(rs.getString("description"));
            book.setQuantity(rs.getInt("quantity"));
            return book;
        } else {
            return null;
        }
    }

    @Override
    public int updateBookQuantity(int bookID, boolean increase) throws SQLException {
        String query = "UPDATE books SET quantity = ? WHERE bookID = ?";
        PreparedStatement ps = con.prepareStatement(query);
        int quantity = getBookByID(bookID).getQuantity();
        if (increase) {
            quantity++;
        } else {
            quantity--;
        }
        ps.setInt(1, quantity);
        ps.setInt(2, bookID);
        return ps.executeUpdate();
    }
}