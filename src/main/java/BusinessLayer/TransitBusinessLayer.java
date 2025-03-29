/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BusinessLayer;

import DataAccessLayer.VehicleData.*;
import DataAccessLayer.LoginData.*;
import TransferObjects.*;
import java.sql.*;

/**
 *
 * @author johnt
 */
public class TransitBusinessLayer {
    private final VehicleDAO vehicleDao;
    private final LoginDao loginDao;
    
    public TransitBusinessLayer() throws SQLException {
        vehicleDao = new VehicleDAOImpl();
        loginDao = new LoginDaoImpl();
    }
    
    public boolean validateCredentials(String userInput, String passInput) throws SQLException {
        // To be modified to select credentials being referenced in operators table
        for (int i = 0; i < loginDao.getAllCredentials().size(); i++) {
            if (userInput.equals(loginDao.getAllCredentials().get(i).getUsername()) && 
                    passInput.equals(loginDao.getAllCredentials().get(i).getPassword())) {
                return true;
            }
        }
        loginDao.closeConnection();
        return false;
    }
}
