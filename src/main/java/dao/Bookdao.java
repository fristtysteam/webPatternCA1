package dao;

import business.Book;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Bookdao extends Dao implements BookDaoInterface {

    public Bookdao(String dbName) {
        super(dbName);
    }

    /**
     * Get a list of all books in the library.
     *
     * @return A list of books.
     */
    @Override
    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();

        String query = "SELECT * FROM books";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                books.add(new Book(
                        rs.getInt("bookID"),
                        rs.getString("bookName"),
                        rs.getString("author"),
                        rs.getString("description"),
                        rs.getInt("quantity")
                ));
            }
        } catch (SQLException e) {
            // Handle or log the exception
            e.printStackTrace();
        }

        return books;
    }

    /**
     * Get a book by its ID.
     *
     * @param bookID The ID of the book to retrieve.
     * @return The Book object if found, otherwise null.
     */
    @Override
    public Book getBookByID(int bookID) {
        Book book = null;

        String query = "SELECT * FROM books WHERE bookID = ?";

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, bookID);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    book = new Book(
                            rs.getInt("bookID"),
                            rs.getString("bookName"),
                            rs.getString("author"),
                            rs.getString("description"),
                            rs.getInt("quantity")
                    );
                }
            }
        } catch (SQLException e) {
            // Handle or log the exception
            e.printStackTrace();
        }

        return book;
    }

    /**
     * Update the quantity of a book in the library.
     *
     * @param bookID   The ID of the book to update.
     * @param increase True if you want to increase the quantity, false to decrease.
     * @return The number of rows affected by the update.
     */
    @Override
    public int updateBookQuantity(int bookID, boolean increase) {
        String query = increase ? "UPDATE books SET quantity = quantity + 1 WHERE bookID = ?"
                : "UPDATE books SET quantity = quantity - 1 WHERE bookID = ?";
        int rowsAffected = 0;

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, bookID);
            rowsAffected = ps.executeUpdate();
        } catch (SQLException e) {
            // Handle or log the exception
            e.printStackTrace();
        }

        return rowsAffected;
    }
}
