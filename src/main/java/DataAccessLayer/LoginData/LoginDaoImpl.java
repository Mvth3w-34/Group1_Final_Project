/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DataAccessLayer.LoginData;

import DataAccessLayer.TransitDataSource;
import TransferObjects.LoginDTO;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author johnt
 */
public class LoginDaoImpl implements LoginDao{

    private final TransitDataSource instance;
    
    public LoginDaoImpl() throws SQLException {
        instance = TransitDataSource.getDataInstance();
        // Test to see if login is successful upon login
//        instance.getConnection();
    }
    @Override
    public List<LoginDTO> getAllCredentials() throws SQLException {
        List<LoginDTO> loginList = new ArrayList<>();
        ResultSet set;
        LoginDTO login;
        String query = "SELECT * FROM Credentials";
        try (PreparedStatement statement = instance.getConnection().prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY))
        {
            set = statement.executeQuery();
            if (set.next()) {
                // Reset the pointer location to prevent row skips before printing table
                set.beforeFirst();
                while (set.next()) {
                    // Add the vehicle from the DB to the list to validate the operator
                    login = new LoginDTO();
                    login.setUsername(set.getString("UserName"));
                    login.setPassword(set.getString("Password"));
                    loginList.add(login);
                }
            }
        } catch (SQLException e) {
            throw e;
        }
        return loginList;
    }
    
    @Override
    public void closeConnection() {
        instance.closeConnection();
    }
    
}
