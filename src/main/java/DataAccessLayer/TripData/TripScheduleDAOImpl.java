/* filename: TripScheduleDAOImpl.java
 * date: Apr. 5th, 2025
 * authors: Stephanie Prystupa-Maule, John Tieu
 * course: CST8288 O.O.P. with Design Patterns - Lab Section 023 
 * professor: Samira Ouaaz
 * coursework: Final Project - Public Transit Management System
 */
package DataAccessLayer.TripData;

import TransferObjects.TripScheduleDTO;
import TransferObjects.VehicleDTO;
import DataAccessLayer.BaseDAOImpl;
import DataAccessLayer.VehicleData.VehicleDAO;
import DataAccessLayer.VehicleData.VehicleDAOImpl;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Implementation of the TripScheduleDAO interface that provides database operations
 * for retrieving trip schedule data from the vw_trip_schedules view.
 * Extends BaseDAOImpl to leverage common database functionality.
 * 
 * @author johnt
 * @author Stephanie Prystupa-Maule
 * @version 2.0
 * @since 04/05/2025
 * @see BaseDAOImpl
 * @see TripScheduleDAO
 */
public class TripScheduleDAOImpl extends BaseDAOImpl implements TripScheduleDAO {
    
    // VehicleDAO to populate VehicleDTO within TripScheduleDTO
    private final VehicleDAO vehicleDAO;
    
    // Initialize the VehicleDAO
    public TripScheduleDAOImpl() {
        this.vehicleDAO = new VehicleDAOImpl();
    }    
    
    /**
     * Retrieves a trip schedule from the database by Trip Schedule ID.
     * Executes a SELECT query on the vw_trip_schedules view and returns the record that
     * matches the Trip Schedule ID.
     * 
     * @param scheduleID The ID of the trip schedule to retrieve
     * @return TripScheduleDTO object containing the trip schedule data for the matching record
     *          or null if no matching record is found
     * @throws SQLException 
     */
    @Override
    public TripScheduleDTO getTripSchedByID(Integer scheduleID) throws SQLException {
        return executeOperation(new DatabaseOperation<TripScheduleDTO>() {
            @Override
            public TripScheduleDTO execute(Connection conn, PreparedStatement pstmt, ResultSet rs) throws SQLException {
                TripScheduleDTO tripSchedule = null;
                pstmt = conn.prepareStatement(
                        "SELECT trip_schedule_id, start_time, route_id, route_name, "
                        + "direction, vehicle_id, vehicle_type, vehicle_number "
                        + "FROM vw_trip_schedules WHERE trip_schedule_id = ?");
                pstmt.setInt(1, scheduleID);
                rs = pstmt.executeQuery();

                if (rs.next()) {
                    tripSchedule = extractTripScheduleFromResultSet(rs);
                }
                return tripSchedule;
            }
            
            @Override
            public String getDescription() {
                return "getTripSchedByID";
            }
        });
    }
    
    /**
     * Retrieves all trip schedules from the database.
     * Executes a SELECT query on the vw_trip_schedules view and maps the result set
     * to a list of TripScheduleDTO objects.
     * 
     * @return ArrayList of TripScheduleDTO objects containing all the trip schedule data.
     *          If no matching records are found, returns an empty list
     * @throws SQLException 
     */
    @Override
    public List<TripScheduleDTO> getAllTripScheds() throws SQLException {
        return executeOperation(new DatabaseOperation<List<TripScheduleDTO>>() {
            @Override
            public List<TripScheduleDTO> execute(Connection conn, PreparedStatement pstmt, ResultSet rs) throws SQLException {
                List<TripScheduleDTO> tripSchedules = new ArrayList<>();
            
                pstmt = conn.prepareStatement(
                    "SELECT trip_schedule_id, start_time, route_id, route_name, "
                    + "direction, vehicle_id, vehicle_type, vehicle_number "
                    + "FROM vw_trip_schedules ORDER BY start_time, route_name, direction");
            
                rs = pstmt.executeQuery();
            
                while (rs.next()) {
                    TripScheduleDTO tripSchedule = extractTripScheduleFromResultSet(rs);
                    tripSchedules.add(tripSchedule);
                }
            
                return tripSchedules;
            }
        
            @Override
            public String getDescription() {
                return "getAllTripScheds";
            }
        });
    }

    /**
     * Retrieves trip schedules for a specific route from the database.
     * Executes a SELECT query on the vw_trip_schedules view and returns records that
     * match the specified route name.
     * 
     * @param routeName The name of the route to filter by
     * @return List of TripScheduleDTO objects containing trip schedule data for the matching route.
     *          If no matching records are found, returns an empty list
     * @throws SQLException 
     */
    @Override
    public List<TripScheduleDTO> getTripSchedsByRoute(String routeName) throws SQLException {
        return executeOperation(new DatabaseOperation<List<TripScheduleDTO>>() {
            @Override
            public List<TripScheduleDTO> execute(Connection conn, PreparedStatement pstmt, ResultSet rs) throws SQLException {
                List<TripScheduleDTO> tripSchedules = new ArrayList<>();
            
                pstmt = conn.prepareStatement(
                    "SELECT trip_schedule_id, start_time, route_id, route_name, "
                    + "direction, vehicle_id, vehicle_type, vehicle_number "
                    + "FROM vw_trip_schedules WHERE route_name = ? "
                    + "ORDER BY start_time, direction");
                
                pstmt.setString(1, routeName);
                rs = pstmt.executeQuery();
            
                while (rs.next()) {
                    TripScheduleDTO tripSchedule = extractTripScheduleFromResultSet(rs);
                    tripSchedules.add(tripSchedule);
                }
            
                return tripSchedules;
            }
        
            @Override
            public String getDescription() {
                return "getTripSchedsByRoute";
            }
        });
    }
    
    /**
     * Helper method to extract a TripScheduleDTO from a ResultSet row.
     * Creates and populates a TripScheduleDTO object based on the current row of the ResultSet.
     * 
     * @param rs The ResultSet containing trip schedule data
     * @return A populated TripScheduleDTO object
     * @throws SQLException If there is an error accessing the ResultSet
     */
    private TripScheduleDTO extractTripScheduleFromResultSet(ResultSet rs) throws SQLException {
        TripScheduleDTO.Builder builder = TripScheduleDTO.builder()
            .setTripScheduleId(rs.getInt("trip_schedule_id"))
            .setStartTime(rs.getTime("start_time"))
            .setRouteId(rs.getInt("route_id"))
            .setRouteName(rs.getString("route_name"))
            .setDirection(rs.getString("direction"));
        
        // Check if vehicle_id is not null before creating the VehicleDTO
        Integer vehicleId = rs.getInt("vehicle_id");
        if (!rs.wasNull()) {
            try {
                // Use VehicleDAO to get full vehicle details
                VehicleDTO vehicle = vehicleDAO.getVehicleByID(vehicleId);

                // If search did not return a vehicle, don't set the VehicleDTO
                if (vehicle != null) {
                    builder.setVehicle(vehicle);
                }
            } catch (SQLException e) {
                // If vehicle not found, log the error but continue with a partially populated DTO
                Logger.getLogger(TripScheduleDAOImpl.class.getName())
                      .log(Level.WARNING, "Error retrieving complete vehicle data for ID: " + vehicleId, e);
            }
        }
        return builder.build();
    }
}
