package dao;

import java.sql.*;

public class Dao {
    protected Connection con = null;
    protected PreparedStatement ps = null;
    protected ResultSet rs = null;
    protected String dbName;

    public Dao(String dbName){
        this.dbName = dbName;
    }

    public Dao(){}

    /**
     * get the connection to the database
     * @return connection
     */
    public Connection getConnection() {

        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/" + dbName;
        String username = "root";
        String password = "";

        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException ex1) {
            System.out.println("Failed to find driver class " + ex1.getMessage());
            System.exit(1);
        } catch (SQLException ex2) {
            System.out.println("Connection failed " + ex2.getMessage());
            System.exit(2);
        }
        return con;
    }

    /**
     * free the connection
     */
    public void freeConnection() {
        try {
            if (rs != null){
                rs.close();
            }
            if (ps != null){
                ps.close();
            }
            if (con != null) {
                con.close();
            }
        } catch (SQLException e) {
            System.out.println("Failed to free connection: " + e.getMessage());
            System.exit(1);
        }
    }

    /**
     * free connection, for insert, update, delete
     */
    public void freeConnectionUpdate() {
        try {
            if (ps != null){
                ps.close();
            }
            if (con != null) {
                con.close();
            }
        } catch (SQLException e) {
            System.out.println("Failed to free connection: " + e.getMessage());
            System.exit(1);
        }
    }

    /**
     * updates the increment in the table
     * @param tableName the table name
     * @param num the number increment to set to
     */
    public void updateIncrement(String tableName, int num) {
        try {
            con = this.getConnection();

            String query = "ALTER TABLE ? AUTO_INCREMENT = ?";
            ps = con.prepareStatement(query);
            ps.setString(1, tableName);
            ps.setInt(2, num);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("\tA problem occurred during the updateIncrement method:");
            System.err.println("\t" + e.getMessage());
        } finally {
            freeConnectionUpdate();
        }
    }

    /**
     * DELETES EVERYTHING IN THE TABLE, USE WISELY
     * @param tableName the table name
     * @return rowsAffected, if it is 0 means there is truly none in it
     */
    public int clearDB(String tableName){
        int rowsAffected = 0;

        try{
            con = this.getConnection();

            String query = "DELETE FROM ?";
            ps = con.prepareStatement(query);
            ps.setString(1, tableName);
            rowsAffected = ps.executeUpdate();
        }
        catch (SQLException e)
        {
            System.err.println("\tA problem occurred during the clearStockDB method:");
            System.err.println("\t"+e.getMessage());
        }
        finally
        {
            freeConnectionUpdate();
        }

        return rowsAffected;
    }
}

