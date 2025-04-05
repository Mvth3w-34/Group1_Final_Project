/* filename: OperatorDAOImpl.java
 * date: Apr. 5th, 2025
 * authors: Stephanie Prystupa-Maule. John Tieu
 * course: CST8288 O.O.P. with Design Patterns - Lab Section 023 
 * professor: Samira Ouaaz
 * coursework: Final Project - Public Transit Management System
 */
package DataAccessLayer.OperatorData;

import TransferObjects.OperatorDTO;
import DataAccessLayer.BaseDAOImpl;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of the OperatorDAO interface that provides database operations
 * for managing operator data in the OPERATORS table.
 * Extends BaseDAOImpl to leverage common database functionality.
 * 
 * @author johnt
 * @author Stephanie Prystupa-Maule
 * @version 2.0
 * @since 04/05/2025
 * @see BaseDAOImpl
 * @see OperatorDAO
 */
public class OperatorDAOImpl extends BaseDAOImpl implements OperatorDAO {
    
    /**
     * Retrieves an operator from the database by Operator ID.
     * Executes a SELECT query on the OPERATORS table and returns the record that
     * matches the Operator ID.
     * 
     * @param operatorID The ID of the operator to retrieve
     * @return OperatorDTO object containing the operator data for the matching record
     *          or null if no matching record is found
     * @throws SQLException 
     */
    @Override
    public OperatorDTO getOperatorByID(Integer operatorID) throws SQLException {
        return executeOperation(new DatabaseOperation<OperatorDTO>() {
            @Override
            public OperatorDTO execute(Connection conn, PreparedStatement pstmt, ResultSet rs) throws SQLException {
                OperatorDTO operator = null;
                pstmt = conn.prepareStatement(
                        "SELECT operator_id, operator_name, credential_id, operator_user, email "
                        + "FROM OPERATORS WHERE operator_id = ?");
                pstmt.setInt(1, operatorID);
                rs = pstmt.executeQuery();

                if (rs.next()) {
                    operator = extractOperatorFromResultSet(rs);
                }
                return operator;
            }
            
            @Override
            public String getDescription() {
                return "getOperatorByID";
            }
        });
    }
    
    /**
     * Retrieves all operators from the database.
     * Executes a SELECT query on the OPERATORS table and maps the result set
     * to a list of OperatorDTO objects.
     * 
     * @return ArrayList of OperatorDTO objects containing all the operator data.
     *          If no matching records are found, returns an empty list
     * @throws SQLException 
     */
    @Override
    public List<OperatorDTO> getAllOperators() throws SQLException {
        return executeOperation(new DatabaseOperation<List<OperatorDTO>>() {
            @Override
            public List<OperatorDTO> execute(Connection conn, PreparedStatement pstmt, ResultSet rs) throws SQLException {
                List<OperatorDTO> operators = new ArrayList<>();
            
                pstmt = conn.prepareStatement(
                    "SELECT operator_id, operator_name, credential_id, operator_user, email "
                    + "FROM OPERATORS ORDER BY operator_name");
                
                rs = pstmt.executeQuery();
            
                while (rs.next()) {
                    OperatorDTO operator = extractOperatorFromResultSet(rs);
                    operators.add(operator);
                }
            
                return operators;
            }
        
            @Override
            public String getDescription() {
                return "getAllOperators";
            }
        });
    }
    
    /**
     * Adds a new operator record to the database.
     * Executes an INSERT query on the OPERATORS table with the data from the OperatorDTO.
     * 
     * @param operator The OperatorDTO to be added
     * @throws SQLException 
     */
    @Override
    public void addOperator(OperatorDTO operator) throws SQLException {
        executeOperation(new DatabaseOperation<Void>() {
            @Override
            public Void execute(Connection conn, PreparedStatement pstmt, ResultSet rs) throws SQLException {
                pstmt = conn.prepareStatement(
                    "INSERT INTO OPERATORS (operator_name, credential_id, operator_user, email) "
                    + "VALUES (?, ?, ?, ?)");
                
                pstmt.setString(1, operator.getOperatorName());
                pstmt.setInt(2, operator.getCredentialId());
                pstmt.setString(3, operator.getUserType().toString());
                pstmt.setString(4, operator.getEmail());
                
                pstmt.executeUpdate();
                return null;
            }
            
            @Override
            public String getDescription() {
                return "addOperator";
            }
        });
    }
    
    /**
     * Updates an operator record in the database.
     * Executes an UPDATE query on the OPERATORS table with the data from the OperatorDTO.
     * 
     * @param operator The OperatorDTO to be updated
     * @throws SQLException 
     */
    @Override
    public void updateOperator(OperatorDTO operator) throws SQLException {
        executeOperation(new DatabaseOperation<Void>() {
            @Override
            public Void execute(Connection conn, PreparedStatement pstmt, ResultSet rs) throws SQLException {
                pstmt = conn.prepareStatement(
                    "UPDATE OPERATORS SET operator_name = ?, credential_id = ?, "
                    + "operator_user = ?, email = ? WHERE operator_id = ?");
                
                pstmt.setString(1, operator.getOperatorName());
                pstmt.setInt(2, operator.getCredentialId());
                pstmt.setString(3, operator.getUserType().toString());
                pstmt.setString(4, operator.getEmail());
                pstmt.setInt(5, operator.getOperatorId());
                
                pstmt.executeUpdate();
                return null;
            }
            
            @Override
            public String getDescription() {
                return "updateOperator";
            }
        });
    }
    
    /**
     * Deletes an operator record from the database.
     * Executes a DELETE query on the OPERATORS table for the operator with the specified ID.
     * 
     * @param operator The OperatorDTO to be deleted
     * @throws SQLException 
     */
    @Override
    public void deleteOperator(OperatorDTO operator) throws SQLException {
        executeOperation(new DatabaseOperation<Void>() {
            @Override
            public Void execute(Connection conn, PreparedStatement pstmt, ResultSet rs) throws SQLException {
                pstmt = conn.prepareStatement(
                    "DELETE FROM OPERATORS WHERE operator_id = ?");
                
                pstmt.setInt(1, operator.getOperatorId());
                
                pstmt.executeUpdate();
                return null;
            }
            
            @Override
            public String getDescription() {
                return "deleteOperator";
            }
        });
    }
    
    /**
     * Helper method to extract an OperatorDTO from a ResultSet row.
     * Creates and populates an OperatorDTO object based on the current row of the ResultSet.
     * 
     * @param rs The ResultSet containing operator data
     * @return A populated OperatorDTO object
     * @throws SQLException If there is an error accessing the ResultSet
     */
    private OperatorDTO extractOperatorFromResultSet(ResultSet rs) throws SQLException {
        // Map the operator_user string from database to UserType enum
        String userTypeString = rs.getString("operator_user");
        OperatorDTO.UserType userType;
        
        // Convert the string value to the enum value
        try {
            userType = OperatorDTO.UserType.valueOf(userTypeString);
        } catch (IllegalArgumentException e) {
            // Default to OPERATOR if the value doesn't match the enum
            userType = OperatorDTO.UserType.OPERATOR;
        }
        
        return OperatorDTO.builder()
            .setOperatorId(rs.getInt("operator_id"))
            .setOperatorName(rs.getString("operator_name"))
            .setCredentialId(rs.getInt("credential_id"))
            .setUserType(userType)
            .setEmail(rs.getString("email"))
            .build();
    }
}