package dao;

import business.Book;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class UserBookDao extends Dao implements UserBookDaoInterface{
    public UserBookDao(String dbName) {
        super(dbName);
    }

    @Override
    public List<Book> getAllBooksByUserID(int userID) {
        List<Book> books = new ArrayList<>();
        BookDao bookDao = new BookDao("library");

        try{
            String query = "SELECT * FROM userbooks WHERE userID = ?";
            con = getConnection();
            ps = con.prepareStatement(query);
            ps.setInt(1, userID);
            rs = ps.executeQuery();

            while(rs.next()){
                books.add(bookDao.getBookByID(rs.getInt("bookID")));
            }
        }
        catch(SQLException se){
            System.out.println(se.getMessage());
            System.out.println("something went wrong");
        }
        finally {
            freeConnection();
        }

        return books;
    }

    @Override
    public List<Book> getAllCurrentBooksByUserID(int userID) {
        List<Book> books = new ArrayList<>();
        BookDao bookDao = new BookDao("library");

        try{
            String query = "SELECT * FROM userbooks WHERE userID = ? AND borrowDate < dueDate";
            con = getConnection();
            ps = con.prepareStatement(query);
            ps.setInt(1, userID);
            rs = ps.executeQuery();

            while(rs.next()){
                books.add(bookDao.getBookByID(rs.getInt("bookID")));
            }
        }
        catch(SQLException se){
            System.out.println(se.getMessage());
            System.out.println("something went wrong");
        }
        finally {
            freeConnection();
        }

        return books;
    }

    @Override
    public int borrowBook(int userID, int bookID) {
        BookDao bookDao = new BookDao("library");
        Book book = bookDao.getBookByID(bookID);
        int rowsAffected = 0;

        //fail fast methods
        if(book.getQuantity() - 1 < 1 || checkForDuplicateBorrow(userID, bookID)){
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
            bookDao.updateBookQuantity(bookID, false);
        }
        catch(SQLException se){
            System.out.println(se.getMessage());
            System.out.println("something went wrong");
        }
        finally {
            freeConnection();
        }


        return rowsAffected;
    }

    @Override
    public int returnBook(int userID, int bookID) {
        BookDao bookDao = new BookDao("library");
        String query = "UPDATE userbooks SET returnedDate = ? WHERE userID = ? AND bookID = ?";
        int rowsAffected = 0;

        try{
            con = getConnection();
            ps = con.prepareStatement(query);
            ps.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
            ps.setInt(2, userID);
            ps.setInt(3, bookID);

            rowsAffected = ps.executeUpdate();
            bookDao.updateBookQuantity(bookID, true);
        }
        catch(SQLException se){
            System.out.println(se.getMessage());
            System.out.println("something went wrong");
        }
        finally {
            freeConnection();
        }

        return rowsAffected;
    }

    @Override
    public boolean checkForDuplicateBorrow(int userID, int bookID) {
        BookDao bookDao = new BookDao("library");
        int count;
        boolean flag = false;

        try{
            String query = "SELECT count(*) FROM userbooks WHERE userID = ? AND bookID = ?";
            con = getConnection();
            ps = con.prepareStatement(query);
            ps.setInt(1, userID);
            rs = ps.executeQuery();

            if(rs.next()){
                count = rs.getInt(1);
                if(count > 1){
                    flag = true;
                }
            }

        }
        catch(SQLException se){
            System.out.println(se.getMessage());
            System.out.println("something went wrong");
        }
        finally {
            freeConnection();
        }

        return flag;
    }

    @Override
    public int deleteUserBookByUserID(int userID) {
        return 0;
    }

    @Override
    public boolean checkIfLate(int bookID) {
        return false;
    }
}
