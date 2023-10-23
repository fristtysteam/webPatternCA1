import business.User;
import dao.UserDao;
import org.mindrot.jbcrypt.BCrypt;

public class Main {
    public static void main(String[] args) {
        UserDao userDao = new UserDao("testLibrary");
        User user = userDao.getUserByID(2);
        System.out.println(user);
    }
}