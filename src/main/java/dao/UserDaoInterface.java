package dao;

import business.User;

import java.security.NoSuchAlgorithmException;

public interface UserDaoInterface {

    /**
     * register a user to the database
     * @param username the username
     * @param email the email
     * @param password the password
     * @param address the address
     * @param phone the phone number
     * @return the user
     */
    User registerUser(String username, String email, String password, String address, String phone) throws NoSuchAlgorithmException;

    /**
     * login the user
     * @param email the email
     * @param password the password
     * @return the user
     */
    User loginUser(String email, String password);


    /**
     * get the user by ID
     * @param userID the userID
     * @return the user
     */
    User getUserByID(int userID);

    /**
     * delete the user by ID
     * @param userID the userID
     * @return rows affected
     */
    int deleteUserByID(int userID);

    /**
     * add or subtract fee to the user
     * @param userID the userID
     * @param fee fee to add or reduce (+/-)
     * @return number of rows affected
     */
    int updateFee(int userID, int fee);

    /**
     * check a username, this can determine whether it exists or not, could used to check
     * duplicates or a new unique name
     * @param username the username
     * @return true(more than one) or false(none)
     */
    boolean checkUsername(String username);

    /**
     * check email, to check for a duplicate or a new email
     * @param email the email
     * @return true(more than one) or false(none)
     */
    boolean checkEmail(String email);

}
