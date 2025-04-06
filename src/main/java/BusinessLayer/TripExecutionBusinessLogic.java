/* filename: TripExecutionBusinessLogic.java
 * date: Apr. 5th, 2025
 * authors: Stephanie Prystupa-Maule
 * course: CST8288 O.O.P. with Design Patterns - Lab Section 023 
 * professor: Samira Ouaaz
 * coursework: Final Project - Public Transit Management System
 */
package BusinessLayer;

import DataAccessLayer.TransitDataSource;
import DataAccessLayer.TripData.ActualTripDAO;
import DataAccessLayer.TripData.ActualTripDAOImpl;
import DataAccessLayer.TripData.ActualStopTimeDAO;
import DataAccessLayer.TripData.ActualStopTimeDAOImpl;
import TransferObjects.ActualTripDTO;
import TransferObjects.ActualStopTimeDTO;
import TransferObjects.CredentialsDTO;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.util.List;

/**
 * Business logic layer for managing Trip Execution data operations.
 * Handles the business rules for actual trip and stop time operations,
 * serving as an intermediary between the presentation and data access layers.
 * 
 * @author Stephanie Prystupa-Maule
 * @version 1.0
 * @since 04/05/2025
 */
public class TripExecutionBusinessLogic {
    
    private ActualTripDAO actualTripDAO = null;
    private ActualStopTimeDAO actualStopTimeDAO = null;
    
    /**
     * Constructor that sets the credentials for the Data Source and initializes
     * the ActualTrip and ActualStopTime DAOs
     * 
     * @param creds The CredentialsDTO containing the user login credentials
     */
    public TripExecutionBusinessLogic(CredentialsDTO creds) {
        TransitDataSource.getInstance().setCredentials(creds);
        actualTripDAO = new ActualTripDAOImpl();
        actualStopTimeDAO = new ActualStopTimeDAOImpl();
    }
    
    /**
     * Constructor with injected DAOs for testing purposes
     * 
     * @param actualTripDAO The ActualTripDAO implementation to use
     * @param actualStopTimeDAO The ActualStopTimeDAO implementation to use
     * @param creds The CredentialsDTO containing the user login credentials
     */
    public TripExecutionBusinessLogic(ActualTripDAO actualTripDAO, ActualStopTimeDAO actualStopTimeDAO, CredentialsDTO creds) {
        TransitDataSource.getInstance().setCredentials(creds);
        this.actualTripDAO = actualTripDAO;
        this.actualStopTimeDAO = actualStopTimeDAO;
    }
    
    /* Actual Trip Methods */
    
    /**
     * Retrieves an actual trip from the database by its Actual Trip ID
     * 
     * @param actualTripId Integer of the Actual Trip ID of the record to retrieve
     * @return ActualTripDTO The ActualTripDTO object matching the actualTripId
     * @throws SQLException if a database access error occurs
     */
    public ActualTripDTO getActualTrip(Integer actualTripId) throws SQLException {
        return actualTripDAO.getActualTripByID(actualTripId);
    }
    
    /**
     * Retrieves all actual trips from the database.
     * 
     * @return List of ActualTripDTO objects containing all actual trips
     * @throws SQLException if a database access error occurs
     */
    public List<ActualTripDTO> getAllActualTrips() throws SQLException {
        return actualTripDAO.getAllActualTrips();
    }
    
    /**
     * Creates a new actual trip in the database.
     * 
     * @param actualTrip The ActualTripDTO object to be added
     * @throws SQLException if a database access error occurs
     */
    public void createActualTrip(ActualTripDTO actualTrip) throws SQLException {
        actualTripDAO.createActualTrip(actualTrip);
    }
    
    /**
     * Updates an existing actual trip.
     * 
     * @param actualTrip The ActualTripDTO to be updated
     * @throws SQLException if a database access error occurs
     */
    public void updateActualTrip(ActualTripDTO actualTrip) throws SQLException {
        actualTripDAO.updateActualTrip(actualTrip);
    }
    
    /**
     * Deletes an existing actual trip.
     * 
     * @param actualTripId The ID of the actual trip to be deleted
     * @throws SQLException if a database access error occurs
     */
    public void deleteActualTrip(Integer actualTripId) throws SQLException {
        actualTripDAO.deleteActualTrip(actualTripId);
    }
    
