package dao;

import business.User;
import exceptions.DuplicateEmailException;
import exceptions.DuplicateUsernameException;
import exceptions.InvalidEmailException;
import exceptions.InvalidPasswordException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserDaoTest {
    private final UserDao userDao = new UserDao("testLibrary");
    /**
     * registerUser, normal way
     */
    @Test
    void registerUser_normal() {
        User act = userDao.registerUser("aabbcc", "aabbcc@gmail.com",
                "wefwef", "address", "sad");

        User user = userDao.getUserByID(3);

        userDao.deleteUserByID(3);
        userDao.updateIncrement("users", 3);
        assertEquals(user, act);

    }

    /**
     * registerUser, fail because there is a same username
     */
    @Test
    void registerUser_fail_usernameDuplicate() {
        assertThrows(DuplicateUsernameException.class,
                ()-> userDao.registerUser("jerry", "aabbcc@gmail.com",
                "wefwef", "address", "sad"));

        userDao.updateIncrement("users", 3);

    }

    /**
     * registerUser, fail because there is a same email
     */
    @Test
    void registerUser_fail_emailDuplicate() {
        assertThrows(DuplicateEmailException.class,
                ()->userDao.registerUser("jerryfcsd", "jerry@gmail.com",
                        "wefwef", "address", "sad"));

        userDao.updateIncrement("users", 3);
    }

    /**
     * loginUser, normal way
     */
    @Test
    void loginUser_normal() {
        User user = new User(1, "jerry", "jerry@gmail.com", "rippleMMW1$",
                "address", "231030213", 30, 0);
        User act = userDao.loginUser("jerry@gmail.com", "rippleMMW1$");

        assertEquals(user.getUserID(), act.getUserID());
        assertEquals(user.getUserName(), act.getUserName());
        assertEquals(user.getEmail(), act.getEmail());
        assertEquals(user.getPassword(), act.getPassword());
        assertEquals(user.getAddress(), act.getAddress());
        assertEquals(user.getPhone(), act.getPhone());
        assertEquals(user.getFees(), act.getFees());
        assertEquals(user.getUserType(), act.getUserType());
    }

    /**
     * loginUser, fail because the password is wrong
     */
    @Test
    void loginUser_fail_password() {
        assertThrows(InvalidPasswordException.class,
                ()->userDao.loginUser("jerry@gmail.com", "aadfsa"));
    }

    /**
     * loginUser, fail because the email is wrong
     */
    @Test
    void loginUser_fail_email() {
        assertThrows(InvalidEmailException.class,
                ()->userDao.loginUser("jerryfwsef@gmail.com", "aadfsa"));
    }

    /**
     * getUserByID, normal way
     */
    @Test
    void getUserByID_normal() {
        User user = new User(1, "jerry", "jerry@gmail.com", "rippleMMW1$",
                "address", "231030213", 30, 0);
        User act = userDao.getUserByID(1);

        assertEquals(user.getUserID(), act.getUserID());
        assertEquals(user.getUserName(), act.getUserName());
        assertEquals(user.getEmail(), act.getEmail());
        assertEquals(user.getAddress(), act.getAddress());
        assertEquals(user.getPhone(), act.getPhone());
        assertEquals(user.getFees(), act.getFees());
        assertEquals(user.getUserType(), act.getUserType());
    }

    /**
     * getUserByID, fail because couldn't get ID
     */
    @Test
    void getUserByID_fail_noID() {
        User act = userDao.getUserByID(2131);

        assertNull(act);
    }

    /**
     * deleteUserByID, normal way
     */
    @Test
    void deleteUserByID_normal() {
        int exp = 1;
        userDao.registerUser("aabbcc", "aabbcc@gmail.com",
                "wefwef", "address", "sad");
        int act = userDao.deleteUserByID(3);

        userDao.updateIncrement("users", 3);
        assertEquals(exp, act);

    }

    /**
     * deleteUserByID, fail because no ID found
     */
    @Test
    void deleteUserByID_fail_noID() {
        int exp = 0;
        int act = userDao.deleteUserByID(100);

        assertEquals(exp, act);
    }

    /**
     * updateFee, normal
     */
    @Test
    void updateFee_normal() {
        assertEquals(1, userDao.updateFee(1, 20));
        assertEquals(1, userDao.updateFee(1, -20));
    }

    /**
     * updateFee, but no valid ID
     */
    @Test
    void updateFee_no_valid_ID() {
        assertEquals(0, userDao.updateFee(100, 20));
    }

    /**
     * checkUsername, there is a username
     */
    @Test
    void checkUsername_thereIs(){
        assertTrue(userDao.checkUsername("jerry"));
    }

    /**
     * checkUsername, there is not a username
     */
    @Test
    void checkUsername_thereIs_not(){
        assertFalse(userDao.checkUsername("jerrgergrywg"));
    }

    /**
     * checkEmail, there is an email
     */
    @Test
    void checkEmail_thereIs(){
        assertTrue(userDao.checkEmail("jerry@gmail.com"));
    }

    /**
     * checkEmail, there is no email
     */
    @Test
    void checkEmail_thereIs_not(){
        assertTrue(userDao.checkEmail("jerry@gmail.com"));
    }



}