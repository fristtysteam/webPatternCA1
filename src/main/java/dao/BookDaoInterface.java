package dao;

import java.util.List;
import business.Book;

public interface BookDaoInterface {

    /**
     * get all the books
     * @return a list of books
     */
    public List<Book> getAllBooks();

    /**
     * get the book by ID
     * @param bookID the bookID
     * @return the book
     */
    public Book getBookByID(int bookID);

}
