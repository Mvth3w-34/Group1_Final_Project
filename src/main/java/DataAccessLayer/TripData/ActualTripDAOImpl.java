/* filename: ActualTripDAOImpl.java
 * date: Apr. 5th, 2025
 * authors: Stephanie Prystupa-Maule, John Tieu
 * course: CST8288 O.O.P. with Design Patterns - Lab Section 023 
 * professor: Samira Ouaaz
 * coursework: Final Project - Public Transit Management System
 */
package DataAccessLayer.TripData;

import DataAccessLayer.BaseDAOImpl;
import DataAccessLayer.VehicleData.VehicleDAO;
import DataAccessLayer.VehicleData.VehicleDAOImpl;
import TransferObjects.ActualTripDTO;
import TransferObjects.VehicleDTO;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Implementation of the ActualTripDAO interface that provides database operations
 * for managing records in the actual_trips table.
 * Extends BaseDAOImpl to leverage common database functionality.
 * 
 * @author Stephanie Prystupa-Maule
 * @version 1.0
 * @since 04/05/2025
 * @see BaseDAOImpl
 * @see ActualTripDAO
 */
public class ActualTripDAOImpl extends BaseDAOImpl implements ActualTripDAO {
    
    private static final Logger logger = Logger.getLogger(ActualTripDAOImpl.class.getName());
    private final VehicleDAO vehicleDAO;
    
    /**
     * Default constructor that initializes a VehicleDAOImpl for vehicle data retrieval.
     */
    public ActualTripDAOImpl() {
        this.vehicleDAO = new VehicleDAOImpl();
    }
    
    /**
     * Constructor with injected VehicleDAO for testing/dependency injection.
     * 
     * @param vehicleDAO The VehicleDAO implementation to use
     */
    public ActualTripDAOImpl(VehicleDAO vehicleDAO) {
        this.vehicleDAO = vehicleDAO;
    }

    /**
     * Retrieves an actual trip from the database by its ID.
     * Executes a SELECT query on the actual_trips table and returns the record that
     * matches the actual trip ID.
     * 
     * @param actualTripId The ID of the actual trip to retrieve
     * @return ActualTripDTO object containing the actual trip data for the matching record
     *         or null if no matching record is found
     * @throws SQLException if a database access error occurs
     */
    @Override
    public ActualTripDTO getActualTripByID(Integer actualTripId) throws SQLException {
        return executeOperation(new DatabaseOperation<ActualTripDTO>() {
            @Override
            public ActualTripDTO execute(Connection conn, PreparedStatement pstmt, ResultSet rs) throws SQLException {
                ActualTripDTO actualTrip = null;
                pstmt = conn.prepareStatement(
                        "SELECT id, scheduled_trip_id, operator_id, vehicle_id, trip_date, trip_status " +
                        "FROM actual_trips WHERE id = ?");
                pstmt.setInt(1, actualTripId);
                rs = pstmt.executeQuery();

                if (rs.next()) {
                    // Get the vehicle data using VehicleDAO
                    Integer vehicleId = rs.getInt("vehicle_id");
                    VehicleDTO vehicle = vehicleDAO.getVehicleByID(vehicleId);
                    
                    // Build the ActualTripDTO using the Builder pattern
                    actualTrip = ActualTripDTO.builder()
                            .setActualTripId(rs.getInt("id"))
                            .setScheduledTripId(rs.getInt("scheduled_trip_id"))
                            .setOperatorId(rs.getInt("operator_id"))
                            .setVehicle(vehicle)
                            .setTripDate(rs.getDate("trip_date"))
                            .setTripStatus(rs.getString("trip_status"))
                            .build();
                }
                return actualTrip;
            }

            @Override
            public String getDescription() {
                return "getActualTripByID";
            }
        });
    }

