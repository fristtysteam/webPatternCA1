package dao;

import business.Book;
import business.Genre;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BookGenreDaoTest {
    private final BookGenreDao bookGenreDao = new BookGenreDao("testLibrary");
    @Test
    void getGenreByBookID() {

      List<Genre> genres = bookGenreDao.getGenreByBookID(1);
        assertEquals(1,genres.get(0).getGenreID());
        assertEquals(2,genres.get(1).getGenreID());


    }
    @Test
    void getGenreByBookID2() {

        List<Genre> genres = bookGenreDao.getGenreByBookID(-1);

        assertTrue(genres.isEmpty());


    }

    @Test
    void getBooksByGenre() {
        List<Book> books = bookGenreDao.getBooksByGenre("comedy");
assertEquals(1,books.get(0).getBookID());
assertEquals(2,books.get(1).getBookID());
assertEquals(4,books.get(2).getBookID());



    }
    @Test
    void getBooksByGenre2() {
        List<Book> books = bookGenreDao.getBooksByGenre("jerry");
      assertTrue(books.isEmpty());



    }

    @Test
    void addGenreToBook() {



    }

    @Test
    void deleteGenreToBook() {



    }

    @Test
    void duplicateGenreInBook() {


    }
}