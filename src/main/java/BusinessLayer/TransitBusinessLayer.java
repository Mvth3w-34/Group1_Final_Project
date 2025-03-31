/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BusinessLayer;

import DataAccessLayer.VehicleData.*;
//import DataAccessLayer.LoginData.*;
import DataAccessLayer.OperatorData.*;
import TransferObjects.*;
//import TransferObjects.*;
import java.sql.*;

/**
 *
 * @author johnt
 */
public class TransitBusinessLayer {
    private final VehicleDAO vehicleDao;
    private final OperatorDao operatorDao;
    
    public TransitBusinessLayer() throws SQLException {
        vehicleDao = new VehicleDAOImpl();
        operatorDao = new OperatorDaoImpl();
    }
    /**
     * Verifies if the credentials entered exists in the DB system
     * @param userInput
     * @param passInput
     * @return
     * @throws SQLException 
     */
    public OperatorDTO validateCredentials(String userInput, String passInput) throws SQLException {
        // To be modified to select credentials being referenced in operators table
        for (int i = 0; i < operatorDao.getAllOperators().size(); i++) {
            if (userInput.equals(operatorDao.getAllOperators().get(i).getLogin().getUsername()) && 
                    passInput.equals(operatorDao.getAllOperators().get(i).getLogin().getPassword())) {
                return operatorDao.getAllOperators().get(i);
            }
        }
        operatorDao.closeConnection();
        return null;
    }
    public void registerVehicle(String vType, 
            String vin, String fuelType, float fuelRate, int maxPass) throws SQLException {
        VehicleBuilder builder = new VehicleBuilder();
        try {
            vehicleDao.registerVehicle(builder
                    .setVehicleType(VehicleDTO.VehicleType.valueOf(vType))
                    .setVehicleNum(vin)
                    .setFuelType(fuelType)
                    .setConsumptionRate(fuelRate)
                    .setMaxPassenger(maxPass)
                    .setRoute(null)
                    .registerVehicle()
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
//        VehicleDTO vehicle = new ;
    }
}
