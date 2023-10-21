package dao;

import business.User;
import business.UserBook;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserBookDaoTest {

    private final UserBookDao userBookDao = new UserBookDao("testLibrary");
    private final BookDao bookDao = new BookDao("testLibrary");
    private final UserDao userDao = new UserDao("testLibrary");
    private final User user = userDao.getUserByID(1);

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

        //delete it and restore book quantity
        userBookDao.deleteUserBookByUserIDAndBookID(1,1);
        bookDao.updateBookQuantity(1, true);

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
     * getAllBooksByUserID, but user doesn't borrow anything
     */
    @Test
    void getAllBooksByUserID_noBooks() {
        List<UserBook> expBooks = new ArrayList<>();
        List<UserBook> actBooks = userBookDao.getAllBooksByUserID(1);

        assertEquals(expBooks, actBooks);
    }

    /**
     * getAllCurrentBooksByUserID, normal way
     */
    @Test
    void getAllCurrentBooksByUserID_normal() {
        //time to borrow 2 books and return one
        userBookDao.borrowBook(1, 1);
        userBookDao.borrowBook(1, 2);
        userBookDao.returnBook(1, 2);

        //now let's check, there should be one there because it is checking currently
        //borrowed books
        List<UserBook> expUserBooks = new ArrayList<>();
        expUserBooks.add(new UserBook(
                user,
                bookDao.getBookByID(2),
                LocalDateTime.now(),
                LocalDateTime.now().plusWeeks(2),
                null
        ));
        List<UserBook> actUserBooks = userBookDao.getAllCurrentBooksByUserID(1);

        //delete it and restore book quantity
        userBookDao.deleteUserBookByUserIDAndBookID(1,1);
        bookDao.updateBookQuantity(1, true);
        userBookDao.deleteUserBookByUserIDAndBookID(1,2);

        assertEquals(expUserBooks, actUserBooks);
    }

    /**
     * getAllCurrentBooksByUserID, failed way because there are no books borrowed
     */
    @Test
    void getAllCurrentBooksByUserID_no_books() {
        //simulate borrow, not returned
        userBookDao.borrowBook(1, 1);

        //checking
        List<UserBook> expBooks = new ArrayList<>();
        List<UserBook> actBooks = userBookDao.getAllCurrentBooksByUserID(1);

        //delete it and restore book quantity
        userBookDao.deleteUserBookByUserIDAndBookID(1,1);
        bookDao.updateBookQuantity(1, true);

        assertEquals(expBooks, actBooks);
    }

    /**
     * borrowBook, normal way
     */
    @Test
    void borrowBook_normal() {
        //need to simulate the book
        assertEquals(1, userBookDao.borrowBook(1, 1));

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

        //delete it and restore book quantity
        userBookDao.deleteUserBookByUserIDAndBookID(1,1);
        bookDao.updateBookQuantity(1, true);

        assertEquals(expUserBooks, actUserBooks);
        assertEquals(expUserBooks.get(0).getReturnedDate(), actUserBooks.get(0).getReturnedDate());
    }

    /**
     * borrowBook, failed because there is a duplicate borrow
     */
    @Test
    void borrowBook_failed_duplicateBorrow() {
        //need to simulate the book
        userBookDao.borrowBook(1, 1);
        userBookDao.borrowBook(1, 1);
        userBookDao.borrowBook(1, 1);

        //now let's check, there should be one there, the rest are copies
        List<UserBook> expUserBooks = new ArrayList<>();
        expUserBooks.add(new UserBook(
                user,
                bookDao.getBookByID(1),
                LocalDateTime.now(),
                LocalDateTime.now().plusWeeks(2),
                null
        ));
        List<UserBook> actUserBooks = userBookDao.getAllBooksByUserID(1);

        //delete it and restore book quantity
        userBookDao.deleteUserBookByUserIDAndBookID(1,1);
        bookDao.updateBookQuantity(1, true);

        assertEquals(expUserBooks, actUserBooks);
    }

    /**
     * return book, normal
     */
    @Test
    void returnBook_normal() {
        //time to borrow a book and return one
        userBookDao.borrowBook(1, 2);
        assertEquals(1, userBookDao.returnBook(1, 2));

        //check if it has been returned
        List<UserBook> expUserBooks = new ArrayList<>();
        expUserBooks.add(new UserBook(
                user,
                bookDao.getBookByID(2),
                LocalDateTime.now(),
                LocalDateTime.now().plusWeeks(2),
                LocalDateTime.now()
        ));
        List<UserBook> actUserBooks = userBookDao.getAllBooksByUserID(1);

        //delete it and restore book quantity
        userBookDao.deleteUserBookByUserIDAndBookID(1,2);

        assertEquals(expUserBooks, actUserBooks);

    }

    /**
     * deleteUserBookByUserIDAndBookID, normal way
     */
    @Test
    void deleteUserBookByUserIDAndBookID_normal() {
        userBookDao.borrowBook(1, 2);
        userBookDao.returnBook(1, 2);

        int exp = 1;
        int act = userBookDao.deleteUserBookByUserIDAndBookID(1, 2);

        assertEquals(exp, act);
    }

    /**
     * deleteUserBookByUserIDAndBookID, failed since there is none to delete
     */
    @Test
    void deleteUserBookByUserIDAndBookID_noDelete() {
        int exp = 0;
        int act = userBookDao.deleteUserBookByUserIDAndBookID(100, 100);

        assertEquals(exp, act);
    }

    /**
     * checkIfLate, normal way, which means there is someone late
     */
    @Test
    void checkIfLate_normal() {
        //there is a user returning late, assume he is
        userBookDao.checkIfLate(1, 4);

        //the fee should be updated from 30 to 60
        User user = userDao.getUserByID(1);
        int exp = 60;
        int act = user.getFees();

        //revert the fee
        userDao.updateFee(1, -30);

        assertEquals(exp, act);
    }
}