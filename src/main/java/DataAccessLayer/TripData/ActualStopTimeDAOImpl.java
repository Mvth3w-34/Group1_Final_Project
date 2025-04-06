/* filename: ActualStopTimeDAOImpl.java
 * date: Apr. 5th, 2025
 * authors: Stephanie Prystupa-Maule, John Tieu
 * course: CST8288 O.O.P. with Design Patterns - Lab Section 023 
 * professor: Samira Ouaaz
 * coursework: Final Project - Public Transit Management System
 */
package DataAccessLayer.TripData;

import DataAccessLayer.BaseDAOImpl;
import TransferObjects.ActualStopTimeDTO;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Implementation of the ActualStopTimeDAO interface that provides database operations
 * for managing records in the actual_stop_times table.
 * Extends BaseDAOImpl to leverage common database functionality.
 * Uses the VW_ACTUAL_STOP_TIMES view for simplified data retrieval.
 * 
 * @author Stephanie Prystupa-Maule
 * @version 1.0
 * @since 04/05/2025
 * @see BaseDAOImpl
 * @see ActualStopTimeDAO
 */
public class ActualStopTimeDAOImpl extends BaseDAOImpl implements ActualStopTimeDAO {
    
    private static final Logger logger = Logger.getLogger(ActualStopTimeDAOImpl.class.getName());
    
    /**
     * Default constructor.
     */
    public ActualStopTimeDAOImpl() {
        // No initialization needed
    }

