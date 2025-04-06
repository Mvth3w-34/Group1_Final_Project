/* filename: ActualStopTimesDAO.java
 * date: Apr. 5th, 2025
 * authors: Stephanie Prystupa-Maule, John Tieu
 * course: CST8288 O.O.P. with Design Patterns - Lab Section 023 
 * professor: Samira Ouaaz
 * coursework: Final Project - Public Transit Management System
 */
package DataAccessLayer.TripData;

import TransferObjects.ActualStopTimeDTO;
import TransferObjects.ActualTripDTO;
import java.sql.SQLException;
import java.sql.Time;
import java.util.List;
import java.util.Map;

/**
 * Data Access Object interface for Actual Stop Times
 * Defines the contract for database operations on actual_stop_times table.
 * 
 * @author Stephanie Prystupa-Maule
 * @version 1.0
 * @since 04/03/2025
 */
public interface ActualStopTimeDAO {

    /**
     * Retrieves an actual stop time from the database by its ID.
     * 
     * @param actualStopTimeId Integer value of the actual stop time ID
     * @return ActualStopTimeDTO The ActualStopTimeDTO object matching the actualStopTimeId
     * @throws SQLException If there is an error accessing the database
     */    
    ActualStopTimeDTO getActualtopTimeByID(Integer actualStopTimeId) throws SQLException;

    /**
     * Retrieves all actual stop times from the database.
     * 
     * @return List of ActualStopTimeDTO objects
     * @throws SQLException If there is an error accessing the database
     */    
    List<ActualStopTimeDTO> getAllActualStopTimes() throws SQLException;

     /**
     * Adds a new actual stop time record using stored procedure
     * 
     * @param actualStopTime The ActualStopTimeDTO to be added
     * @throws SQLException If there is an error accessing the database
     */   
    void createActualStopTime(ActualStopTimeDTO actualStopTime) throws SQLException;

    /**
     * Updates an actual stop time record in the database.
     * 
     * @param actualStopTime The ActualStopTimeDTO to be updated
     * @throws SQLException If there is an error accessing the database
     */    
    void updateActualStopTime(ActualStopTimeDTO actualStopTime) throws SQLException;

    /**
     * Deletes an actual stop time record from the database.
     * 
     * @param actualStopTimeId The ID of the actual stop time to be deleted
     * @throws SQLException If there is an error accessing the database
     */    
    void deleteActualStopTime(Integer actualStopTimeId) throws SQLException;

    /**
     * Executes the INSERT_ACTUAL_STOP_TIME stored procedure.
     * 
     * @param actualTripId The ID of the actual trip this stop time belongs to
     * @param seqStopId The route stop sequence ID
     * @param actualArrival The actual arrival time at the stop
     * @param actualDeparture The actual departure time from the stop
     * @throws SQLException If there is an error accessing the database
     */
    void executeInsertActualStopTimeProcedure(
            Integer actualTripId,
            Integer seqStopId,
            Time actualArrival,
            Time actualDeparture) throws SQLException;    
    
    /**
     * Retrieves a list of actual stop times that match the specified filter criteria.
     * 
     * @param filters A map containing filter criteria where:
     *                - key: the field name to filter on (e.g., "actualTripId", "seqStopId"))
     *                - value: the value to match (can be a simple value or potentially a range)
     * @return A list of ActualStopTimeDTO objects matching the filter criteria, or an empty list if none found
     * @throws SQLException
     */
    List<ActualStopTimeDTO> findByFilter(Map<String, Object> filters) throws SQLException;
     
}
