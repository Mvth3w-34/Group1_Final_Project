/* filename: TripScheduleDAO.java
 * date: Apr. 5th, 2025
 * authors: Stephanie Prystupa-Maule, John Tieu
 * course: CST8288 O.O.P. with Design Patterns - Lab Section 023 
 * professor: Samira Ouaaz
 * coursework: Final Project - Public Transit Management System
 */
package DataAccessLayer.TripData;

import TransferObjects.TripScheduleDTO;
import java.sql.SQLException;
import java.util.List;

/**
 * Data Access Object interface for Trip Schedules 
 * Defines the contract for retrieving data from the vw_trip_schedules view
 * that collects trip schedules and related data.
 * 
 * @author johnt
 * @author Stephanie Prystupa-Maule
 * @version 2.0
 * @since 04/05/2025
 */
public interface TripScheduleDAO {
    /**
     * Retrieves a trip schedule and details from the database, by trip schedule ID.
     * 
     * @param scheduleID Integer value of the trip schedule ID 
     * @return TripScheduleDTO The TripScheduleDTO object matching the scheduleID
     * @throws SQLException 
     */
    TripScheduleDTO getTripSchedByID(Integer scheduleID) throws SQLException;
    
    /**
     * Retrieves all trip schedules and their details from the database.
     * 
     * @return List of TripScheduleDTO objects
     * @throws SQLException 
     */ 
    List<TripScheduleDTO> getAllTripScheds() throws SQLException;
    /**
     * Retrieves trip schedules and their details that match a given route
     * 
     * @param routeName String of the route name 
     * @return List of TripScheduleDTO object matching the route name
     * @throws SQLException 
     */
    List<TripScheduleDTO> getTripSchedsByRoute(String routeName) throws SQLException;
    
}
