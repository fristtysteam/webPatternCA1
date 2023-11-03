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

    public int addBook (Book book);

    public int deleteBook(int bookID);


}


