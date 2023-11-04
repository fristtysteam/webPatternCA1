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

/**
 * @author Marco
 */
public class BookGenreDao extends Dao implements BookGenreDaoInterface {
    public BookGenreDao(String dbName) {
        super(dbName);
    }


    //CREATE TABLE bookGenres(
    //   bookID int NOT NULL,
////genreID int NOT NULL,
    //FOREIGN KEY(bookID) REFERENCES books(bookID),
    //FOREIGN KEY(genreID) REFERENCES genres(genreID)




    @Override
    public List<Genre> getGenreByBookID(int bookID) {
        String query = "SELECT * FROM bookgenres WHERE bookID = ?";
        List<Genre> genres = new ArrayList<>();
        GenreDao genreDao = new GenreDao(dbName);

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, bookID);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {


                genres.add(genreDao.getGenreByID(rs.getInt("genreID")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            freeConnection();
        }
        return genres;
    }


    @Override
    public List<Book> getBooksByGenre(String genreName) {
        List<Book> books = new ArrayList<>();
        String query = "SELECT * FROM bookgenres WHERE genreID = (select genreID from genres where genreName = ?)";
        BookDao bookDao = new BookDao(dbName);

        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, genreName);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                books.add(bookDao.getBookByID(rs.getInt("bookID")));
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
            System.out.println("error");        } finally {
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
