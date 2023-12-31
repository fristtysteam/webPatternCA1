package dao;

import business.UserBook;

import java.util.List;

/**
 * @author playerzer0-ui
 */
public interface UserBookDaoInterface {

    /**
     * get all the books borrowed by user (user history)
     * @param userID the user
     * @return lists of books
     */
    List<UserBook> getAllBooksByUserID(int userID);

    /**
     * get all the current books borrowed by user
     * @param userID the user
     * @return lists of books
     */
    List<UserBook> getAllCurrentBooksByUserID(int userID);

    /**
     * borrow a book, dueDate within next 2 weeks, quantity of book will be decreased
     * if quantity not enough, fail
     * @param userID the userID
     * @param bookID the bookID
     * @return 1 if success, 0 if fail
     */
    int borrowBook(int userID, int bookID);

    /**
     * return a book, quantity restored
     * @param userID the userID
     * @param bookID the bookID
     * @return 1 if success, 0 if fail
     */
    int returnBook(int userID, int bookID);

    /**
     * check for a duplicate borrow, you can only borrow one book at a time
     * @param userID the userID
     * @param bookID the bookID
     * @return true if there are duplicates, false otherwise
     */
    boolean checkForDuplicateBorrow(int userID, int bookID);

    /**
     * deletes all records by the userID
     * @param userID the userID
     * @param bookID the bookID
     * @return rows affected
     */
    int deleteUserBookByUserIDAndBookID(int userID, int bookID);

    /**
     * check if the book is returned late or not
     * @param userID the userID
     * @param bookID the bookID
     */
    void checkIfLate(int userID, int bookID);
}