    /**
     * Retrieves all actual trips from the database.
     * Executes a SELECT query on the actual_trips table and maps the result set
     * to a list of ActualTripDTO objects.
     * 
     * @return List of ActualTripDTO objects containing all the actual trip data.
     *         If no matching records are found, returns an empty list
     * @throws SQLException if a database access error occurs
     */
    @Override
    public List<ActualTripDTO> getAllActualTrips() throws SQLException {
        return executeOperation(new DatabaseOperation<List<ActualTripDTO>>() {
            @Override
            public List<ActualTripDTO> execute(Connection conn, PreparedStatement pstmt, ResultSet rs) throws SQLException {
                List<ActualTripDTO> actualTrips = new ArrayList<>();
                
                pstmt = conn.prepareStatement(
                        "SELECT id, scheduled_trip_id, operator_id, vehicle_id, trip_date, trip_status " +
                        "FROM actual_trips ORDER BY trip_date DESC, id");
                
                rs = pstmt.executeQuery();
                
                while (rs.next()) {
                    // Get the vehicle data using VehicleDAO
                    Integer vehicleId = rs.getInt("vehicle_id");
                    VehicleDTO vehicle = vehicleDAO.getVehicleByID(vehicleId);
                    
                    // Build the ActualTripDTO using the Builder pattern
                    ActualTripDTO actualTrip = ActualTripDTO.builder()
                            .setActualTripId(rs.getInt("id"))
                            .setScheduledTripId(rs.getInt("scheduled_trip_id"))
                            .setOperatorId(rs.getInt("operator_id"))
                            .setVehicle(vehicle)
                            .setTripDate(rs.getDate("trip_date"))
                            .setTripStatus(rs.getString("trip_status"))
                            .build();
                    
                    actualTrips.add(actualTrip);
                }
                
                return actualTrips;
            }
            
            @Override
            public String getDescription() {
                return "getAllActualTrips";
            }
        });
    }

    /**
     * Adds a new actual trip using the stored procedure.
     * Converts DTO to procedure parameters.
     * 
     * @param actualTrip The ActualTripDTO object containing the data to be inserted
     * @throws SQLException if a database access error occurs
     */
    @Override
    public void createActualTrip(ActualTripDTO actualTrip) throws SQLException {
        if (actualTrip == null) {
            throw new IllegalArgumentException("ActualTripDTO cannot be null");
        }

        // Extract the required parameters from the DTO
        Integer scheduledTripId = actualTrip.getTripScheduleId();
        Integer operatorId = actualTrip.getOperatorId();
        Integer vehicleId = actualTrip.getVehicleId();
        Date tripDate = actualTrip.getTripDate();
        String tripStatus = actualTrip.getTripStatus();

        // Validate required parameters
        if (scheduledTripId == null) {
            throw new IllegalArgumentException("Scheduled trip ID cannot be null");
        }
        if (operatorId == null) {
            throw new IllegalArgumentException("Operator ID cannot be null");
        }
        if (vehicleId == null) {
            throw new IllegalArgumentException("Vehicle ID cannot be null");
        }
        if (tripDate == null) {
            throw new IllegalArgumentException("Trip date cannot be null");
        }
        if (tripStatus == null || tripStatus.isEmpty()) {
            throw new IllegalArgumentException("Trip status cannot be null or empty");
        }

        // Call the stored procedure using the extracted parameters
        executeInsertActualTripProcedure(
            scheduledTripId,
            operatorId,
            vehicleId,
            tripDate,
            tripStatus
        );
    }

