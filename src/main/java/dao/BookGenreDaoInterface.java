package dao;

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

}
