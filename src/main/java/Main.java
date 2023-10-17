
import business.User;
import dao.UserDao;

public class Main {
    public static void main(String[] args) {

        UserDao userDao = new UserDao("library");
        User u =userDao.registerUser("admin", "admin@gmail.com",
                "rippleMMW1$", "address", "0231030213");

        System.out.println(u);
        User a = userDao.loginUser("admin@gmail.com", "rippleMMW1$");

        System.out.println(a);
    }
}