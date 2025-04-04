/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BusinessLayer;

import DataAccessLayer.VehicleData.*;
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
    
    public void updateVehicle(String fuel, String route, String id) throws SQLException {
        String newFuel;
        String routeUpdate;
        // Clear the current trip from a vehicle if default option is selected
        try {
            if (route.isBlank() || route.isEmpty() || route.equals("0")) {
                routeUpdate = null;
            } else {
                routeUpdate = route;
            }
        } catch (NullPointerException e) {
            routeUpdate = null;
        }
        // Check if the fuel textbox input is empty
        try {
            if (fuel.isBlank() || fuel.isEmpty()) {
                newFuel = null;
            } else {
                newFuel = fuel;
            }
        } catch (NullPointerException e) {
            newFuel = null;
        }
        if(id.equals("0")) {
            throw new SQLException();
        } else {
            int vehicleID = Integer.parseInt(id);
            for (int i = 0; i < getVehicles().size(); i++) {
                if (getVehicles().get(i).getVehicleID() == vehicleID) {
                    vehicleDao.updateVehicle(newFuel, routeUpdate, getVehicles().get(i));
                }
            }
        }
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
