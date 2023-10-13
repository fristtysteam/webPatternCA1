package dao;

import business.Book;

import java.sql.SQLException;
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

            return books;
        }
        catch(SQLException se){
            System.out.println(se.getMessage());
            System.out.println("something went wrong");
        }
        finally {
            freeConnection();
        }

        return null;
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

            return books;
        }
        catch(SQLException se){
            System.out.println(se.getMessage());
            System.out.println("something went wrong");
        }
        finally {
            freeConnection();
        }

        return null;
    }

    @Override
    public int borrowBook(int userID, int bookID) {
        BookDao bookDao = new BookDao("library");
        Book book = bookDao.getBookByID(bookID);

        if(book.getQuantity() - 1 < 1){
            return 0;
        }

        LocalDateTime borrowDate = LocalDateTime.now();
        LocalDateTime dueDate = LocalDateTime.now().plusWeeks(2);

        return 0;
    }

    @Override
    public int returnBook(int userID, int bookID) {
        return 0;
    }

    @Override
    public boolean checkForDuplicateBorrow(int userID, int bookID) {
        BookDao bookDao = new BookDao("library");
        int count = 0;

        try{
            String query = "SELECT count(*) FROM userbooks WHERE userID = ? AND bookID = ?";
            con = getConnection();
            ps = con.prepareStatement(query);
            ps.setInt(1, userID);
            rs = ps.executeQuery();

            if(rs.next()){
                count = rs.getInt(1);
                if(count > 1){
                    return true;
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

        return false;
    }

    @Override
    public int deleteUserBookByUserID(int userID) {
        return 0;
    }
}
