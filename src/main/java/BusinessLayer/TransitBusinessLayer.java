/* filename: ActualTripDAOImpl.java
 * date: Apr. 6th, 2025
 * authors: John Tieu, Mathew Chebet
 * course: CST8288 O.O.P. with Design Patterns - Lab Section 023 
 * professor: Samira Ouaaz
 * coursework: Final Project - Public Transit Management System
 */
package BusinessLayer;

import DataAccessLayer.MaintenanceRequest.MaintenanceRequestDAO;
import DataAccessLayer.MaintenanceRequest.MaintenanceRequestDAOImpl;
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
import DataAccessLayer.VehicleComponents.VehicleComponentDAO;
import DataAccessLayer.VehicleComponents.VehicleComponentDAOImpl;
import TransferObjects.MaintenanceRequestTicketDTO;
import TransferObjects.VehicleComponentDTO;

/**
 * The business layer used to handle the logic for the Transit web app
 * @author John Tieu, Mathew Chebet
 */
public class TransitBusinessLayer {
    private final VehicleDAO vehicleDao;
    private final OperatorDAO operatorDao;
    private final TimestampDAO timestampDao;
    private final RoutesTripsDAO routesTripsDao;
    private final VehicleComponentDAO vehicleComponentDao;
    private final MaintenanceRequestDAO maintenanceRequestDao;
    
    /**
     * The constructor for the business logic
     * @throws SQLException 
     */
    public TransitBusinessLayer() throws SQLException {
        vehicleDao = new VehicleDAOImpl();
        operatorDao = new OperatorDAOImpl();
        timestampDao = new TimestampDAOImpl();
        routesTripsDao = new RoutesTripsDAOImpl();
        vehicleComponentDao = new VehicleComponentDAOImpl();
        maintenanceRequestDao = new MaintenanceRequestDAOImpl();

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
            String value =operatorDao.getAllOperators().get(i).getLogin().getUsername();
            String value2 = operatorDao.getAllOperators().get(i).getLogin().getPassword();
            if (userInput.equals(value) && 
                    passInput.equals(value2)) {
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
                .setTripID(null)
                .buildVehicle()
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
                    vehicleDao.updateVehicle(newFuel, routeUpdate, vehicleID);
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
            operator.setOperatorName(name);
            operator.setEmail(email);
            operator.setUserType(OperatorDTO.UserType.valueOf(userType));
            operator.assignLogin(login);
            operatorDao.addOperator(operator);
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
    
    //The following code was written by Mathew Chebet
    
    /**
     * This method will retrieve all of the maintenance requests.
     * 
     * @return maintenanceRequestDao.getAllMaintenanceRequests(), 
     *  a list of objects representing rows of data needed for a maintenance request dashboard
     */
    public List<Object[]> getAllMaintenanceRequests(){
        return maintenanceRequestDao.getAllMaintenanceRequests();
    }
    
    /**
     * This method will retrieve all of maintenance requests based on their completion status.
     * 
     * @return maintenanceRequestDao.getMaintenanceRequestsByCompletion(complete), 
     *  a list of objects representing rows of data needed for a maintenance request dashboard
     */
    public List<Object[]> getMaintenanceRequestsByCompletion(){
        return maintenanceRequestDao.getMaintenanceRequestsByCompletion();
    }
    
    /**
     * This method will retrieve all of the incomplete maintenance requests.
     * 
     * @param request, a MaintenanceRequestTicketDTO object
     */
    public void addMaintenanceRequest(MaintenanceRequestTicketDTO request){
        maintenanceRequestDao.addMaintenanceRequest(request);
    }
    
    /**
     * This method will retrieve all of the incomplete maintenance requests.
     * 
     * @param id, a maintenance request id
     */
    public MaintenanceRequestTicketDTO getMaintenanceRequestById(int id){
        return maintenanceRequestDao.getMaintenanceRequestById(id);
    }
    
    /**
     * This method will update the status of an incomplete request to complete.
     * 
     * @param request, a MaintenanceRequestTicketDTO object
     */
    public void updateMaintenanceRequest(MaintenanceRequestTicketDTO request){
        maintenanceRequestDao.updateMaintenanceRequest(request);
    }
    
    /**
     * This method returns a list of all of the known vehicle components for a specific vehicle.
     * 
     * @param request, a MaintenanceRequestTicketDTO object
     */
    public List<VehicleComponentDTO> getComponentsByVehicleID(int id){
        return vehicleComponentDao.getComponentsByVehicleID(id);
    }
    
    /**
     * This method returns a vehicle component based on the vehicle ID and component ID.
     * 
     * @param vehicleID, a vehicleID
     * @param componentID, a componentID
     * @return vehicleComponentDao.getComponentByIDs(vehicleID, componentID), a VehicleComponentDTO object
     */
    public VehicleComponentDTO getComponentByIDs(int vehicleID, int componentID){
        return vehicleComponentDao.getComponentByIDs(vehicleID, componentID);
    }
    
    /**
     * This method will add a vehicle component to the database.
     * 
     * @param component, a VehicleComponentDTO object
     */
    public void addVehicleComponent(VehicleComponentDTO component){
        vehicleComponentDao.addVehicleComponent(component);
    }
    
    /**
     * This method will update the hours used for a vehicle component.
     * 
     * @param component, a VehicleComponentDTO object
     */
    public void updateVehicleComponent(VehicleComponentDTO component){
        vehicleComponentDao.updateVehicleComponent(component);
    }
}
