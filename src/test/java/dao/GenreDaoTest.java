package dao;

import business.Genre;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GenreDaoTest {
    private GenreDao genreDao;
    private List<Genre> testGenres;
    @BeforeEach
    void setUp() {
        genreDao = new GenreDao("testlibrary");
    }

    @Test
    void getAllGenres() {
        List<Genre> genres = genreDao.getAllGenres();

        assertFalse(genres.isEmpty());
    }


    @Test
    void getGenreByID() {
        int genreID = 1;
        Genre genre = genreDao.getGenreByID(genreID);

        assertEquals(1, genre.getGenreID());
    }

    @Test
    void getGenreByIDNoID() {
        int genreID = 100;
        Genre genre = genreDao.getGenreByID(genreID);

        assertEquals(null, genre);
    }



    }
