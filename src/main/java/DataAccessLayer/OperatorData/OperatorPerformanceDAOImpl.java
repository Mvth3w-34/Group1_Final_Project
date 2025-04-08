/* filename: OperatorPerformanceDAOImpl.java
 * date: Apr. 6th, 2025
 * authors: Stephanie Prystupa-Maule
 * course: CST8288 O.O.P. with Design Patterns - Lab Section 023 
 * professor: Samira Ouaaz
 * coursework: Final Project - Public Transit Management System
 */
package DataAccessLayer.OperatorData;

import TransferObjects.OperatorPerformanceDTO;
import DataAccessLayer.BaseDAOImpl;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
// No need for additional logging imports as BaseDAOImpl handles logging

/**
 * Implementation of the OperatorPerformanceDAO interface that provides database operations
 * for retrieving operator performance data from the vw_operator_performance view.
 * Extends BaseDAOImpl to leverage common database functionality.
 * 
 * @author Stephanie Prystupa-Maule
 * @version 1.0
 * @since 04/06/2025
 * @see BaseDAOImpl
 * @see OperatorPerformanceDAO
 */
public class OperatorPerformanceDAOImpl extends BaseDAOImpl implements OperatorPerformanceDAO {

    // Logger is handled by BaseDAOImpl
    
    /**
     * Retrieves an operator's performance metrics from the database by Operator ID.
     * Executes a SELECT query on the vw_operator_performance view and returns the record that
     * matches the Operator ID.
     * 
     * @param operatorId The ID of the operator to retrieve performance for
     * @return OperatorPerformanceDTO object containing the performance data for the matching operator
     *          or null if no matching record is found
     * @throws SQLException if a database access error occurs
     */
    @Override
    public OperatorPerformanceDTO getOperatorPerformanceByID(Integer operatorId) throws SQLException {
        return executeOperation(new DatabaseOperation<OperatorPerformanceDTO>() {
            @Override
            public OperatorPerformanceDTO execute(Connection conn, PreparedStatement pstmt, ResultSet rs) throws SQLException {
                OperatorPerformanceDTO operatorPerformance = null;
                pstmt = conn.prepareStatement(
                        "SELECT operator_id, operator_name, total_trips, avg_combined_variance, " +
                        "pct_ontime_arrivals, pct_ontime_departures, " +
                        "pct_early_arrivals, avg_early_arrival_time, " +
                        "pct_early_departures, avg_early_departure_time, " +
                        "pct_late_arrivals, avg_late_arrival_time, " +
                        "pct_late_departures, avg_late_departure_time " +
                        "FROM vw_operator_performance WHERE operator_id = ?");
                pstmt.setInt(1, operatorId);
                rs = pstmt.executeQuery();

                if (rs.next()) {
                    operatorPerformance = extractOperatorPerformanceFromResultSet(rs);
                }
                return operatorPerformance;
            }
            
            @Override
            public String getDescription() {
                return "getOperatorPerformanceByID";
            }
        });
    }

    /**
     * Retrieves all operator performance records from the database.
     * Executes a SELECT query on the vw_operator_performance view and maps the result set
     * to a list of OperatorPerformanceDTO objects.
     * 
     * @return ArrayList of OperatorPerformanceDTO objects containing all operator performance data.
     *          If no records are found, returns an empty list
     * @throws SQLException if a database access error occurs
     */
    @Override
    public List<OperatorPerformanceDTO> getAllOperatorPerformance() throws SQLException {
        return executeOperation(new DatabaseOperation<List<OperatorPerformanceDTO>>() {
            @Override
            public List<OperatorPerformanceDTO> execute(Connection conn, PreparedStatement pstmt, ResultSet rs) throws SQLException {
                List<OperatorPerformanceDTO> operatorPerformances = new ArrayList<>();
            
                pstmt = conn.prepareStatement(
                        "SELECT operator_id, operator_name, total_trips, avg_combined_variance, " +
                        "pct_ontime_arrivals, pct_ontime_departures, " +
                        "pct_early_arrivals, avg_early_arrival_time, " +
                        "pct_early_departures, avg_early_departure_time, " +
                        "pct_late_arrivals, avg_late_arrival_time, " +
                        "pct_late_departures, avg_late_departure_time " +
                        "FROM vw_operator_performance " +
                        "ORDER BY operator_name");
                
                rs = pstmt.executeQuery();
            
                while (rs.next()) {
                    OperatorPerformanceDTO operatorPerformance = extractOperatorPerformanceFromResultSet(rs);
                    operatorPerformances.add(operatorPerformance);
                }
            
                return operatorPerformances;
            }
        
            @Override
            public String getDescription() {
                return "getAllOperatorPerformance";
            }
        });
    }
    
    /**
     * Helper method to extract an OperatorPerformanceDTO from a ResultSet row.
     * Creates and populates an OperatorPerformanceDTO object based on the current row of the ResultSet.
     * 
     * @param rs The ResultSet containing operator performance data
     * @return A populated OperatorPerformanceDTO object
     * @throws SQLException If there is an error accessing the ResultSet
     */
    private OperatorPerformanceDTO extractOperatorPerformanceFromResultSet(ResultSet rs) throws SQLException {
        return OperatorPerformanceDTO.builder()
            .setOperatorId(rs.getInt("operator_id"))
            .setOperatorName(rs.getString("operator_name"))
            .setTotalTrips(rs.getInt("total_trips"))
            .setAvgCombinedVariance(rs.getDouble("avg_combined_variance"))
            .setPctOntimeArrivals(rs.getDouble("pct_ontime_arrivals"))
            .setPctOntimeDepartures(rs.getDouble("pct_ontime_departures"))
            .setPctEarlyArrivals(rs.getDouble("pct_early_arrivals"))
            .setAvgEarlyArrivalTime(rs.getDouble("avg_early_arrival_time"))
            .setPctEarlyDepartures(rs.getDouble("pct_early_departures"))
            .setAvgEarlyDepartureTime(rs.getDouble("avg_early_departure_time"))
            .setPctLateArrivals(rs.getDouble("pct_late_arrivals"))
            .setAvgLateArrivalTime(rs.getDouble("avg_late_arrival_time"))
            .setPctLateDepartures(rs.getDouble("pct_late_departures"))
            .setAvgLateDepartureTime(rs.getDouble("avg_late_departure_time"))
            .build();
    }
}
