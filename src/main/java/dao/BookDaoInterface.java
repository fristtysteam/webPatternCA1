package dao;

import java.util.List;
import business.Book;

/**
 * @author CheePheng
 */
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

    /**
     * update the quantity in the book
     * @param bookID the bookID
     * @param increase enter interger to delete or update quantity
     * @return rows affected
     */
    public int updateBookQuantity(int bookID, int increase);

    /**
     * Add book to the library database.
     *
     * @param book the book to update.
     * @return The number of rows affected by the update.
     */
    public int addBook (Book book);

    /**
     * Add book to the library database.
     *
     * @param bookID the id of the book to delete.
     * @return The number of rows affected by the update.
     */
    public int deleteBook(int bookID);


}


