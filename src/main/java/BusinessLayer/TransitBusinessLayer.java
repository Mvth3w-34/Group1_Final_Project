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
 * The business layer used to handle the logic for the Transit web app
 * @author John Tieu
 */
public class TransitBusinessLayer {
    private final VehicleDAO vehicleDao;
    private final OperatorDao operatorDao;
    private final TimestampDAO timestampDao;
    private final RoutesTripsDAO routesTripsDao;
    
    /**
     * The constructor for the business logic
     * @throws SQLException 
     */
    public TransitBusinessLayer() throws SQLException {
        vehicleDao = new VehicleDAOImpl();
        operatorDao = new OperatorDaoImpl();
        timestampDao = new TimestampDAOImpl();
        routesTripsDao = new RoutesTripsDAOImpl();
    }
    /**
     * Verifies if the credentials entered exists in the DB system
     * @param userInput The input username
     * @param passInput The input password
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
    /**
     * Registers a vehicle
     * @param vType The vehicle type
     * @param vin The vehicle identification number (VIN)
     * @param fuelType The fuel type of the vehicle
     * @param fuelRate The fuel consumption rate
     * @param maxPass The passenger capacity of the vehicle
     * @throws SQLException when the registration fails
     */
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
    /**
     * Gets the list of headers for the selected table
     * @param tblName The table to be selected
     * @return The list of header names
     * @throws SQLException If unable to retrieve the table
     */
    public List<String> getHeaders(String tblName) throws SQLException {
        if (tblName.toLowerCase().equals("vehicle")) {
            return vehicleDao.getVehicleHeaders();
        } else if (tblName.toLowerCase().equals("vehicleroutes")) {
            return routesTripsDao.getHeaders();
        }
        throw new SQLException();
    }
    /**
     * Gets a list of available routes
     * @return The list of available route IDs
     * @throws SQLException 
     */
    public List<Integer> getRoutesID() throws SQLException {
        return routesTripsDao.getRoutes();
    }
    /**
     * Updates a vehicle's fuel type, and assigned trip
     * @param fuel The new fuel type
     * @param route The new route ID
     * @param id The vehicle ID to update
     * @throws SQLException If the update fails
     */
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
    /**
     * Returns a list of vehicles from the DB
     * @return The list of vehicles
     * @throws SQLException If the connection is unable to access the DB
     */
    public List<VehicleDTO> getVehicles() throws SQLException {
        return vehicleDao.getAllVehicles();
    }
    /**
     * Register's the operator's log time
     * @param opId The operator's ID
     * @param start The start timestamp
     * @param end The end timestamp
     * @param type The type of end time clock-out
     * @throws SQLException If the DB is unable to log the operator's time
     */
    public void logTime(int opId, String start, String end, String type) throws SQLException {
        if (start != null || end != null || !start.isEmpty()) {
            TimeStamp time = new TimeStamp();
            LocalDate date = LocalDate.now();
            time.setOperatorId(opId);
            time.setStartTime(Timestamp.valueOf(date.toString() + " " + start + ":00"));
            time.setEndTime(Timestamp.valueOf(date.toString() + " " + end + ":00"));
            time.setTimestampType(type);
            timestampDao.addTimestamp(time);
        }
    }
    /**
     * Registers a new user/operator to the system
     * @param name The operator's name
     * @param email The operator's email
     * @param username The operator's username to login
     * @param password The operator's password to login
     * @param userType The operator's account type: {@code MANAGER} and {@code OPERATOR} 
     * @throws SQLException If the registration fails
     * @throws IllegalArgumentException If the user type input is bad
     */
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
    /**
     * Get a list of vehicle route timestamps
     * @param vehicleID The vehicle ID
     * @return The list of vehicle arrival and departure times for each timestamps in the route
     * @throws SQLException If the DB is unable to retrieve the route timestamps
     */
    public List<VehicleStationTimetable> getRoutes(int vehicleID) throws SQLException {
        return routesTripsDao.getAllVehicleStationTimes(vehicleID);
    }
}
