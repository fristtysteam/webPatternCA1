package dao;

import business.Book;
import business.BookGenre;
import business.Genre;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookGenreDao extends Dao implements BookGenreDaoInterface {
    public BookGenreDao(String dbName) {
        super(dbName);
    }


    //CREATE TABLE bookGenres(
    //   bookID int NOT NULL,
////genreID int NOT NULL,
    //FOREIGN KEY(bookID) REFERENCES books(bookID),
    //FOREIGN KEY(genreID) REFERENCES genres(genreID)


    //addBookGenre
    public int addBookGenre(BookGenre bookGenre) {
        String query = "INSERT INTO bookgenres (bookID,genreID) VALUES (?, ?)";
        int rowsAffected = 0;

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, bookGenre.getBookID());
            ps.setInt(2, bookGenre.getGenreID().getGenreID());

            rowsAffected = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            freeConnectionUpdate();
        }

        return rowsAffected;
    }

    //deleteBookGenre

    public int deleteBookGenreByID(int genreID) {
        int rowsAffected = 0;
        try {
            String query = "DELETE FROM bookgenres WHERE genreID = ?";
            con = getConnection();
            ps = con.prepareStatement(query);
            ps.setInt(1, genreID);

            rowsAffected = ps.executeUpdate();

        } catch (SQLException se) {
            System.out.println(se.getMessage());
            System.out.println("Failed to Delete Book genre");
        } finally {
            freeConnectionUpdate();
        }

        return rowsAffected;
    }
    //getDuplicateGenre

    public boolean checkForDuplicate(int genreID, int bookID) {
        int count;
        boolean flag = false;

        try {
            String query = "SELECT count(*) FROM bookgenres WHERE genreID = ? AND bookID = ?";
            con = getConnection();
            ps = con.prepareStatement(query);
            ps.setInt(1, genreID);
            ps.setInt(2, bookID);
            rs = ps.executeQuery();

            if (rs.next()) {
                count = rs.getInt(1);
                if (count >= 1) {
                    flag = true;
                }
            }

        } catch (SQLException se) {
            System.out.println(se.getMessage());
            System.out.println("duplicate error");
        } finally {
            freeConnection();
        }

        return flag;
    }


    @Override
    public List<Book> getBooksByGenre(String genreName) {
        List<Book> books = new ArrayList<>();
        String query = "SELECT * FROM bookgenres WHERE genreID = (select genreID from genres where genreName = ?)";


        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, genreName);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Book book = new Book(
                        rs.getInt("bookID"),
                        rs.getString("bookName"),
                        rs.getString("author"),
                        rs.getString("description"),
                        rs.getInt("quantity")
                );
                books.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            freeConnection();
        }
        return books;
    }

    @Override
    public int addGenreToBook(int bookID, int genreID) {

        String query = "INSERT INTO bookgenres (bookID,genreID) VALUES (?, ?)";
        int rowsAffected = 0;

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, bookID);
            ps.setInt(2, genreID);

            rowsAffected = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            freeConnection();
        }

        return rowsAffected;
    }


    @Override
    public int deleteGenreToBook(int bookID, int genreID) {

        String query = "DELETE FROM bookgenres WHERE bookID = ? AND genreID = ?";
        int rowsAffected = 0;

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, bookID);
            ps.setInt(2, genreID);


            rowsAffected = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            freeConnection();
        }

        return rowsAffected;
    }


    @Override
    public boolean duplicateGenreInBook(int bookID, int genreID) {

        boolean flag = false;

        try {
            String query = "SELECT * FROM bookgenres WHERE bookID = ? AND genreID = ?";
            con = getConnection();
            ps = con.prepareStatement(query);
            ps.setInt(1, genreID);
            ps.setInt(2, bookID);
            rs = ps.executeQuery();

            if (rs.next()) {
                    flag = true;
            }
        }catch(SQLException se){
            System.out.println(se.getMessage());
            System.out.println("Exception found error thrown");
        }
        finally {
            freeConnection();
        }

        return flag;
    }
}
