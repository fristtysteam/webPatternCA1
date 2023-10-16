package dao;
import business.Book;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookDao extends Dao {
    private String dbName;
    public BookDao(String dbName) {
        this.dbName = dbName;
    }

    /**
     * Gets a list of all books in the database.
     *
     * @return a list of all books in the database
     * @throws SQLException if there is an error accessing the database
     */
    @Override
    public List<Book> getAllBooks() throws SQLException {
        List<Book> books = new ArrayList<>();
        try {
            String query = "SELECT * FROM books";
            con = getConnection();
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next()) {
                books.add(new Book(
                        rs.getInt("bookID"),
                        rs.getString("bookName"),
                        rs.getString("author"),
                        rs.getString("description"),
                        rs.getInt("quantity")
                ));
            }

        } catch (SQLException se) {
            System.out.println(se.getMessage());
            System.out.println("Something Went Wrong");
        } finally {
            freeConnection();
        }

        return books;
    }


}
