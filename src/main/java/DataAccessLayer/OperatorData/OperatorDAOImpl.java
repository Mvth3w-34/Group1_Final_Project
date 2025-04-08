/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DataAccessLayer.OperatorData;

import DataAccessLayer.BaseDAOImpl;
import DataAccessLayer.TransitDataSource;
import TransferObjects.LoginDTO;
import TransferObjects.OperatorDTO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 
 * @author John Tieu 
 * @author Stephanie Prystupa-Maule
 * @version 1.0
 * @since 04/05/2025
 */
 public class OperatorDAOImpl extends BaseDAOImpl implements OperatorDAO {
    
    
    private final TransitDataSource instance;
    
    public OperatorDAOImpl() throws SQLException {
        instance = TransitDataSource.getDataInstance();
        // Test to see if login is successful upon login
//        instance.getConnection();
    }
    
    
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

    @Override
    public List<OperatorDTO> getAllOperators() throws SQLException {
        List<OperatorDTO> operatorList = new ArrayList<>();
        ResultSet set;
        OperatorDTO operator;
        LoginDTO login;
        String joinQuery = "SELECT * FROM OPERATORS INNER JOIN CREDENTIALS ON OPERATORS.CREDENTIAL_ID = CREDENTIALS.CREDENTIAL_ID";
        try (PreparedStatement statement = instance.getConnection().prepareStatement(joinQuery, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY))
        {
            set = statement.executeQuery();
            if (set.next()) {
                // Reset the pointer location to prevent row skips before printing table
                set.beforeFirst();
                while (set.next()) {
                    try {
                        // Add the vehicle from the DB to the list to validate the operator
                        login = new LoginDTO();
                        login.setLoginID(set.getInt("CREDENTIAL_ID"));
                        login.setUsername(set.getString("USERNAME"));
                        login.setPassword(set.getString("PASSWORD"));

                        operator = new OperatorDTO();
                        operator.setOperatorId(set.getInt("OPERATOR_ID"));
                        operator.setOperatorName(set.getString("OPERATOR_NAME"));
                        operator.assignLogin(login);
                        operator.setUserType(OperatorDTO.UserType.valueOf(set.getString("OPERATOR_USER")));
                        operator.setEmail(set.getString("EMAIL"));
                        operatorList.add(operator);
                    } catch (IllegalArgumentException e) {
                        // Skip the operator with an invalid user type
                    }
                }
            }
        } catch (SQLException e) {
            throw e;
        }
        return operatorList;
    }
    
    @Override
    public void addOperator(OperatorDTO operator) throws SQLException {
        int id = registerCredentials(operator); // Adds new credentials to DB first
        if (id == 0) {
            throw new SQLException();
        }
        String insertOperatorQuery = "INSERT INTO OPERATORS (OPERATOR_NAME, CREDENTIAL_ID, OPERATOR_USER, "
                + "EMAIL)"
                + " VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = instance.getConnection().prepareStatement(insertOperatorQuery, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            statement.setString(1, operator.getOperatorName());
            statement.setInt(2, id);
            statement.setString(3, operator.getUserType().name());
            statement.setString(4, operator.getEmail());
            statement.executeUpdate();
        }
    }

    private int registerCredentials(OperatorDTO operator) throws SQLException {
        String insertLoginQuery = "INSERT INTO CREDENTIALS (USERNAME, PASSWORD) VALUES (?, ?)";
        int id;
        try (PreparedStatement statement = instance.getConnection().prepareStatement(insertLoginQuery, Statement.RETURN_GENERATED_KEYS)
        ) {
            statement.setString(1, operator.getLogin().getUsername());
            statement.setString(2, operator.getLogin().getPassword());
            statement.executeUpdate();
            try (ResultSet set = statement.getGeneratedKeys()) {
                if (set.next()) {
                    id = set.getInt(1);
                    return id;
                }
            }
            return 0;
        }   
    }
    
    /**
     * Updates an operator record in the database.
     * Executes an UPDATE query on the OPERATORS table with the data from the OperatorDTO.
     * 
     * @param operator The OperatorDTO to be updated
     * @throws SQLException 
     */
    @Override
    public void updateOperator(OperatorDTO operator) throws SQLException{
              
        String query = "UPDATE OPERATORS SET operator_name = ?, credential_id = ?, "
              + "operator_user = ?, email = ? WHERE operator_id = ?";

        try (PreparedStatement pstmt = instance.getConnection().prepareStatement(query)){

          pstmt.setString(1, operator.getOperatorName());
          pstmt.setInt(2, operator.getCredentialId());
          pstmt.setString(3, operator.getUserType().toString());
          pstmt.setString(4, operator.getEmail());
          pstmt.setInt(5, operator.getOperatorId());

          pstmt.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Deletes an operator record from the database.
     * Executes a DELETE query on the OPERATORS table for the operator with the specified ID.
     * 
     * @param operator The OperatorDTO to be deleted
     * @throws SQLException 
     */
    @Override
    public void deleteOperator(OperatorDTO operator) throws SQLException{ 
        
        String query = "DELETE FROM OPERATORS WHERE operator_id = ?";
        try (PreparedStatement pstmt = instance.getConnection().prepareStatement(query)){
                
            pstmt.setInt(1, operator.getOperatorId());
            pstmt.executeUpdate();
            
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    
    }
    
    /**
     * Helper method to extract an OperatorDTO from a ResultSet row.
     * Creates and populates an OperatorDTO object based on the current row of the ResultSet.
     * 
     * @param rs The ResultSet containing operator data
     * @return A populated OperatorDTO object
     * @throws SQLException If there is an error accessing the ResultSet
     */
    @Override
    public OperatorDTO extractOperatorFromResultSet(ResultSet rs) throws SQLException {
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

    @Override
    public void closeConnection() {
        instance.closeConnection();
    }

}

