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
    public void closeConnection() {
        instance.closeConnection();
    }
    
}
