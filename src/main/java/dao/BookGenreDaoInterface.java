package dao;

import business.Book;
import business.Genre;

import java.util.List;

public interface BookGenreDaoInterface {

    /**
     * get genre by bookID
     * @param bookID the bookID
     * @return the genre list
     */
    public List<Genre> getGenreByBookID(int bookID);

    /**
     * get the books by genre
     * @param genreName the genre name
     * @return a list of books
     */
    public List<Book> getBooksByGenre(String genreName);

    /**
     * add a genre to a book
     * @param bookID bookID
     * @param genreID genreID
     * @return rows affected, 1 means added
     */
    int addGenreToBook(int bookID, int genreID);

    /**
     * delete a genre to a book
     * @param bookID bookID
     * @param genreID genreID
     * @return rows affected, 1 means added
     */
    int deleteGenreToBook(int bookID, int genreID);

    /**
     * check if a duplicate genre is there
     * @param bookID bookID
     * @param genreID genreID
     * @return true or false, if found or not
     */
    boolean duplicateGenreInBook(int bookID, int genreID);
}






