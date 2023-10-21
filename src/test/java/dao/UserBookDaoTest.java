package dao;

import business.Book;
import business.User;
import business.UserBook;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserBookDaoTest {

    private UserBookDao userBookDao = new UserBookDao("testLibrary");
    private BookDao bookDao = new BookDao("testLibrary");
    private UserDao userDao = new UserDao("testLibrary");
    private User user = userDao.getUserByID(1);

    /**
     * getAllBooksByUserID, normal way
     */
    @Test
    void getAllBooksByUserID_normal() {
        //need to simulate the book being returned
        userBookDao.borrowBook(1, 1);

        //now let's check, there should be one there
        List<UserBook> expUserBooks = new ArrayList<>();
        expUserBooks.add(new UserBook(
                user,
                bookDao.getBookByID(1),
                LocalDateTime.now(),
                LocalDateTime.now().plusWeeks(2),
                null
        ));
        List<UserBook> actUserBooks = userBookDao.getAllBooksByUserID(1);

        //delete it
        userBookDao.deleteUserBookByUserIDAndBookID(1,1);

        assertEquals(expUserBooks, actUserBooks);

    }

    /**
     * getAllBooksByUserID, failed way because no userID
     */
    @Test
    void getAllBooksByUserID_failed_duplicateID() {
        List<UserBook> expBooks = new ArrayList<>();
        List<UserBook> actBooks = userBookDao.getAllBooksByUserID(100);

        assertEquals(expBooks, actBooks);
    }

    /**
     * getAllCurrentBooksByUserID, normal way
     */
    @Test
    void getAllCurrentBooksByUserID_normal() {

    }

    /**
     * getAllCurrentBooksByUserID, failed way because there are no books borrowed
     */
    @Test
    void getAllCurrentBooksByUserID_failed() {

    }

    /**
     * borrowBook, normal way
     */
    @Test
    void borrowBook_normal() {

    }

    @Test
    void returnBook() {
    }

    @Test
    void checkForDuplicateBorrow() {
    }

    @Test
    void deleteUserBookByUserIDAndBookID() {
    }

    @Test
    void checkIfLate() {
    }
}