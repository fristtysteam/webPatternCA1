package dao;

import business.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserDaoTest {

    /**
     * registerUser, normal way
     */
    @Test
    void registerUser_normal() {
        UserDao userDao = new UserDao("testLibrary");
        User exp = new User(4, "aabbcc", "aabbcc@gmail.com",
                "wefwef", "address", "sad", 0, 0);
        User act = userDao.registerUser("aabbcc", "aabbcc@gmail.com",
                "wefwef", "address", "sad");

        assertEquals(exp, act);

        userDao.deleteUserByID(4);

        userDao.updateIncrement("users", 4);
    }

    /**
     * registerUser, fail because there is a same username
     */
    @Test
    void registerUser_fail() {
        UserDao userDao = new UserDao("testLibrary");
    }

    @Test
    void loginUser() {
    }

    @Test
    void getUserByID() {
    }

    @Test
    void deleteUserByID() {
    }
}