    /**
     * Retrieves an actual stop time from the database by its ID.
     * Executes a SELECT query on the VW_ACTUAL_STOP_TIMES view and returns the record that
     * matches the actual stop time ID.
     * 
     * @param actualStopTimeId The ID of the actual stop time to retrieve
     * @return ActualStopTimeDTO object containing the actual stop time data for the matching record
     *         or null if no matching record is found
     * @throws SQLException if a database access error occurs
     */
    @Override
    public ActualStopTimeDTO getActualtopTimeByID(Integer actualStopTimeId) throws SQLException {
        return executeOperation(new DatabaseOperation<ActualStopTimeDTO>() {
            @Override
            public ActualStopTimeDTO execute(Connection conn, PreparedStatement pstmt, ResultSet rs) throws SQLException {
                ActualStopTimeDTO actualStopTime = null;
                
                // Use the view for data retrieval
                String sql = "SELECT * FROM VW_ACTUAL_STOP_TIMES WHERE id = ?";
                
                pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1, actualStopTimeId);
                rs = pstmt.executeQuery();

                if (rs.next()) {
                    // Extract the ActualStopTimeDTO from the ResultSet
                    actualStopTime = extractActualStopTimeFromResultSet(rs);
                }
                return actualStopTime;
            }

            @Override
            public String getDescription() {
                return "getActualStopTimeByID";
            }
        });
    }

    /**
     * Retrieves all actual stop times from the database.
     * Executes a SELECT query on the VW_ACTUAL_STOP_TIMES view and maps the result set
     * to a list of ActualStopTimeDTO objects.
     * 
     * @return List of ActualStopTimeDTO objects containing all the actual stop time data.
     *         If no matching records are found, returns an empty list
     * @throws SQLException if a database access error occurs
     */
    @Override
    public List<ActualStopTimeDTO> getAllActualStopTimes() throws SQLException {
        return executeOperation(new DatabaseOperation<List<ActualStopTimeDTO>>() {
            @Override
            public List<ActualStopTimeDTO> execute(Connection conn, PreparedStatement pstmt, ResultSet rs) throws SQLException {
                List<ActualStopTimeDTO> actualStopTimes = new ArrayList<>();
                
                // Use the view with order by clause
                String sql = "SELECT * FROM VW_ACTUAL_STOP_TIMES ORDER BY actual_trip_id, stop_sequence";
                
                pstmt = conn.prepareStatement(sql);
                rs = pstmt.executeQuery();
                
                while (rs.next()) {
                    // Extract the ActualStopTimeDTO from the ResultSet
                    ActualStopTimeDTO actualStopTime = extractActualStopTimeFromResultSet(rs);
                    actualStopTimes.add(actualStopTime);
                }
                
                return actualStopTimes;
            }
            
            @Override
            public String getDescription() {
                return "getAllActualStopTimes";
            }
        });
    }

    /**
     * Adds a new actual stop time using the stored procedure.
     * Converts DTO to procedure parameters.
     * 
     * @param actualStopTime The ActualStopTimeDTO to be added
     * @throws SQLException If there is an error accessing the database
     */
    @Override
    public void createActualStopTime(ActualStopTimeDTO actualStopTime) throws SQLException {
        if (actualStopTime == null) {
            throw new IllegalArgumentException("ActualStopTimeDTO cannot be null");
        }

        // Extract the required parameters from the DTO
        Integer actualTripId = actualStopTime.getActualTripId();
        Integer seqStopId = actualStopTime.getSeqStopId();
        Time actualArrival = actualStopTime.getActualArrival();
        Time actualDeparture = actualStopTime.getActualDeparture();

        // Validate required parameters
        if (actualTripId == null) {
            throw new IllegalArgumentException("Actual trip ID cannot be null");
        }
        if (seqStopId == null) {
            throw new IllegalArgumentException("Sequence stop ID cannot be null");
        }

        // Call the stored procedure using the extracted parameters
        executeInsertActualStopTimeProcedure(
            actualTripId,
            seqStopId,
            actualArrival,
            actualDeparture
        );
    }
    /**
     * Updates an actual stop time record in the database.
     * Executes an UPDATE query to the actual_stop_times table.
     * 
     * @param actualStopTime The ActualStopTimeDTO containing the data to be updated
     * @throws SQLException if a database access error occurs
     */
    @Override
    public void updateActualStopTime(ActualStopTimeDTO actualStopTime) throws SQLException {
        executeOperation(new DatabaseOperation<Void>() {
            @Override
            public Void execute(Connection conn, PreparedStatement pstmt, ResultSet rs) throws SQLException {
                pstmt = conn.prepareStatement(
                        "UPDATE actual_stop_times SET actual_trip_id = ?, seq_stop_id = ?, " +
                        "scheduled_arrival = ?, scheduled_departure = ?, " +
                        "actual_arrival = ?, actual_departure = ?, " +
                        "arrival_variance = ?, departure_variance = ? " +
                        "WHERE id = ?");
                
                pstmt.setInt(1, actualStopTime.getActualTripId());
                pstmt.setInt(2, actualStopTime.getSeqStopId());
                
                // Handle nullable Time fields
                setTimeOrNull(pstmt, 3, actualStopTime.getScheduledArrival());
                setTimeOrNull(pstmt, 4, actualStopTime.getScheduledDeparture());
                setTimeOrNull(pstmt, 5, actualStopTime.getActualArrival());
                setTimeOrNull(pstmt, 6, actualStopTime.getActualDeparture());
                
                // Handle variance fields
                setIntOrNull(pstmt, 7, actualStopTime.getArrivalVariance());
                setIntOrNull(pstmt, 8, actualStopTime.getDepartureVariance());
                
                pstmt.setInt(9, actualStopTime.getActualStopTimeId());
                
                pstmt.executeUpdate();
                
                return null;
            }
            
            @Override
            public String getDescription() {
                return "updateActualStopTime";
            }
        });
    }

    /**
     * Deletes an actual stop time record from the database.
     * Executes a DELETE query to remove the specified actual stop time from the database.
     * 
     * @param actualStopTimeId The ID of the actual stop time to be deleted
     * @throws SQLException if a database access error occurs
     */
    @Override
    public void deleteActualStopTime(Integer actualStopTimeId) throws SQLException {
        executeOperation(new DatabaseOperation<Void>() {
            @Override
            public Void execute(Connection conn, PreparedStatement pstmt, ResultSet rs) throws SQLException {
                pstmt = conn.prepareStatement(
                        "DELETE FROM actual_stop_times WHERE id = ?");
                pstmt.setInt(1, actualStopTimeId);
                pstmt.executeUpdate();
                
                return null;
            }
            
            @Override
            public String getDescription() {
                return "deleteActualStopTime";
            }
        });
    }

    /**
     * Executes the INSERT_ACTUAL_STOP_TIME stored procedure.
     * 
     * @param actualTripId The ID of the actual trip this stop time belongs to
     * @param seqStopId The route stop sequence ID
     * @param actualArrival The actual arrival time at the stop
     * @param actualDeparture The actual departure time from the stop
     * @throws SQLException if a database access error occurs
     */
    @Override
    public void executeInsertActualStopTimeProcedure(
            Integer actualTripId,
            Integer seqStopId,
            Time actualArrival,
            Time actualDeparture) throws SQLException {

        Connection conn = null;
        CallableStatement cstmt = null;

        try {
            conn = getConnection();

            // Call the stored procedure
            cstmt = conn.prepareCall("{CALL INSERT_ACTUAL_STOP_TIME(?, ?, ?, ?)}");

            // Set input parameters
            cstmt.setInt(1, actualTripId);
            cstmt.setInt(2, seqStopId);

            if (actualArrival != null) {
                cstmt.setTime(3, actualArrival);
            } else {
                cstmt.setNull(3, Types.TIME);
            }

            if (actualDeparture != null) {
                cstmt.setTime(4, actualDeparture);
            } else {
                cstmt.setNull(4, Types.TIME);
            }

            // Execute the procedure
            cstmt.execute();

            // We don't need to retrieve or return the generated ID

        } catch (SQLException ex) {
            handleSQLException(ex, "executeInsertActualStopTimeProcedure", true);
        } finally {
            closeResources(null, cstmt, conn);
        }
    }    
    
    /**
     * Retrieves a list of actual stop times that match the specified filter criteria.
     * 
     * @param filters A map containing filter criteria where:
     *                - key: the field name to filter on (e.g., "actualTripId", "seqStopId"))
     *                - value: the value to match
     * @return A list of ActualStopTimeDTO objects matching the filter criteria, or an empty list if none found
     * @throws SQLException if a database access error occurs
     */
    @Override
    public List<ActualStopTimeDTO> findByFilter(Map<String, Object> filters) throws SQLException {
        return executeOperation(new DatabaseOperation<List<ActualStopTimeDTO>>() {
            @Override
            public List<ActualStopTimeDTO> execute(Connection conn, PreparedStatement pstmt, ResultSet rs) throws SQLException {
                List<ActualStopTimeDTO> actualStopTimes = new ArrayList<>();
                
                // Start with the base query using the view
                StringBuilder sql = new StringBuilder("SELECT * FROM VW_ACTUAL_STOP_TIMES WHERE 1=1");
                
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
                        
                        // Convert keys to database column names - all columns are present in the view
                        String columnName;
                        switch (key) {
                            case "actualStopTimeId":
                                columnName = "id";
                                break;
                            case "actualTripId":
                                columnName = "actual_trip_id";
                                break;
                            case "seqStopId":
                                columnName = "seq_stop_id";
                                break;
                            case "stopId":
                                columnName = "stop_id";
                                break;
                            case "stopSequence":
                                columnName = "stop_sequence";
                                break;
                            case "stopName":
                                columnName = "stop_name";
                                break;
                            case "isStation":
                                columnName = "is_station";
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
                
                // Add order by clause for consistent results
                sql.append(" ORDER BY actual_trip_id, stop_sequence");
                
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
                    } else if (value instanceof Boolean) {
                        pstmt.setBoolean(i + 1, (Boolean) value);
                    } else if (value instanceof Time) {
                        pstmt.setTime(i + 1, (Time) value);
                    } else {
                        // Default to string representation
                        pstmt.setString(i + 1, value.toString());
                    }
                }
                
                rs = pstmt.executeQuery();
                
                while (rs.next()) {
                    // Build the ActualStopTimeDTO using the Builder pattern
                    ActualStopTimeDTO actualStopTime = extractActualStopTimeFromResultSet(rs);
                    actualStopTimes.add(actualStopTime);
                }
                
                return actualStopTimes;
            }
            
            @Override
            public String getDescription() {
                return "findByFilter";
            }
        });
    }
    
    /**
     * Helper method to extract an ActualStopTimeDTO from a ResultSet row.
     * Creates and populates an ActualStopTimeDTO object based on the current row of the ResultSet.
     * 
     * @param rs The ResultSet containing actual stop time data
     * @return A populated ActualStopTimeDTO object
     * @throws SQLException If there is an error accessing the ResultSet
     */
    private ActualStopTimeDTO extractActualStopTimeFromResultSet(ResultSet rs) throws SQLException {
        ActualStopTimeDTO.Builder builder = ActualStopTimeDTO.builder()
                .setActualStopTimeId(rs.getInt("id"))
                .setActualTripId(rs.getInt("actual_trip_id"))
                .setSeqStopId(rs.getInt("seq_stop_id"));
        
        // Handle stop information
        Integer stopId = rs.getInt("stop_id");
        if (!rs.wasNull()) {
            builder.setStopId(stopId)
                   .setStopName(rs.getString("stop_name"))
                   .setIsStation(rs.getBoolean("is_station"))
                   .setStopSequence(rs.getInt("stop_sequence"));
        }
        
        // Handle time fields - these can all be null
        Time scheduledArrival = rs.getTime("scheduled_arrival");
        if (!rs.wasNull()) {
            builder.setScheduledArrival(scheduledArrival);
        }
        
        Time scheduledDeparture = rs.getTime("scheduled_departure");
        if (!rs.wasNull()) {
            builder.setScheduledDeparture(scheduledDeparture);
        }
        
        Time actualArrival = rs.getTime("actual_arrival");
        if (!rs.wasNull()) {
            builder.setActualArrival(actualArrival);
        }
        
        Time actualDeparture = rs.getTime("actual_departure");
        if (!rs.wasNull()) {
            builder.setActualDeparture(actualDeparture);
        }
        
        // Handle variance fields
        Integer arrivalVariance = rs.getInt("arrival_variance");
        if (!rs.wasNull()) {
            builder.setArrivalVariance(arrivalVariance);
        }
        
        Integer departureVariance = rs.getInt("departure_variance");
        if (!rs.wasNull()) {
            builder.setDepartureVariance(departureVariance);
        }
        
        return builder.build();
    }
    
    /**
     * Helper method to set a Time value or NULL in a PreparedStatement.
     * 
     * @param pstmt The PreparedStatement
     * @param parameterIndex The parameter index
     * @param value The Time value to set
     * @throws SQLException if a database access error occurs
     */
    private void setTimeOrNull(PreparedStatement pstmt, int parameterIndex, Time value) throws SQLException {
        if (value != null) {
            pstmt.setTime(parameterIndex, value);
        } else {
            pstmt.setNull(parameterIndex, Types.TIME);
        }
    }
    
    /**
     * Helper method to set an Integer value or NULL in a PreparedStatement.
     * 
     * @param pstmt The PreparedStatement
     * @param parameterIndex The parameter index
     * @param value The Integer value to set
     * @throws SQLException if a database access error occurs
     */
    private void setIntOrNull(PreparedStatement pstmt, int parameterIndex, Integer value) throws SQLException {
        if (value != null) {
            pstmt.setInt(parameterIndex, value);
        } else {
            pstmt.setNull(parameterIndex, Types.INTEGER);
        }
    }
    
    /**
     * Helper method to get an Integer value from a ResultSet that might be NULL.
     * 
     * @param rs The ResultSet
     * @param columnName The column name
     * @return The Integer value or null if the column is NULL
     * @throws SQLException if a database access error occurs
     */
    private Integer getIntOrNull(ResultSet rs, String columnName) throws SQLException {
        int value = rs.getInt(columnName);
        return rs.wasNull() ? null : value;
    }
}
