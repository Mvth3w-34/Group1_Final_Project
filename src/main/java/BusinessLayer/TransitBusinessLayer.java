/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BusinessLayer;

import DataAccessLayer.VehicleData.*;
//import DataAccessLayer.LoginData.*;
import DataAccessLayer.OperatorData.*;
import DataAccessLayer.TimestampData.*;
import TransferObjects.*;
//import TransferObjects.*;
import java.sql.*;
import java.util.*;
import java.time.*;

/**
 *
 * @author johnt
 */
public class TransitBusinessLayer {
    private final VehicleDAO vehicleDao;
    private final OperatorDao operatorDao;
    private final TimestampDAO timestampDao;
    
    public TransitBusinessLayer() throws SQLException {
        vehicleDao = new VehicleDAOImpl();
        operatorDao = new OperatorDaoImpl();
        timestampDao = new TimestampDAOImpl();
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
        vehicleDao.registerVehicle(builder
                .setVehicleType(VehicleDTO.VehicleType.valueOf(vType))
                .setVehicleNum(vin)
                .setFuelType(fuelType)
                .setConsumptionRate(fuelRate)
                .setMaxPassenger(maxPass)
                .setRoute(null)
                .registerVehicle()
        );
//        VehicleDTO vehicle = new ;
    }
    public void updateVehicle(String fuel, String route, VehicleDTO vehicle) throws SQLException {
        vehicleDao.updateVehicle(fuel, route, vehicle);
    }
    public List<VehicleDTO> getVehicles() throws SQLException {
        return vehicleDao.getAllVehicles();
    }
    public List<String> getVehicleHeaders() throws SQLException {
        return vehicleDao.getVehicleHeaders();
    }
    public void logTime(int opId, String start, String end, String type) throws SQLException {
        TimeStamp time = new TimeStamp();
        LocalDate date = LocalDate.now();
        time.setOperatorId(opId);
        time.setStartTime(Timestamp.valueOf(date.toString() + " " + start + ":00"));
        time.setEndTime(Timestamp.valueOf(date.toString() + " " + end + ":00"));
        time.setTimestampType(type);
        timestampDao.addTimestamp(time);
    }
}
