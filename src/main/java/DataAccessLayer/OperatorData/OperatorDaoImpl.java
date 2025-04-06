/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DataAccessLayer.OperatorData;

import DataAccessLayer.TransitDataSource;
import TransferObjects.LoginDTO;
import TransferObjects.OperatorDTO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author johnt
 */
public class OperatorDaoImpl implements OperatorDao {

    private final TransitDataSource instance;
    
    public OperatorDaoImpl() throws SQLException {
        instance = TransitDataSource.getDataInstance();
        // Test to see if login is successful upon login
//        instance.getConnection();
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
                        operator.setOperatorID(set.getInt("OPERATOR_ID"));
                        operator.setName(set.getString("OPERATOR_NAME"));
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
    public void registerOperator(OperatorDTO operator) throws SQLException {
        int id = registerCredentials(operator); // Adds new credentials to DB first
        if (id == 0) {
            throw new SQLException();
        }
        String insertOperatorQuery = "INSERT INTO OPERATORS (OPERATOR_NAME, CREDENTIAL_ID, OPERATOR_USER, "
                + "EMAIL)"
                + " VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = instance.getConnection().prepareStatement(insertOperatorQuery, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            statement.setString(1, operator.getName());
            statement.setInt(2, id);
            statement.setString(3, operator.getOperatorType().name());
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

    @Override
    public void closeConnection() {
        instance.closeConnection();
    }
    
}
