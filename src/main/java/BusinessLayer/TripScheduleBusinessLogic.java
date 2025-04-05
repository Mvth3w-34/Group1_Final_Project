/* filename: TripScheduleBusinessLogic.java
 * date: Apr. 5th, 2025
 * author: Stephanie Prystupa-Maule
 * course: CST8288 O.O.P. with Design Patterns - Lab Section 023 
 * professor: Samira Ouaaz
 * coursework: Final Project - Public Transit Management System
 */
package BusinessLayer;

import TransferObjects.TripScheduleDTO;
import DataAccessLayer.TransitDataSource;
import DataAccessLayer.TripData.TripScheduleDAO;
import DataAccessLayer.TripData.TripScheduleDAOImpl;
import java.sql.SQLException;
import java.util.List;
import TransferObjects.CredentialsDTO;

/**
 * Business logic layer for managing Trip Schedule data operations.
 * Handles the business rules for trip schedule operations,
 * serving as an intermediary between the presentation and data access layers.
 * 
 * @author Stephanie Prystupa-Maule
 * @version 1.0
 * @since 04/05/2025
 */
public class TripScheduleBusinessLogic {
    
    private TripScheduleDAO tripScheduleDAO = null;
    
    /**
     * Constructor that sets the credentials for the Data Source and initializes
     * the Trip Schedule DAO
     * 
     * @param creds The CredentialsDTO containing the user login credentials
     */
    public TripScheduleBusinessLogic(CredentialsDTO creds) {
        TransitDataSource.getInstance().setCredentials(creds);
        tripScheduleDAO = new TripScheduleDAOImpl();
    }
    
    /**
     * Retrieves a trip schedule from the database by its Trip Schedule ID
     * 
     * @param scheduleID Integer of the Trip Schedule ID of the record to retrieve
     * @return TripScheduleDTO The TripScheduleDTO object matching the scheduleID
     * @throws SQLException 
     */
    public TripScheduleDTO getTripSchedule(Integer scheduleID) throws SQLException {
        return tripScheduleDAO.getTripSchedByID(scheduleID);
    }
    
    /**
     * Retrieves all trip schedules from the database.
     * 
     * @return List of TripScheduleDTO objects containing all trip schedules
     * @throws SQLException 
     */
    public List<TripScheduleDTO> getAllTripSchedules() throws SQLException {
        return tripScheduleDAO.getAllTripScheds();
    }
    
    /**
     * Retrieves trip schedules for a specific route from the database.
     * 
     * @param routeName The name of the route to filter by
     * @return List of TripScheduleDTO objects containing trip schedules for the matching route
     * @throws SQLException 
     */
    public List<TripScheduleDTO> getTripSchedulesByRoute(String routeName) throws SQLException {
        return tripScheduleDAO.getTripSchedsByRoute(routeName);
    }
    
    /**
     * Filters trip schedules based on whether they have a vehicle assigned.
     * 
     * @param schedules List of TripScheduleDTO objects to filter
     * @param vehicleAssigned true to filter for schedules with vehicles assigned, 
     *                        false for schedules without vehicles
     * @return List of filtered TripScheduleDTO objects
     */
    public List<TripScheduleDTO> filterByVehicleAssignment(List<TripScheduleDTO> schedules, boolean vehicleAssigned) {
        return schedules.stream()
                .filter(schedule -> schedule.hasVehicleAssigned() == vehicleAssigned)
                .collect(java.util.stream.Collectors.toList());
    }
    
    /**
     * Gets trip schedules that already have a vehicle assigned
     * 
     * @return List of TripScheduleDTO objects with assigned vehicles
     * @throws SQLException 
     */
    public List<TripScheduleDTO> getAssignedTrips() throws SQLException {
        List<TripScheduleDTO> allSchedules = getAllTripSchedules();
        return filterByVehicleAssignment(allSchedules, true);
    }
    
    /**
     * Gets trip schedules that don't have a vehicle assignment yet
     * 
     * @return List of TripScheduleDTO objects without assigned vehicles
     * @throws SQLException 
     */
    public List<TripScheduleDTO> getUnassignedTrips() throws SQLException {
        List<TripScheduleDTO> allSchedules = getAllTripSchedules();
        return filterByVehicleAssignment(allSchedules, false);
    }

}
