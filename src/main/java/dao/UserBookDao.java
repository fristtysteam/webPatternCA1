package dao;

import business.Book;

import java.sql.SQLException;
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
