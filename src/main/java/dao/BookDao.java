package dao;

import business.Book;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookDao extends Dao implements BookDaoInterface{
    @Override
    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();
        try{
            String query = "SELECT * FROM books";
            con = getConnection();
            ps = con.prepareStatement(query);
            rs = ps.executeQuery();

            while(rs.next()){
                books.add(new Book(
                        rs.getInt("bookID"),
                        rs.getString("bookName"),
                        rs.getString("author"),
                        rs.getString("description"),
                        rs.getInt("quantity")
                ));
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
    public Book getBookByID(int bookID) {
        Book book = null;
        try{
            String query = "SELECT * FROM books WHERE bookID = ?";
            con = getConnection();
            ps = con.prepareStatement(query);
            ps.setInt(1, bookID);
            rs = ps.executeQuery();

            if(rs.next()){
                book = new Book(
                        rs.getInt("bookID"),
                        rs.getString("bookName"),
                        rs.getString("author"),
                        rs.getString("description"),
                        rs.getInt("quantity")
                );
            }

            return book;
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
}