    /**
     * Generates a simulated trip with random time variances.
     * 
     * @param scheduledTripId The scheduled trip to use as a template
     * @param operatorId The operator executing the trip
     * @param vehicleId The vehicle used for the trip
     * @param tripDate The date of the trip
     * @param punctualityLevel How punctual the simulation should be:
     *        - "HIGH": Small variances (-60 to 120 seconds)
     *        - "MEDIUM": Moderate variances (-180 to 300 seconds)
     *        - "LOW": Large variances (-300 to 900 seconds)
     * @throws SQLException If there is a database error
     * @throws IllegalArgumentException If parameters are invalid
     */
    public void simulateTrip(
            Integer scheduledTripId,
            Integer operatorId,
            Integer vehicleId,
            Date tripDate,
            String punctualityLevel) throws SQLException {
            
        int minVariance;
        int maxVariance;
        
        switch (punctualityLevel.toUpperCase()) {
            case "HIGH":
                minVariance = -60;   // 1 minute early
                maxVariance = 120;   // 2 minutes late
                break;
            case "MEDIUM":
                minVariance = -180;  // 3 minutes early
                maxVariance = 300;   // 5 minutes late
                break;
            case "LOW":
                minVariance = -300;  // 5 minutes early
                maxVariance = 900;   // 15 minutes late
                break;
            default:
                throw new IllegalArgumentException(
                    "Punctuality level must be HIGH, MEDIUM, or LOW");
        }
        
        actualTripDAO.generateTestTrip(
            scheduledTripId, operatorId, vehicleId, 
            tripDate, minVariance, maxVariance);
    }   
    
    /*  Actual Stop Time Methods */
    
    /**
     * Retrieves an actual stop time from the database by its ID.
     * 
     * @param actualStopTimeId The ID of the actual stop time to retrieve
     * @return ActualStopTimeDTO object containing the actual stop time data
     * @throws SQLException if a database access error occurs
     */
    public ActualStopTimeDTO getActualStopTime(Integer actualStopTimeId) throws SQLException {
        return actualStopTimeDAO.getActualtopTimeByID(actualStopTimeId);
    }
    
    /**
     * Retrieves all actual stop times from the database.
     * 
     * @return List of ActualStopTimeDTO objects containing all actual stop times
     * @throws SQLException if a database access error occurs
     */
    public List<ActualStopTimeDTO> getAllActualStopTimes() throws SQLException {
        return actualStopTimeDAO.getAllActualStopTimes();
    }
    
    /**
     * Creates a new actual stop time in the database.
     * 
     * @param actualStopTime The ActualStopTimeDTO object to be added
     * @throws SQLException if a database access error occurs
     */
    public void createActualStopTime(ActualStopTimeDTO actualStopTime) throws SQLException {
        actualStopTimeDAO.createActualStopTime(actualStopTime);
    }
    
    /**
     * Updates an existing actual stop time.
     * 
     * @param actualStopTime The ActualStopTimeDTO to be updated
     * @throws SQLException if a database access error occurs
     */
    public void updateActualStopTime(ActualStopTimeDTO actualStopTime) throws SQLException {
        actualStopTimeDAO.updateActualStopTime(actualStopTime);
    }
    
    /**
     * Deletes an existing actual stop time.
     * 
     * @param actualStopTimeId The ID of the actual stop time to be deleted
     * @throws SQLException if a database access error occurs
     */
    public void deleteActualStopTime(Integer actualStopTimeId) throws SQLException {
        actualStopTimeDAO.deleteActualStopTime(actualStopTimeId);
    }
    
    /**
     * Records an actual stop time directly using the stored procedure.
     * This is a convenience method for creating a stop time entry with minimal data.
     * 
     * @param actualTripId The ID of the actual trip this stop time belongs to
     * @param seqStopId The route stop sequence ID
     * @param actualArrival The actual arrival time at the stop
     * @param actualDeparture The actual departure time from the stop
     * @throws SQLException if a database access error occurs
     */
    public void recordActualStopTime(Integer actualTripId, Integer seqStopId, 
                                   Time actualArrival, Time actualDeparture) throws SQLException {
        actualStopTimeDAO.executeInsertActualStopTimeProcedure(actualTripId, seqStopId, actualArrival, actualDeparture);
    }
}

