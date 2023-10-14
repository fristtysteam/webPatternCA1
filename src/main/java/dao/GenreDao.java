package dao;

import business.Genre;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GenreDao extends Dao implements GenreDaoInterface{

    public GenreDao(String dbName) {
        super(dbName);
    }

    @Override
    public List<Genre> getAllGenres() {
        List<Genre> genres = new ArrayList<>();

        try{
            String query = "SELECT * FROM genres";
            con = getConnection();
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();

            while(rs.next()){
                genres.add(new Genre(
                        rs.getInt("genreID"),
                        rs.getString("genreName")
                ));
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
    public Genre getGenreByID(int genreID) {
        Genre genre = null;

        try{
            String query = "SELECT * FROM genres";
            con = getConnection();
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();

            if(rs.next()){
                genre = new Genre(
                        rs.getInt("genreID"),
                        rs.getString("genreName")
                );
            }
        }
        catch(SQLException se){
            System.out.println(se.getMessage());
            System.out.println("something went wrong");
        }
        finally {
            freeConnection();
        }

        return genre;
    }
}
