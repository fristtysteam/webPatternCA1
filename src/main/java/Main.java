import business.Book;
import business.User;
import dao.UserDao;
import util.AESCrypto;

import javax.crypto.BadPaddingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws NoSuchAlgorithmException {

        UserDao userDao = new UserDao("library");
        User u =userDao.registerUser("admin", "admin@gmail.com",
                "rippleMMW1$", "address", "0231030213");

        System.out.println(u);
        User a = userDao.loginUser("admin@gmail.com", "rippleMMW1$");

        System.out.println(a);
    }
}