    /**
     * Updates an actual trip record in the database.
     * Executes an UPDATE query to the actual_trips table.
     * 
     * @param actualTrip The ActualTripDTO containing the data to be updated
     * @throws SQLException if a database access error occurs
     */
    @Override
    public void updateActualTrip(ActualTripDTO actualTrip) throws SQLException {
        executeOperation(new DatabaseOperation<Void>() {
            @Override
            public Void execute(Connection conn, PreparedStatement pstmt, ResultSet rs) throws SQLException {
                pstmt = conn.prepareStatement(
                        "UPDATE actual_trips SET scheduled_trip_id = ?, operator_id = ?, " +
                        "vehicle_id = ?, trip_date = ?, trip_status = ? WHERE id = ?");
                
                pstmt.setInt(1, actualTrip.getTripScheduleId());
                pstmt.setInt(2, actualTrip.getOperatorId());
                pstmt.setInt(3, actualTrip.getVehicleId());
                pstmt.setDate(4, actualTrip.getTripDate());
                pstmt.setString(5, actualTrip.getTripStatus());
                pstmt.setInt(6, actualTrip.getActualTripId());
                
                pstmt.executeUpdate();
                
                return null;
            }
            
            @Override
            public String getDescription() {
                return "updateActualTrip";
            }
        });
    }

    /**
     * Deletes an actual trip record from the database.
     * Executes a DELETE query to remove the specified actual trip from the database.
     * 
     * @param actualTripID The ID of the actual trip to be deleted
     * @throws SQLException if a database access error occurs
     */
    @Override
    public void deleteActualTrip(Integer actualTripID) throws SQLException {
        executeOperation(new DatabaseOperation<Void>() {
            @Override
            public Void execute(Connection conn, PreparedStatement pstmt, ResultSet rs) throws SQLException {
                pstmt = conn.prepareStatement(
                        "DELETE FROM actual_trips WHERE id = ?");
                pstmt.setInt(1, actualTripID);
                pstmt.executeUpdate();
                
                return null;
            }
            
            @Override
            public String getDescription() {
                return "deleteActualTrip";
            }
        });
    }

   /**
     * Executes the INSERT_ACTUAL_TRIP stored procedure.
     * 
     * @param scheduledTripId The ID of the scheduled trip
     * @param operatorId The ID of the operator
     * @param vehicleId The ID of the vehicle
     * @param tripDate The date of the trip
     * @param tripStatus The status of the trip
     * @throws SQLException if a database access error occurs
     */
    @Override
    public void executeInsertActualTripProcedure(
            Integer scheduledTripId, 
            Integer operatorId, 
            Integer vehicleId, 
            Date tripDate, 
            String tripStatus) throws SQLException {

        Connection conn = null;
        CallableStatement cstmt = null;

        try {
            conn = getConnection();

            // Call the stored procedure
            cstmt = conn.prepareCall("{CALL INSERT_ACTUAL_TRIP(?, ?, ?, ?, ?)}");

            // Set input parameters
            cstmt.setInt(1, scheduledTripId);
            cstmt.setInt(2, operatorId);
            cstmt.setInt(3, vehicleId);
            cstmt.setDate(4, tripDate);
            cstmt.setString(5, tripStatus);

            // Execute the procedure
            cstmt.execute();

        } catch (SQLException ex) {
            handleSQLException(ex, "executeInsertActualTripProcedure", true);
        } finally {
            closeResources(null, cstmt, conn);
        }
    }  
    
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
    @Override
    public void generateTestTrip(
        Integer scheduledTripId,
        Integer operatorId,
        Integer vehicleId,
        Date tripDate,
        Integer minVariance,
        Integer maxVariance) throws SQLException {

        Connection conn = null;
        CallableStatement cstmt = null;

        try {
            conn = getConnection();

            // Call the stored procedure
            cstmt = conn.prepareCall("{CALL GENERATE_TEST_TRIP(?, ?, ?, ?, ?, ?)}");

            // Set input parameters
            cstmt.setInt(1, scheduledTripId);
            cstmt.setInt(2, operatorId);
            cstmt.setInt(3, vehicleId);
            cstmt.setDate(4, tripDate);
            cstmt.setInt(5, minVariance);
            cstmt.setInt(6, maxVariance);

            // Execute the procedure
            cstmt.execute();

        } catch (SQLException ex) {
            handleSQLException(ex, "generateTestTrip", true);
        } finally {
            closeResources(null, cstmt, conn);
        }
    }
    
