/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BusinessLayer;

import DataAccessLayer.Routes.RoutesTripsDAO;
import DataAccessLayer.Routes.RoutesTripsDAOImpl;
import DataAccessLayer.TimestampData.TimestampDAO;
import DataAccessLayer.TimestampData.TimestampDAOImpl;
import DataAccessLayer.VehicleData.*;
import TransferObjects.LoginDTO;
import TransferObjects.OperatorDTO;
import TransferObjects.TimeStamp;
import TransferObjects.VehicleDTO;
import TransferObjects.VehicleStationTimetable;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;
import DataAccessLayer.OperatorData.*;
import DataAccessLayer.Routes.*;
import DataAccessLayer.TimestampData.*;
import TransferObjects.*;
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
    private final RoutesTripsDAO routesTripsDao;
    
    public TransitBusinessLayer() throws SQLException {
        vehicleDao = new VehicleDAOImpl();
        operatorDao = new OperatorDaoImpl();
        timestampDao = new TimestampDAOImpl();
        routesTripsDao = new RoutesTripsDAOImpl();
        
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
    
    public List<String> getHeaders(String tblName) throws SQLException {
        if (tblName.toLowerCase().equals("vehicle")) {
            return vehicleDao.getVehicleHeaders();
        } else if (tblName.toLowerCase().equals("vehicleroutes")) {
            return routesTripsDao.getHeaders();
        }
        throw new SQLException();
    }
    
    public List<Integer> getRoutes() throws SQLException {
        return routesTripsDao.getRoutes();
    }
    
    public void updateVehicle(String fuel, String route, VehicleDTO vehicle) throws SQLException {
        vehicleDao.updateVehicle(fuel, route, vehicle);
    }
    public List<VehicleDTO> getVehicles() throws SQLException {
        return vehicleDao.getAllVehicles();
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
    public void registerAccount(String name, String email, String username, String password, String userType) throws SQLException, IllegalArgumentException {
        LoginDTO login = new LoginDTO();
        login.setUsername(username);
        login.setPassword(password);
        
        try {
            OperatorDTO operator = new OperatorDTO();
            operator.setName(name);
            operator.setEmail(email);
            operator.setUserType(OperatorDTO.UserType.valueOf(userType));
            operator.assignLogin(login);
            operatorDao.registerOperator(operator);
        } catch (IllegalArgumentException | SQLException e) {
            throw e;
        }
    }
    public List<VehicleStationTimetable> getRoutes(int vehicleID) throws SQLException {
        return routesTripsDao.getAllVehicleStationTimes(vehicleID);
    }
}
