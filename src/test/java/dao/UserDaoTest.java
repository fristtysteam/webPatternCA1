//package dao;
//
//import business.User;
//import org.junit.jupiter.api.Test;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class UserDaoTest {
//    private final UserDao userDao = new UserDao("testLibrary");
//    /**
//     * registerUser, normal way
//     */
//    @Test
//    void registerUser_normal() {
//        User exp = new User(4, "aabbcc", "aabbcc@gmail.com",
//                "wefwef", "address", "sad", 0, 0);
//        User act = userDao.registerUser("aabbcc", "aabbcc@gmail.com",
//                "wefwef", "address", "sad");
//
//        assertEquals(exp, act);
//        userDao.deleteUserByID(4);
//        userDao.updateIncrement("users", 4);
//    }
//
//    /**
//     * registerUser, fail because there is a same username
//     */
//    @Test
//    void registerUser_fail_usernameDuplicate() {
//        User act = userDao.registerUser("jerry", "aabbcc@gmail.com",
//                "wefwef", "address", "sad");
//
//        assertNull(act);
//        userDao.updateIncrement("users", 4);
//    }
//
//    /**
//     * registerUser, fail because there is a same email
//     */
//    @Test
//    void registerUser_fail_emailDuplicate() {
//        User act = userDao.registerUser("jerryfcsd", "jerry@gmail.com",
//                "wefwef", "address", "sad");
//
//        assertNull(act);
//        userDao.updateIncrement("users", 4);
//    }
//
//    /**
//     * loginUser, normal way
//     */
//    @Test
//    void loginUser_normal() {
//        User exp = new User(1, "jerry",
//                "jerry@gmail.com", "aaa",
//                null, null, 0, 0);
//        User act = userDao.loginUser("jerry@gmail.com", "aaa");
//
//        assertEquals(exp, act);
//    }
//
//    /**
//     * loginUser, fail because the password is wrong
//     */
//    @Test
//    void loginUser_fail_password() {
//        User act = userDao.loginUser("jerry@gmail.com", "aadfsa");
//
//        assertNull(act);
//    }
//
//    /**
//     * loginUser, fail because the email is wrong
//     */
//    @Test
//    void loginUser_fail_email() {
//        User act = userDao.loginUser("jerryfwsef@gmail.com", "aadfsa");
//
//        assertNull(act);
//    }
//
//    /**
//     * getUserByID, normal way
//     */
//    @Test
//    void getUserByID_normal() {
//        User exp = new User(1, "jerry",
//                "jerry@gmail.com", "aaa",
//                null, null, 0, 0);
//        User act = userDao.getUserByID(1);
//
//        assertEquals(exp, act);
//    }
//
//    /**
//     * getUserByID, fail because couldn't get ID
//     */
//    @Test
//    void getUserByID_fail_noID() {
//        User act = userDao.getUserByID(2131);
//
//        assertNull(act);
//    }
//
//    /**
//     * deleteUserByID, normal way
//     */
//    @Test
//    void deleteUserByID_normal() {
//        int exp = 1;
//        userDao.registerUser("aabbcc", "aabbcc@gmail.com",
//                "wefwef", "address", "sad");
//        int act = userDao.deleteUserByID(4);
//
//        assertEquals(exp, act);
//    }
//
//    /**
//     * deleteUserByID, fail because no ID found
//     */
//    @Test
//    void deleteUserByID_fail_noID() {
//        int exp = 0;
//        int act = userDao.deleteUserByID(100);
//
//        assertEquals(exp, act);
//    }
//}