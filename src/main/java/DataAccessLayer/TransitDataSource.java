
/* filename: DataSource.java
 * date: Mar. 25th, 2025
 * updated by: Stephanie Prystupa-Maule
 * original authors: Stan Pieda (2015)
 * course: CST8288 O.O.P. with Design Patterns - Lab Section 023 
 * professor: Samira Ouaaz
 * coursework: Lab Assignment 2 - Java Servlet
 */

package DataAccessLayer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import TransferObjects.CredentialsDTO;

/**
 * Singleton class managing database connections.
 * Provides a single point of access to database connections using user-provided credentials.
 * 
 * @author Stephanie Prystupa-Maule
 * @version 1.1
 * @since 03/25/2025
 */
public class TransitDataSource {

    private static final TransitDataSource instance = new TransitDataSource();
    private static Connection connection = null;
    private String connectionString;
    private String username;
    private String password;
    private boolean credentialsSet = false;

    /**
     * Private constructor to prevent instantiation.
     */
    private TransitDataSource() {
        this.connectionString = "jdbc:mysql://localhost:3306/transit";
    }

    /**
     * Gets the singleton instance of DataSource.
     * 
     * @return The singleton DataSource instance
     */
    public static TransitDataSource getInstance() {
        return instance;
    }
    
    /**
     * Sets credentials and connection string for database connection.
     * 
     * @param credentials CredentialsDTO containing username and password
     * @param connectionString JDBC connection URL
     */
    public void setCredentials(CredentialsDTO credentials, String connectionString) {
        if (credentials != null && connectionString != null && !connectionString.isEmpty()) {
            this.username = credentials.getUsername();
            this.password = credentials.getPassword();
            this.connectionString = connectionString;
            this.credentialsSet = true;
            
            // Close any existing connection when credentials change
            closeConnection();
        }
    }
    
    /**
     * Sets credentials for database connection using the existing connection string.
     * 
     * @param credentials CredentialsDTO containing username and password
     */
    public void setCredentials(CredentialsDTO credentials) {
        if (credentials != null) {
            this.username = credentials.getUsername();
            this.password = credentials.getPassword();
            this.credentialsSet = true;
            
            // Close any existing connection when credentials change
            closeConnection();
        }
    }
    
    /**
     * Updates the JDBC connection string.
     * 
     * @param connectionString the new JDBC connection URL
     */
    public void setConnectionString(String connectionString) {
        if (connectionString != null && !connectionString.isEmpty()) {
            this.connectionString = connectionString;
            
            // Close any existing connection when connection string changes
            closeConnection();
        }
    }

    /**
     * Gets a database connection, creating it if necessary.
     * 
     * @return Connection object representing the database connection
     * @throws SQLException if a database access error occurs
     * @throws IllegalStateException if credentials have not been set
     */
    public synchronized Connection getConnection() throws SQLException {
        if (!credentialsSet) {
            throw new IllegalStateException("Database credentials have not been set");
        }
        
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(connectionString, username, password);
        }
        return connection;
    }

    /**
     * Closes the current database connection.
     */
    public synchronized void closeConnection() {
        if (connection != null) {
            try {
                if (!connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                connection = null;
            }
        }
    }
    
    /**
     * Checks if credentials have been set.
     * 
     * @return true if credentials are set, false otherwise
     */
    public boolean areCredentialsSet() {
        return credentialsSet;
    }
    
    /**
     * Clears the current credentials.
     * This will require new credentials to be set before connections can be created.
     */
    public void clearCredentials() {
        closeConnection();
        this.username = null;
        this.password = null;
        this.credentialsSet = false;
    }
}

