package dao;

import business.User;
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

        assertEquals(user.getUserID(), act.getUserID());
        assertEquals(user.getUserName(), act.getUserName());
        assertEquals(user.getEmail(), act.getEmail());
        assertEquals(user.getPassword(), act.getPassword());
        assertEquals(user.getAddress(), act.getAddress());
        assertEquals(user.getPhone(), act.getPhone());
        assertEquals(user.getFees(), act.getFees());
        assertEquals(user.getSecret(), act.getSecret());
        assertEquals(user.getSalt(), act.getSalt());
        assertEquals(user.getUserType(), act.getUserType());

        userDao.deleteUserByID(3);
        userDao.updateIncrement("users", 3);
    }

    /**
     * registerUser, fail because there is a same username
     */
    @Test
    void registerUser_fail_usernameDuplicate() {
        User act = userDao.registerUser("jerry", "aabbcc@gmail.com",
                "wefwef", "address", "sad");

        assertNull(act);
        userDao.updateIncrement("users", 3);
    }

    /**
     * registerUser, fail because there is a same email
     */
    @Test
    void registerUser_fail_emailDuplicate() {
        User act = userDao.registerUser("jerryfcsd", "jerry@gmail.com",
                "wefwef", "address", "sad");

        assertNull(act);
        userDao.updateIncrement("users", 3);
    }

    /**
     * loginUser, normal way
     */
    @Test
    void loginUser_normal() {
        User user = new User(1, "jerry", "jerry@gmail.com", "rippleMMW1$",
                "address", "231030213", 30,
                "OYYdUyE9lGfw3Gb8/m59KALhcTL2scX/", "99G8d2K9vXql2YyHPw++fzn+UgPbS+vu/kyzvBfXzSk=", 0);
        User act = userDao.loginUser("jerry@gmail.com", "rippleMMW1$");

        assertEquals(user.getUserID(), act.getUserID());
        assertEquals(user.getUserName(), act.getUserName());
        assertEquals(user.getEmail(), act.getEmail());
        assertEquals(user.getPassword(), act.getPassword());
        assertEquals(user.getAddress(), act.getAddress());
        assertEquals(user.getPhone(), act.getPhone());
        assertEquals(user.getFees(), act.getFees());
        assertEquals(user.getSecret(), act.getSecret());
        assertEquals(user.getSalt(), act.getSalt());
        assertEquals(user.getUserType(), act.getUserType());
    }

    /**
     * loginUser, fail because the password is wrong
     */
    @Test
    void loginUser_fail_password() {
        User act = userDao.loginUser("jerry@gmail.com", "aadfsa");

        assertNull(act);
    }

    /**
     * loginUser, fail because the email is wrong
     */
    @Test
    void loginUser_fail_email() {
        User act = userDao.loginUser("jerryfwsef@gmail.com", "aadfsa");

        assertNull(act);
    }

    /**
     * getUserByID, normal way
     */
    @Test
    void getUserByID_normal() {
        User user = new User(1, "jerry", "jerry@gmail.com", "rippleMMW1$",
                "address", "231030213", 30,
                "OYYdUyE9lGfw3Gb8/m59KALhcTL2scX/", "99G8d2K9vXql2YyHPw++fzn+UgPbS+vu/kyzvBfXzSk=", 0);
        User act = userDao.getUserByID(1);

        assertEquals(user.getUserID(), act.getUserID());
        assertEquals(user.getUserName(), act.getUserName());
        assertEquals(user.getEmail(), act.getEmail());
        assertEquals(user.getPassword(), act.getPassword());
        assertEquals(user.getAddress(), act.getAddress());
        assertEquals(user.getPhone(), act.getPhone());
        assertEquals(user.getFees(), act.getFees());
        assertEquals(user.getSecret(), act.getSecret());
        assertEquals(user.getSalt(), act.getSalt());
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

        assertEquals(exp, act);
        userDao.updateIncrement("users", 3);

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
}