/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DataAccessLayer;
import TransferObjects.LoginDTO;
import java.sql.*;
import java.io.*;
import java.util.Properties;

/**
 * Establishes a single connection to the database used for the Transit app
 * @author John Tieu
 */
public class TransitDataSource {
    private static Connection connection = null;
//    private static LoginDTO login;
    private static TransitDataSource ds = null;
    
    private TransitDataSource() {
//        this.login = login;
    }
    /**
     * Opens a new login instance
     * @return
     */
    public static synchronized TransitDataSource getDataInstance() {
        if (ds == null) {
//            ds = new TransitDataSource(login);
            ds = new TransitDataSource();
        }
        return ds;
    }
    /**
     * Opens a connection if there is no instance
     * @return The connection to the DB
     * @throws SQLException 
     */
    public synchronized Connection getConnection() throws SQLException {
        Properties prop = new Properties();
        String url, dbUser, dbPass;
        // Store the database property information to log into the database server
        try (InputStream in = getClass().getClassLoader().getResourceAsStream("database.properties")){
            prop.load(in);
            in.close();
        } catch (NullPointerException | IOException e) {
            e.printStackTrace();
        }
        url = prop.getProperty("jdbc.url");
        dbUser = prop.getProperty("jdbc.username");
        dbPass = prop.getProperty("jdbc.password");

        // Create a connection to the database with the saved login information
        try {
            if (connection == null || connection.isClosed()) {
//                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(url, dbUser, dbPass);
//                connection = DriverManager.getConnection(url, login.getUsername(), login.getPassword());
            }
        } catch (SQLException e) {
            throw e;
        }
        return connection;
    }
    /**
     * Manually closes the DB connection
     */
    public synchronized void closeConnection() {
        try {
            if (connection != null || !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            
        }
    }
}