//
///* filename: DataSource.java
// * date: Apr. 3rd, 2025
// * author: Stephanie Prystupa-Maule
// * course: CST8288 O.O.P. with Design Patterns - Lab Section 023 
// * professor: Samira Ouaaz
// * coursework: Final Group Assignment
// */
//
//package dataaccesslayer;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.SQLException;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.UUID;
//import transferobjects.CredentialsDTO;
//
///**
// * Singleton class managing database connections.
// * Provides multi-user connection support, allowing different users
// * to have their own database connections with different credentials.
// * 
// * @author Stephanie Prystupa-Maule
// * @version 2.0
// * @since 04/03/2025
// */
//public class DataSource {
//
//    private static final DataSource instance = new DataSource();
//    private static final Map<String, Connection> connections = new HashMap<>();
//    private static final Map<String, CredentialsDTO> credentialsMap = new HashMap<>();
//    private String defaultConnectionString;
//
//    /**
//     * Private constructor to prevent instantiation.
//     */
//    private DataSource() {
//        this.defaultConnectionString = "jdbc:mysql://localhost:3306/transit";
//    }
//
//    /**
//     * Gets the singleton instance of DataSource.
//     * 
//     * @return The singleton DataSource instance
//     */
//    public static DataSource getInstance() {
//        return instance;
//    }
//    
//    /**
//     * Sets credentials and connection string for a database connection.
//     * Generates and returns a unique connection ID for future reference.
//     * 
//     * @param credentials CredentialsDTO containing username and password
//     * @param connectionString JDBC connection URL
//     * @return String connectionId to identify this specific connection
//     */
//    public String setCredentials(CredentialsDTO credentials, String connectionString) {
//        if (credentials != null && connectionString != null && !connectionString.isEmpty()) {
//            String connectionId = generateConnectionId(credentials);
//            credentialsMap.put(connectionId, credentials);
//            
//            // Close any existing connection for these credentials
//            closeConnection(connectionId);
//            
//            return connectionId;
//        }
//        return null;
//    }
//    
//    /**
//     * Sets credentials for a database connection using the default connection string.
//     * Generates and returns a unique connection ID for future reference.
//     * 
//     * @param credentials CredentialsDTO containing username and password
//     * @return String connectionId to identify this specific connection
//     */
//    public String setCredentials(CredentialsDTO credentials) {
//        if (credentials != null) {
//            String connectionId = generateConnectionId(credentials);
//            credentialsMap.put(connectionId, credentials);
//            
//            // Close any existing connection for these credentials
//            closeConnection(connectionId);
//            
//            return connectionId;
//        }
//        return null;
//    }
//    
//    /**
//     * Updates the default JDBC connection string.
//     * 
//     * @param connectionString the new JDBC connection URL
//     */
//    public void setDefaultConnectionString(String connectionString) {
//        if (connectionString != null && !connectionString.isEmpty()) {
//            this.defaultConnectionString = connectionString;
//        }
//    }
//
//    /**
//     * Gets a database connection for the specified connection ID.
//     * Creates a new connection if one doesn't exist or is closed.
//     * 
//     * @param connectionId The unique identifier for the connection
//     * @return Connection object representing the database connection
//     * @throws SQLException if a database access error occurs
//     * @throws IllegalStateException if connectionId is invalid or credentials not found
//     */
//    public synchronized Connection getConnection(String connectionId) throws SQLException {
//        if (connectionId == null || !credentialsMap.containsKey(connectionId)) {
//            throw new IllegalStateException("Invalid connection ID or credentials not set");
//        }
//        
//        Connection connection = connections.get(connectionId);
//        if (connection == null || connection.isClosed()) {
//            CredentialsDTO credentials = credentialsMap.get(connectionId);
//            connection = DriverManager.getConnection(
//                    defaultConnectionString, 
//                    credentials.getUsername(), 
//                    credentials.getPassword());
//            connections.put(connectionId, connection);
//        }
//        return connection;
//    }
//
//    /**
//     * Closes the database connection for the specified connection ID.
//     * 
//     * @param connectionId The unique identifier for the connection to close
//     */
//    public synchronized void closeConnection(String connectionId) {
//        if (connectionId != null && connections.containsKey(connectionId)) {
//            Connection connection = connections.get(connectionId);
//            if (connection != null) {
//                try {
//                    if (!connection.isClosed()) {
//                        connection.close();
//                    }
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                } finally {
//                    connections.remove(connectionId);
//                }
//            }
//        }
//    }
//    
//    /**
//     * Closes all open database connections.
//     */
//    public synchronized void closeAllConnections() {
//        for (String connectionId : connections.keySet()) {
//            closeConnection(connectionId);
//        }
//        connections.clear();
//    }
//    
//    /**
//     * Removes credentials and closes connection for the specified connection ID.
//     * 
//     * @param connectionId The unique identifier for the connection
//     */
//    public void removeCredentials(String connectionId) {
//        closeConnection(connectionId);
//        credentialsMap.remove(connectionId);
//    }
//    
//    /**
//     * Removes all credentials and closes all connections.
//     */
//    public void clearAllCredentials() {
//        closeAllConnections();
//        credentialsMap.clear();
//    }
//    
//    /**
//     * Checks if credentials exist for the specified connection ID.
//     * 
//     * @param connectionId The unique identifier for the connection
//     * @return true if credentials exist, false otherwise
//     */
//    public boolean hasCredentials(String connectionId) {
//        return connectionId != null && credentialsMap.containsKey(connectionId);
//    }
//    
//    /**
//     * Generates a unique connection ID based on credentials.
//     * 
//     * @param credentials The credentials to create an ID for
//     * @return A unique string identifier
//     */
//    private String generateConnectionId(CredentialsDTO credentials) {
//        return credentials.getUsername() + "-" + UUID.randomUUID().toString().substring(0, 8);
//    }
//    
//    /**
//     * Gets the number of active connections.
//     * 
//     * @return The count of active connections
//     */
//    public int getActiveConnectionCount() {
//        return connections.size();
//    }
//}