    /**
     * Retrieves a list of actual trips that match the specified filter criteria.
     * 
     * @param filters A map containing filter criteria where:
     *                - key: the field name to filter on (e.g., "operatorId", "vehicleId", "tripDate", "tripStatus")
     *                - value: the value to match
     * @return A list of ActualTripDTO objects matching the filter criteria, or an empty list if none found
     * @throws SQLException if a database access error occurs
     */
    @Override
    public List<ActualTripDTO> findByFilter(Map<String, Object> filters) throws SQLException {
        return executeOperation(new DatabaseOperation<List<ActualTripDTO>>() {
            @Override
            public List<ActualTripDTO> execute(Connection conn, PreparedStatement pstmt, ResultSet rs) throws SQLException {
                List<ActualTripDTO> actualTrips = new ArrayList<>();
                
                // Build the SQL query dynamically based on filters
                StringBuilder sql = new StringBuilder(
                        "SELECT id, scheduled_trip_id, operator_id, vehicle_id, trip_date, trip_status " +
                        "FROM actual_trips WHERE 1=1");
                
                // Map to hold the parameter values in order
                List<Object> paramValues = new ArrayList<>();
                
                // Add filter conditions
                if (filters != null && !filters.isEmpty()) {
                    for (Map.Entry<String, Object> entry : filters.entrySet()) {
                        String key = entry.getKey();
                        Object value = entry.getValue();
                        
                        // Skip null values
                        if (value == null) {
                            continue;
                        }
                        
                        // Convert keys to database column names
                        String columnName;
                        switch (key) {
                            case "actualTripId":
                                columnName = "id";
                                break;
                            case "scheduledTripId":
                                columnName = "scheduled_trip_id";
                                break;
                            case "operatorId":
                                columnName = "operator_id";
                                break;
                            case "vehicleId":
                                columnName = "vehicle_id";
                                break;
                            case "tripDate":
                                columnName = "trip_date";
                                break;
                            case "tripStatus":
                                columnName = "trip_status";
                                break;
                            default:
                                // Skip unknown filter keys
                                logger.log(Level.WARNING, "Unknown filter key: {0}", key);
                                continue;
                        }
                        
                        sql.append(" AND ").append(columnName).append(" = ?");
                        paramValues.add(value);
                    }
                }
                
                // Add order by clause
                sql.append(" ORDER BY trip_date DESC, id");
                
                // Prepare the statement
                pstmt = conn.prepareStatement(sql.toString());
                
                // Set parameters
                for (int i = 0; i < paramValues.size(); i++) {
                    // Set the appropriate parameter type based on the value's class
                    Object value = paramValues.get(i);
                    if (value instanceof Integer) {
                        pstmt.setInt(i + 1, (Integer) value);
                    } else if (value instanceof String) {
                        pstmt.setString(i + 1, (String) value);
                    } else if (value instanceof Date) {
                        pstmt.setDate(i + 1, (Date) value);
                    } else {
                        // Default to string representation
                        pstmt.setString(i + 1, value.toString());
                    }
                }
                
                rs = pstmt.executeQuery();
                
                while (rs.next()) {
                    // Get the vehicle data using VehicleDAO
                    Integer vehicleId = rs.getInt("vehicle_id");
                    VehicleDTO vehicle = vehicleDAO.getVehicleByID(vehicleId);
                    
                    // Build the ActualTripDTO using the Builder pattern
                    ActualTripDTO actualTrip = ActualTripDTO.builder()
                            .setActualTripId(rs.getInt("id"))
                            .setScheduledTripId(rs.getInt("scheduled_trip_id"))
                            .setOperatorId(rs.getInt("operator_id"))
                            .setVehicle(vehicle)
                            .setTripDate(rs.getDate("trip_date"))
                            .setTripStatus(rs.getString("trip_status"))
                            .build();
                    
                    actualTrips.add(actualTrip);
                }
                
                return actualTrips;
            }
            
            @Override
            public String getDescription() {
                return "findByFilter";
            }
        });
    }
}
