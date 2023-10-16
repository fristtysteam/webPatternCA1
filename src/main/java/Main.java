import business.Book;
import dao.UserBookDao;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        UserBookDao userBookDao = new UserBookDao("library");
        List<Book> books;

        books = userBookDao.getAllBooksByUserID(1);

        System.out.println(books);

        System.out.println("start");
    }
}