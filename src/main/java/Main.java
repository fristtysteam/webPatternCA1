import business.User;
import com.mysql.cj.protocol.a.BlobValueEncoder;
import dao.*;
import org.mindrot.jbcrypt.BCrypt;

import java.util.Scanner;

public class Main {

    private static final UserDao userDao = new UserDao("library");
    private static final BookDao bookDao = new BookDao("library");
    private static final UserBookDao userBookDao = new UserBookDao("library");
    private static final GenreDao genreDao = new GenreDao("library");
//    private static final BookGenreDao bookGenreDao = new BookGenreDao("library");

    public static void main(String[] args) {
        boolean logged = false;
        boolean session = false;
        Scanner sc = new Scanner(System.in);
        int choice = -1;

        while(!logged && !session){
            System.out.println("select an option (use number): ");
            System.out.println("1. login");
            System.out.println("2. register");
            System.out.println("3. exit");

            choice = sc.nextInt();
            sc.next();

            switch (choice){
                case 1:
                    System.out.println("enter email: ");
                    System.out.println("enter password: ");
                    break;
            }

            logged = true;

        }


    }
}