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
int books = bookGenreDao.addGenreToBook(5,1);
        List<Genre> booksList = bookGenreDao.getGenreByBookID(5);
        bookGenreDao.deleteGenreToBook(5,1);
        assertEquals(1,books);
        assertEquals(1,booksList.get(1).getGenreID());



    }
    @Test
    void addGenreToBook2(){
        int books = bookGenreDao.addGenreToBook(50,1);
        assertEquals(0,books);

    }
    @Test
    void addGenreToBook3(){
        int books = bookGenreDao.addGenreToBook(1,50);
        assertEquals(0,books);

    }

    @Test
    void deleteGenreToBook() {
 int books = bookGenreDao.deleteGenreToBook(5,2);
 bookGenreDao.addGenreToBook(5, 2);
 assertEquals(1,books);



    }
    @Test
    void deleteGenreToBook2() {
        int books = bookGenreDao.deleteGenreToBook(50,2);

        assertEquals(0,books);



    }
    @Test
    void deleteGenreToBook3() {
        int books = bookGenreDao.deleteGenreToBook(5,50);

        assertEquals(0,books);

    }




    @Test
    void duplicateGenreInBook() {
boolean books = bookGenreDao.duplicateGenreInBook(1,1);
assertEquals(true,books);

    }
    @Test
    void duplicateGenreInBook2() {
        boolean books = bookGenreDao.duplicateGenreInBook(1,5);
        assertEquals(false,books);

    }
    @Test
    void duplicateGenreInBook3() {
        boolean books = bookGenreDao.duplicateGenreInBook(50,1);
        assertEquals(false,books);

    }
    @Test
    void duplicateGenreInBook4() {
        boolean books = bookGenreDao.duplicateGenreInBook(1,-1);
        assertEquals(false,books);

    }
}