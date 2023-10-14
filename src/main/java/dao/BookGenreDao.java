package dao;

import business.Book;
import business.Genre;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookGenreDao extends Dao implements BookGenreDaoInterface{
    public BookGenreDao(String dbName) {
        super(dbName);
    }

    @Override
    public List<Genre> getGenreByBookID(int bookID) {
        List<Genre> genres = new ArrayList<>();
        GenreDao genreDao = new GenreDao("library");

        try{
            String query = "SELECT * FROM bookgenres WHERE bookID = ?";
            con = getConnection();
            ps = con.prepareStatement(query);
            ps.setInt(1, bookID);
            rs = ps.executeQuery();

            while(rs.next()){
                genres.add(genreDao.getGenreByID(rs.getInt("genreID")));
            }
        }
        catch(SQLException se){
            System.out.println(se.getMessage());
            System.out.println("something went wrong");
        }
        finally {
            freeConnection();
        }

        return genres;
    }

    @Override
    public List<Book> getBooksByGenre(String genreName) {
        List<Book> books = new ArrayList<>();
        BookDao bookDao = new BookDao("library");

        try{
            String query = "SELECT * FROM bookgenres WHERE genreID = " +
                    "(select genreID from genres where genreName = ?);";
            con = getConnection();
            ps = con.prepareStatement(query);
            ps.setString(1, genreName);
            rs = ps.executeQuery();

            while(rs.next()){
                books.add(bookDao.getBookByID(rs.getInt("bookID")));
            }
        }
        catch(SQLException se){
            System.out.println(se.getMessage());
            System.out.println("something went wrong");
        }
        finally {
            freeConnection();
        }

        return books;
    }
}
