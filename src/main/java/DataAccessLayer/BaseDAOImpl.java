/* filename: BaseDAOImpl.java
 * date: Apr. 3rd, 2025
 * author: Stephanie Prystupa-Maule
 * course: CST8288 O.O.P. with Design Patterns - Lab Section 023 
 * professor: Samira Ouaaz
 * coursework: Final Project - Public Transit Management System
 */
package DataAccessLayer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Base Data Access Object class that provides common functionality for all DAO implementations.
 * Handles database connection management, resource cleanup, and standardized error handling.
 * 
 * @author Stephanie Prystupa-Maule
 * @version 1.0
 * @since 04/03/2025
 */
public abstract class BaseDAOImpl {
    
    private static final Logger logger = Logger.getLogger(BaseDAOImpl.class.getName());
    
    /**
     * Gets a database connection from the DataSource singleton.
     * 
     * @return Connection object representing a database connection
     * @throws SQLException if a database access error occurs
     */
    protected Connection getConnection() throws SQLException {
        return TransitDataSource.getInstance().getConnection();
    }
    
    /**
     * Safely closes a ResultSet without throwing exceptions.
     * Any exceptions are logged instead.
     * 
     * @param rs The ResultSet to close
     */
    protected void closeResultSet(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException ex) {
                logger.log(Level.WARNING, "Error closing ResultSet", ex);
            }
        }
    }
    
    /**
     * Safely closes a PreparedStatement without throwing exceptions.
     * Any exceptions are logged instead.
     * 
     * @param pstmt The PreparedStatement to close
     */
    protected void closePreparedStatement(PreparedStatement pstmt) {
        if (pstmt != null) {
            try {
                pstmt.close();
            } catch (SQLException ex) {
                logger.log(Level.WARNING, "Error closing PreparedStatement", ex);
            }
        }
    }
    
    /**
     * Safely closes a Connection without throwing exceptions.
     * Any exceptions are logged instead.
     * 
     * @param conn The Connection to close
     */
    protected void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException ex) {
                logger.log(Level.WARNING, "Error closing Connection", ex);
            }
        }
    }
    
    /**
     * Safely closes all database resources (ResultSet, PreparedStatement, Connection).
     * Any exceptions are logged instead of being thrown.
     * 
     * @param rs The ResultSet to close
     * @param pstmt The PreparedStatement to close
     * @param conn The Connection to close
     */
    protected void closeResources(ResultSet rs, PreparedStatement pstmt, Connection conn) {
        closeResultSet(rs);
        closePreparedStatement(pstmt);
        closeConnection(conn);
    }
    
    /**
     * Handles SQL exceptions by logging the details and optionally rethrowing.
     * 
     * @param ex The SQLException that occurred
     * @param operation Description of the operation that failed
     * @param rethrow Whether to rethrow the exception after logging
     * @throws SQLException if rethrow is true
     */
    protected void handleSQLException(SQLException ex, String operation, boolean rethrow) throws SQLException {
        logger.log(Level.SEVERE, "SQL error during " + operation + ": " + ex.getMessage(), ex);
        
        if (rethrow) {
            throw ex;
        }
    }
    
    /**
     * Executes a database operation with proper resource management.
     * This method handles the common pattern of:
     * 1. Get connection
     * 2. Create prepared statement
     * 3. Execute operation
     * 4. Handle any exceptions
     * 5. Close resources
     * 
     * @param <T> The return type of the database operation
     * @param operation The database operation to execute
     * @return The result of the database operation
     * @throws SQLException if a database access error occurs
     */
    protected <T> T executeOperation(DatabaseOperation<T> operation) throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = getConnection();
            return operation.execute(conn, pstmt, rs);
        } catch (SQLException ex) {
            handleSQLException(ex, operation.getDescription(), true);
            return null; // This line will never be reached if handleSQLException rethrows
        } finally {
            closeResources(rs, pstmt, conn);
        }
    }
    
    /**
     * Functional interface for database operations.
     * Implementations define the specific database operation to execute.
     * 
     * @param <T> The return type of the database operation
     */
    @FunctionalInterface
    protected interface DatabaseOperation<T> {
        /**
         * Executes a database operation.
         * 
         * @param conn The database connection
         * @param pstmt The prepared statement (may be null initially)
         * @param rs The result set (may be null initially)
         * @return The result of the database operation
         * @throws SQLException if a database access error occurs
         */
        T execute(Connection conn, PreparedStatement pstmt, ResultSet rs) throws SQLException;
        
        /**
         * Gets a description of the database operation.
         * Used for logging purposes.
         * 
         * @return A string describing the operation
         */
        default String getDescription() {
            return "database operation";
        }
    }
}
