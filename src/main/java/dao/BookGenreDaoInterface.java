package dao;

import java.util.List;

import business.Book;
import business.Genre;

public interface BookGenreDaoInterface {

    /**
     * get genre by bookID
     * @param bookID the bookID
     * @return the genre
     */
    public Genre getGenreByBookID(int bookID);

    /**
     * get the books by genre
     * @param genre the genre name
     * @return a list of books
     */
    public List<Book> getBooksByGenre(String genre);

    /**
     * add a genre to a book
     * @param bookID the bookID
     * @param genreID the genreID
     * @return rows affected
     */
    public int addGenreToBook(int bookID, int genreID);

}
