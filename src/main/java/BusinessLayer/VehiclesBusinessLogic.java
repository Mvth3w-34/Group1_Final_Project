/* filename: VehiclesBusinessLogic.java
 * date: Apr. 3rd, 2025
 * authors: Stephanie Prystupa-Maule, John Tieu
 * course: CST8288 O.O.P. with Design Patterns - Lab Section 023 
 * professor: Samira Ouaaz
 * coursework: Final Project - Public Transit Management System
 */
package BusinessLayer;

import TransferObjects.VehicleDTO;
import DataAccessLayer.TransitDataSource;
import DataAccessLayer.VehicleData.VehicleDAO;
import DataAccessLayer.VehicleData.VehicleDAOImpl;
import java.sql.SQLException;
import java.util.List;
import TransferObjects.CredentialsDTO;

/**
 * Business logic layer for managing Vehicles data operations.
 * Handles the business rules //and validation// for vehicle operations,
 * serving as an intermediary between the presentation and data access layers.
 * 
 * 
 * @author Stephanie Prystupa-Maule
 * @version 1.0
 * @since 04/03/2025
 */
public class VehiclesBusinessLogic{
    
    private VehicleDAO vehicleDAO = null;
    
    /**
     * Constructor that sets the credentials for the Data Source and initializes
     * the Vehicle DAO
     * 
     * @param creds The CredentialsDTO containing the user login credentials
     */
    public VehiclesBusinessLogic(CredentialsDTO creds){
        TransitDataSource.getInstance().setCredentials(creds);
        vehicleDAO = new VehicleDAOImpl();
    }
    
    /**
     * Retrieves a vehicle from the database by its Vehicle ID
     * 
     * @param vehicleID Integer of the Vehicle ID of the record to retrieve
     * @return VehicleDTO The VehicleDTO object matching the vehicleID
     * @throws SQLException 
     */
    public VehicleDTO getVehicle(Integer vehicleID) throws SQLException {
        return vehicleDAO.getVehicleByID(vehicleID);
    }
    
    /**
     * Retrieves all vehicles from the database.
     * 
     * @return List of VehicleDTO objects containing all vehicles
     * @throws SQLException 
     */
    public List<VehicleDTO> getAllVehicles() throws SQLException {
        return vehicleDAO.getAllVehicles();
    }
    
    /**
     * Adds (registers) a new vehicle in the database, //after validation//
     * 
     * @param vehicle The VehicleDTO object to be added
     * @throws SQLException 
     */
    public void registerVehicle(VehicleDTO vehicle) throws SQLException {
        vehicleDAO.registerVehicle(vehicle);
    }
    
    /**
     * Updates an existing vehicle.
     * 
     * @param vehicle The VehicleDTO to be updated
     * @throws SQLException 
     */
    public void updateVehicle(VehicleDTO vehicle) throws SQLException {
        vehicleDAO.updateVehicle(vehicle);
    }
    
    /**
     * Updates the Fuel Rate of an existing vehicle.
     * Fuel Rates are recalculated regularly based on trip and fuel usage data.
     * 
     * @param vehicle The VehicleDTO to be updated
     * @throws SQLException 
     */
    public void updateVehicleFuelRate(VehicleDTO vehicle) throws SQLException {
        vehicleDAO.updateVehicleFuelRate(vehicle);
    }
    
    /**
     * Updates the Assigned Trip ID of an existing vehicle.
     * Assigned Trip IDs are updated when a vehicle is assigned to a new scheduled trip.
     * 
     * @param vehicle The VehicleDTO to be updated
     * @throws SQLException 
     */    
    public void updateVehicleTripID(VehicleDTO vehicle) throws SQLException {
        vehicleDAO.updateVehicleTripID(vehicle);
    }
    
    /**
     * Deletes (removes) an existing vehicle.
     * 
     * @param vehicle The VehicleDTO object to be be deleted
     * @throws SQLException 
     */
    public void removeVehicle(VehicleDTO vehicle) throws SQLException {
        vehicleDAO.removeVehicle(vehicle);
    }
    
    // TODO add validation?
}
