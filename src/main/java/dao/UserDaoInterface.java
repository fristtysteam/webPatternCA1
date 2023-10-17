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
    public User registerUser(String username, String email, String password, String address, String phone) throws NoSuchAlgorithmException;

    /**
     * login the user
     * @param email the email
     * @param password the password
     * @return the user
     */
    public User loginUser(String email, String password);


    /**
     * get the user by ID
     * @param userID the userID
     * @return the user
     */
    public User getUserByID(int userID);

    /**
     * delete the user by ID
     * @param userID the userID
     * @return rows affected
     */
    public int deleteUserByID(int userID);

}
