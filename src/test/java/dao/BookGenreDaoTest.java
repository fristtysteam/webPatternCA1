package dao;

import business.Book;
import business.Genre;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Marco
 */
class BookGenreDaoTest {
    private final BookGenreDao bookGenreDao = new BookGenreDao("testLibrary");
    /**
     * Test case for retrieving genres by a valid book ID.
     * It asserts that the correct genres are returned for the given book ID.
     */
    @Test
    void getGenreByBookID() {

      List<Genre> genres = bookGenreDao.getGenreByBookID(1);
        assertEquals(1,genres.get(0).getGenreID());
        assertEquals(2,genres.get(1).getGenreID());


    }
    /**
     * Test case for retrieving genres by an invalid (negative) book ID .
     */
    @Test
    void getGenreByBookID2() {

        List<Genre> genres = bookGenreDao.getGenreByBookID(-1);

        assertTrue(genres.isEmpty());


    }
    /**
     * Test case for retrieving books by their genre.
     * It asserts that the correct books are returned for the given genre name.
     */
    @Test
    void getBooksByGenre() {
        List<Book> books = bookGenreDao.getBooksByGenre("comedy");
assertEquals(1,books.get(0).getBookID());
assertEquals(2,books.get(1).getBookID());
assertEquals(4,books.get(2).getBookID());


        /**
         * Test case for retrieving books by their genre two.
         * It asserts that the correct books are returned for the given genre name.
         */
    }
    @Test
    void getBooksByGenre2() {
        List<Book> books = bookGenreDao.getBooksByGenre("jerry");
      assertTrue(books.isEmpty());



    }

    /**
     * Test case for adding genre to a book.
     * It asserts that the correct genres are added to the books given book ID.
     */
    @Test
    void addGenreToBook() {
int books = bookGenreDao.addGenreToBook(5,1);
        List<Genre> booksList = bookGenreDao.getGenreByBookID(5);
        bookGenreDao.deleteGenreToBook(5,1);
        assertEquals(1,books);
        assertEquals(1,booksList.get(1).getGenreID());



    }
    /**
     * Test case for adding genre to a book 2.
     * It asserts that the correct genres are added to the books given book ID.
     * Validates the wrong bookID.
     */
    @Test
    void addGenreToBook2(){
        int books = bookGenreDao.addGenreToBook(50,1);
        assertEquals(0,books);

    }
    /**
     * Test case for adding genre to a book 3.
     * It asserts that the correct genres are added to the books given book ID.
     * Validates the wrong genreID
     */
    @Test
    void addGenreToBook3(){
        int books = bookGenreDao.addGenreToBook(1,50);
        assertEquals(0,books);

    }
    /**
     * Test case for deleting genre to book.
     * It asserts that the correct genres are deleted to the books given book ID and genre ID.
     */
    @Test
    void deleteGenreToBook() {
 int books = bookGenreDao.deleteGenreToBook(5,2);
 bookGenreDao.addGenreToBook(5, 2);
 assertEquals(1,books);



    }
    /**
     * Test case for deleting genre to book 2.
     * Validates the wrong bookID.
     */
    @Test
    void deleteGenreToBook2() {
        int books = bookGenreDao.deleteGenreToBook(50,2);

        assertEquals(0,books);



    }
    /**
     * Test case for deleting genre to book 3.
     * Asserts equals if genre id doesnt exist .
     */
    @Test
    void deleteGenreToBook3() {
        int books = bookGenreDao.deleteGenreToBook(5,50);

        assertEquals(0,books);

    }



    /**
     * Test case finding duplicated genre in books.
     * Asserts equals if genre id is duplicate.
     */
    @Test
    void duplicateGenreInBook() {
boolean books = bookGenreDao.duplicateGenreInBook(1,1);
assertEquals(true,books);

    }
    /**
     * Test case finding duplicated genre in books 2.
     * Asserts equals if genre isn't duplicate .
     */
    @Test
    void duplicateGenreInBook2() {
        boolean books = bookGenreDao.duplicateGenreInBook(1,5);
        assertEquals(false,books);

    }
    /**
     * Test case finding duplicated genre in books 3.
     * This test has invalid bookID.
     * Asserts equals if genre isn't duplicate .
     */
    @Test
    void duplicateGenreInBook3() {
        boolean books = bookGenreDao.duplicateGenreInBook(50,1);
        assertEquals(false,books);

    }
    /**
     * Test case finding duplicated genre in books 4.
     * This test has invalid genreID.
     * Asserts equals if genre isn't duplicate .
     */
    @Test
    void duplicateGenreInBook4() {
        boolean books = bookGenreDao.duplicateGenreInBook(1,-1);
        assertEquals(false,books);

    }
}