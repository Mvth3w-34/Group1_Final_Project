/* filename: VehicleDAO.java
 * date: Apr. 3rd, 2025
 * authors: Stephanie Prystupa-Maule, John Tieu
 * course: CST8288 O.O.P. with Design Patterns - Lab Section 023 
 * professor: Samira Ouaaz
 * coursework: Final Project - Public Transit Management System
 */
package DataAccessLayer.VehicleData;

import TransferObjects.VehicleDTO;
import java.sql.SQLException;
import java.util.List;

/**
 * Data Access Object interface for Vehicles
 * Defines the contract for database operations on vehicles table.
 * 
 * @author johnt
 * @author Stephanie Prystupa-Maule
 * @version 2.0
 * @since 04/03/2025
 */
public interface VehicleDAO {
    /**
     * Retrieves a vehicle from the database, by its ID.
     * 
     * @param vehicleID Integer value of the vehicle ID
     * @return VehicleDTO The VehicleDTO object matching the vehicleID
     * @throws SQLException 
     */    
    VehicleDTO getVehicleByID(Integer vehicleID) throws SQLException;
    
    /**
     * Retrieves all vehicles from the database.
     * 
     * @return List of VehicleDTO objects
     * @throws SQLException 
     */
    List<VehicleDTO> getAllVehicles() throws SQLException;
    
    /**
     * Adds (registers) a new vehicle to the database.
     * 
     * @param vehicle The VehicleDTO to be added
     * @throws SQLException 
     */
    void registerVehicle(VehicleDTO vehicle) throws SQLException;
    
    /**
     * Updates vehicle record in the database (all fields).
     * 
     * @param vehicle The VehicleDTO to be updated
     * @throws SQLException 
     */
    void updateVehicle(VehicleDTO vehicle) throws SQLException;
    
    /**
     * Updates the Fuel Consumption Rate for a vehicle record in the database.
     * Fuel Rates are recalculated regularly based on trip and fuel usage data.
     * 
     * @param vehicle The VehicleDTO to be updated
     * @throws SQLException 
     */
    void updateVehicleFuelRate(VehicleDTO vehicle) throws SQLException;
    
    /**
     * Updates the Assigned Trip ID for a vehicle record in the database.
     * Assigned Trip IDs are updated when a vehicle is assigned to a new 
     * scheduled trip.
     * 
     * @param vehicle The VehicleDTO to be updated
     * @throws SQLException 
     */
    void updateVehicleTripID(VehicleDTO vehicle) throws SQLException;
    
    /**
     * Deletes (removes) a vehicle record from the database.
     * 
     * @param vehicle The VehicleDTO to be deleted
     * @throws SQLException 
     */
    void removeVehicle(VehicleDTO vehicle) throws SQLException;
    
//    List<String> getVehicleHeaders() throws SQLException;
}
