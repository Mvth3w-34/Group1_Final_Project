/* filename: ActualTripDAO.java
 * date: Apr. 5th, 2025
 * authors: Stephanie Prystupa-Maule, John Tieu
 * course: CST8288 O.O.P. with Design Patterns - Lab Section 023 
 * professor: Samira Ouaaz
 * coursework: Final Project - Public Transit Management System
 */
package DataAccessLayer.TripData;

import TransferObjects.ActualTripDTO;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Data Access Object interface for Actual Trips
 * Defines the contract for database operations on actual_trips table.
 * 
 * @author Stephanie Prystupa-Maule
 * @version 1.0
 * @since 04/03/2025
 */
public interface ActualTripDAO {
    
    /**
     * Retrieves an actual trip from the database by its ID.
     * 
     * @param actualTripId Integer value of the actual trip ID
     * @return ActualTripDTO The ActualTripDTO object matching the actualTripId
     * @throws SQLException If there is an error accessing the database
     */  
    ActualTripDTO getActualTripByID(Integer actualTripId) throws SQLException;

    /**
     * Retrieves all actual trips from the database.
     * 
     * @return List of ActualTripDTO objects
     * @throws SQLException If there is an error accessing the database
     */    
    List<ActualTripDTO> getAllActualTrips() throws SQLException;
    
    /**
     * Adds a new actual trip record to the database using stored procedure
     * 
     * @param actualTrip The ActualTripDTO to be added
     * @throws SQLException If there is an error accessing the database
     */    
    void createActualTrip(ActualTripDTO actualTrip) throws SQLException;

    /**
     * Updates an actual trip record in the database.
     * 
     * @param actualTrip The ActualTripDTO to be updated
     * @throws SQLException If there is an error accessing the database
     */    
    void updateActualTrip(ActualTripDTO actualTrip) throws SQLException;

    /**
     * Deletes an actual trip record from the database.
     * 
     * @param actualTripID The ID of the actual trip to be deleted
     * @throws SQLException If there is an error accessing the database
     */    
    void deleteActualTrip(Integer actualTripID) throws SQLException;
    
    /**
     * Executes the INSERT_ACTUAL_TRIP stored procedure.
     * 
     * @param scheduledTripId The ID of the scheduled trip
     * @param operatorId The ID of the operator
     * @param vehicleId The ID of the vehicle
     * @param tripDate The date of the trip
     * @param tripStatus The status of the trip
     * @throws SQLException If there is an error accessing the database
     */
    void executeInsertActualTripProcedure(
            Integer scheduledTripId, 
            Integer operatorId, 
            Integer vehicleId, 
            Date tripDate, 
            String tripStatus) throws SQLException;    

    /**
     * Generates test trip data with random variances using the GENERATE_TEST_TRIP stored procedure.
     * This is primarily for testing and simulation purposes.
     * 
     * @param scheduledTripId The ID of the scheduled trip to base the test data on
     * @param operatorId The ID of the operator to associate with the trip
     * @param vehicleId The ID of the vehicle to associate with the trip
     * @param tripDate The date for the generated trip
     * @param minVariance The minimum variance in seconds (can be negative for early arrivals/departures)
     * @param maxVariance The maximum variance in seconds (positive for late arrivals/departures)
     * @throws SQLException If there is an error accessing the database
     */
    void generateTestTrip(
        Integer scheduledTripId,
        Integer operatorId,
        Integer vehicleId,
        Date tripDate,
        Integer minVariance,
        Integer maxVariance) throws SQLException;
    
    /**
     * Retrieves a list of actual trips that match the specified filter criteria.
     * 
     * @param filters A map containing filter criteria where:
     *                - key: the field name to filter on (e.g., "operatorId", "vehicleId", "tripDate", "tripStatus")
     *                - value: the value to match (can be a simple value or potentially a range object for dates)
     * @return A list of ActualTripDTO objects matching the filter criteria, or an empty list if none found
     * @throws SQLException If there is an error accessing the database
     */
    List<ActualTripDTO> findByFilter(Map<String, Object> filters) throws SQLException;
    
}
