package dao;

import business.Book;

import java.util.List;

public class UserBookDao extends Dao implements UserBookDaoInterface{
    @Override
    public List<Book> getAllBooksByUserID(int userID) {
        return null;
    }

    @Override
    public List<Book> getAllCurrentBooksByUserID(int userID) {
        return null;
    }

    @Override
    public int borrowBook(int userID, int bookID) {
        return 0;
    }

    @Override
    public int returnBook(int userID, int bookID) {
        return 0;
    }

    @Override
    public Book checkForDuplicateBorrow(int userID, int bookID) {
        return null;
    }

    @Override
    public int deleteUserBookByUserID(int userID) {
        return 0;
    }
}
