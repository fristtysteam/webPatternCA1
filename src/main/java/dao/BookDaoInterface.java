package dao;

import java.sql.SQLException;
import java.util.List;
import business.Book;
    public interface BookDaoInterface {

        /**
         * @return a list of all books in the database
         * @throws SQLException if there is an error in the database
         */
        List<Book> getAllBooks() throws SQLException;


        /**
         * @param bookID the ID of the book to get
         * @return the book with the ID enter, or null if the book does not exist
         * @throws SQLException if there is an error in the database
         */
        Book getBookByID(int bookID) throws SQLException;

        /**
         * @param bookID the ID of the book to update
         * @param increase true to increase the quantity, false to decrease the quantity
         * @return the number of rows affected by the update
         * @throws SQLException if there is an error in the database
         */
        int updateBookQuantity(int bookID, boolean increase) throws SQLException;
    }

