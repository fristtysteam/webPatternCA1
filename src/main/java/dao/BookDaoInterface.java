package dao;

import java.util.List;

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
     * @param increase boolean, if true then quantity +1, otherwise -1
     * @return rows affected
     */
    public int updateBookQuantity(int bookID, boolean increase);

}
