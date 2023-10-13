package dao;

import java.sql.SQLException;
import java.sql.Statement;
import business.User;

public class UserDao extends Dao implements UserDaoInterface{
    public UserDao(String dbName) {
        super(dbName);
    }

    @Override
    public User registerUser(String username, String email, String password, String address, String phone) {
        int newId;
        try{
            String query = "INSERT INTO users(userName, email, password, address, phone) VALUES (?, ?, ?, ?, ?)";
            con = getConnection();
            ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, username);
            ps.setString(2, password);
            ps.setString(3, email);
            ps.setString(4, address);
            ps.setString(5, phone);

            ps.executeUpdate();
            rs = ps.getGeneratedKeys();
            // If there was a result, i.e. if the entry was inserted successfully
            if(rs.next())
            {
                // Get the id value that was generated by MySQL when the entry was inserted
                newId = rs.getInt(1);
                return new User(newId, username, email, password, address, phone);
            }

        }
        catch(SQLException se){
            System.out.println(se.getMessage());
            System.out.println("something went wrong");
        }
        finally {
            freeConnection();
        }

        return null;
    }

    @Override
    public User loginUser(String email, String password) {
        int newId;
        String username;
        String pass;
        String address;
        String phone;
        try{
            String query = "SELECT * FROM users WHERE email = ? AND password = ?";
            con = getConnection();
            ps = con.prepareStatement(query);
            ps.setString(1, email);
            ps.setString(2, password);

            rs = ps.executeQuery();
            // If there was a result, i.e. if the entry was inserted successfully
            if(rs.next())
            {
                // Get the id value that was generated by MySQL when the entry was inserted
                newId = rs.getInt("userID");
                username = rs.getString("userName");
                email = rs.getString("email");
                pass = rs.getString("password");
                address = rs.getString("address");
                phone = rs.getString("phone");

                return new User(newId, username, email, pass, address, phone);
            }

        }
        catch(SQLException se){
            System.out.println(se.getMessage());
            System.out.println("something went wrong");
        }
        finally {
            freeConnection();
        }

        return null;
    }

    @Override
    public User getUserByID(int userID) {
        int newId;
        String username;
        String email;
        String pass;
        String address;
        String phone;

        try{
            String query = "SELECT * FROM users WHERE userID = ?";
            con = getConnection();
            ps = con.prepareStatement(query);
            ps.setInt(1, userID);
            rs = ps.executeQuery();
            // If there was a result, i.e. if the entry was inserted successfully
            if(rs.next())
            {
                newId = rs.getInt("userID");
                username = rs.getString("userName");
                email = rs.getString("email");
                pass = rs.getString("password");
                address = rs.getString("address");
                phone = rs.getString("phone");

                return new User(newId, username, email, pass, address, phone);
            }

        }
        catch(SQLException se){
            System.out.println(se.getMessage());
            System.out.println("something went wrong");
        }
        finally {
            freeConnection();
        }

        return null;
    }

    @Override
    public int deleteUserByID(int userID) {
        int rowsAffected = 0;
        try{
            String query = "SELECT * FROM users WHERE userID = ?";
            con = getConnection();
            ps = con.prepareStatement(query);
            ps.setInt(1, userID);

            rowsAffected = ps.executeUpdate();

        }
        catch(SQLException se){
            System.out.println(se.getMessage());
            System.out.println("something went wrong");
        }
        finally {
            freeConnectionUpdate();
        }

        return rowsAffected;
    }
}