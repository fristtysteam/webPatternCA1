package dao;

import business.Book;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookDao extends Dao implements BookDaoInterface {

    public BookDao(String dbName) {
        super(dbName);
    }
    public BookDao(Connection con) {super(con); }

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
            System.out.println("Something went wrong with getAllBooks");
            System.out.println(e.getMessage());
        }finally {
            freeConnection();
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
            System.out.println("Something went wrong with get all getBookByID");
            System.out.println(e.getMessage());
        }finally {
            freeConnection();
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
        String query = increase ? "UPDATE books SET quantity = quantity + 1 WHERE bookID = ?" :
                "UPDATE books SET quantity = quantity - 1 WHERE bookID = ?";
        int rowsAffected = 0;

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, bookID);
            rowsAffected = ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Something went wrong with get all updateBookQuantity");
            System.out.println(e.getMessage());
        }finally {
            freeConnectionUpdate();
        }

        return rowsAffected;
    }


@Override
    public int addBook(Book book) {
        String query = "INSERT INTO books (bookName, author, description, quantity) VALUES (?, ?, ?, ?)";
        int rowsAffected = 0;

        if(book.getQuantity() <= 0 ){
            return 0;
        }


        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, book.getBookName());
            ps.setString(2, book.getAuthor());
            ps.setString(3, book.getDescription());
            ps.setInt(4, book.getQuantity());

            rowsAffected = ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Something went wrong with get all addBook");
            System.out.println(e.getMessage());
        }finally {
            freeConnection();
        }

        return rowsAffected;
    }
    @Override
    public int deleteBook(int bookID) {
        String query = "DELETE FROM books WHERE bookID = ?";
        int rowsAffected = 0;

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, bookID);
            rowsAffected = ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Something went wrong with get all deleteBook");
            System.out.println(e.getMessage());
        }finally {
            freeConnection();
        }

        return rowsAffected;
    }
}
