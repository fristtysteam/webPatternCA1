package dao;

import business.Book;

import java.util.List;

public class BookDao extends Dao implements BookDaoInterface{
    @Override
    public List<Book> getAllBooks() {
        System.out.println("what do I do");

        return null;
    }

    @Override
    public Book getBookByID(int bookID) {
        return null;
    }
}
