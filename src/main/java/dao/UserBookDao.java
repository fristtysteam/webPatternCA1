package dao;

import business.Book;
import business.UserBook;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

/**
 * @author playerzer0-ui
 */
public class UserBookDao extends Dao implements UserBookDaoInterface{

    private final BookDao bookDao;
    private final UserDao userDao;
    public UserBookDao(String dbName) {
        super(dbName);
        bookDao = new BookDao(dbName);
        userDao = new UserDao(dbName);
    }

    @Override
    public List<UserBook> getAllBooksByUserID(int userID) {
        List<UserBook> userBooks = new ArrayList<>();
        LocalDateTime returned;

        try{
            String query = "SELECT * FROM userbooks WHERE userID = ?";
            con = getConnection();
            ps = con.prepareStatement(query);
            ps.setInt(1, userID);
            rs = ps.executeQuery();

            while(rs.next()){
                if(rs.getTimestamp("returnedDate") == null){
                    returned = null;
                }
                else{
                    returned = rs.getTimestamp("returnedDate").toLocalDateTime();
                }

                userBooks.add(
                        new UserBook(
                                userDao.getUserByID(userID),
                                bookDao.getBookByID(rs.getInt("bookID")),
                                rs.getTimestamp("borrowDate").toLocalDateTime(),
                                rs.getTimestamp("dueDate").toLocalDateTime(),
                                returned
                        )
                );
            }
        }
        catch(SQLException se){
            System.out.println(se.getMessage());
            System.out.println("something went wrong with getAllBooksByUserID");
        }
        finally {
            freeConnection();
        }

        return userBooks;
    }

    @Override
    public List<UserBook> getAllCurrentBooksByUserID(int userID) {
        List<UserBook> userBooks = new ArrayList<>();

        try{
            String query = "SELECT * FROM userbooks WHERE userID = ? AND borrowDate < dueDate AND returnedDate is null";
            con = getConnection();
            ps = con.prepareStatement(query);
            ps.setInt(1, userID);
            rs = ps.executeQuery();

            while(rs.next()){
                userBooks.add(
                        new UserBook(
                                userDao.getUserByID(userID),
                                bookDao.getBookByID(rs.getInt("bookID")),
                                rs.getTimestamp("borrowDate").toLocalDateTime(),
                                rs.getTimestamp("dueDate").toLocalDateTime(),
                                null
                        )
                );
            }
        }
        catch(SQLException se){
            System.out.println(se.getMessage());
            System.out.println("something went wrong with getAllCurrentBooksByUserID");
        }
        finally {
            freeConnection();
        }

        return userBooks;
    }

    @Override
    public int borrowBook(int userID, int bookID) {
        Book book = bookDao.getBookByID(bookID);
        int rowsAffected = 0;

        //fail fast methods
        if(book.getQuantity() - 1 < 0 || checkForDuplicateBorrow(userID, bookID)){
            return rowsAffected;
        }

        LocalDateTime borrowDate = LocalDateTime.now();
        LocalDateTime dueDate = LocalDateTime.now().plusWeeks(2);
        String query = "INSERT INTO userbooks (userID, bookID, borrowDate, dueDate) VALUES (?, ?, ?, ?)";

        try{
            con = getConnection();
            ps = con.prepareStatement(query);
            ps.setInt(1, userID);
            ps.setInt(2, bookID);
            ps.setTimestamp(3, Timestamp.valueOf(borrowDate));
            ps.setTimestamp(4, Timestamp.valueOf(dueDate));

            rowsAffected = ps.executeUpdate();
            bookDao.updateBookQuantity(bookID, -1);
        }
        catch(SQLException se){
            System.out.println(se.getMessage());
            System.out.println("something went wrong in borrowBook");
        }
        finally {
            freeConnectionUpdate();
        }


        return rowsAffected;
    }

    @Override
    public int returnBook(int userID, int bookID) {
        String query = "UPDATE userbooks SET returnedDate = ? WHERE userID = ? AND bookID = ?";
        int rowsAffected = 0;

        try{
            con = getConnection();
            ps = con.prepareStatement(query);
            ps.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
            ps.setInt(2, userID);
            ps.setInt(3, bookID);

            rowsAffected = ps.executeUpdate();
            bookDao.updateBookQuantity(bookID, 1);
            //checkIfLate(1, 1);
        }
        catch(SQLException se){
            System.out.println(se.getMessage());
            System.out.println("something went wrong with returnBook");
        }
        finally {
            freeConnectionUpdate();
        }

        return rowsAffected;
    }

    @Override
    public boolean checkForDuplicateBorrow(int userID, int bookID) {
        int count;
        boolean flag = false;

        try{
            String query = "SELECT count(*) FROM userbooks WHERE userID = ? AND bookID = ?";
            con = getConnection();
            ps = con.prepareStatement(query);
            ps.setInt(1, userID);
            ps.setInt(2, bookID);
            rs = ps.executeQuery();

            if(rs.next()){
                count = rs.getInt(1);
                if(count >= 1){
                    flag = true;
                }
            }

        }
        catch(SQLException se){
            System.out.println(se.getMessage());
            System.out.println("something went wrong with checkForDuplicateBorrow");
        }
        finally {
            freeConnection();
        }

        return flag;
    }

    @Override
    public int deleteUserBookByUserIDAndBookID(int userID, int bookID) {
        int rowsAffected = 0;

        try{
            String query = "DELETE FROM userbooks WHERE userID = ? AND bookID = ?";
            con = getConnection();
            ps = con.prepareStatement(query);
            ps.setInt(1, userID);
            ps.setInt(2, bookID);
            rowsAffected = ps.executeUpdate();


        }
        catch(SQLException se){
            System.out.println(se.getMessage());
            System.out.println("something went wrong with deleteUserBookByUserIDAndBookID");
        }
        finally {
            freeConnectionUpdate();
        }

        return rowsAffected;
    }

    @Override
    public void checkIfLate(int userID, int bookID) {
        try{
            String query = "SELECT * FROM userbooks WHERE userID = ? AND bookID = ?";
            con = getConnection();
            ps = con.prepareStatement(query);
            ps.setInt(1, userID);
            ps.setInt(2, bookID);
            rs = ps.executeQuery();

            if(rs.next()){
                Timestamp due = rs.getTimestamp("dueDate");
                Timestamp returned = rs.getTimestamp("returnedDate");

                LocalDateTime dueDate = due.toLocalDateTime();
                LocalDateTime returnedDate = returned.toLocalDateTime();

                if(returnedDate.isAfter(dueDate)){
                    int days = (int)ChronoUnit.DAYS.between(dueDate, returnedDate);
                    System.out.println("late return for " + days + " days, late fee added: $" + (10 * days));
                    userDao.updateFee(userID, 10 * days);
                }
            }

        }
        catch(SQLException se){
            System.out.println(se.getMessage());
            System.out.println("something went wrong with checkIfLate");
        }
        finally {
            freeConnection();
        }
    }
}
