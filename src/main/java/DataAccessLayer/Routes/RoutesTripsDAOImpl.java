/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DataAccessLayer.Routes;

import DataAccessLayer.TransitDataSource;
import TransferObjects.*;
import java.sql.*;
import java.util.*;

/**
  The implementation of the Route Trips DAO
 * @author John Tieu
 */
public class RoutesTripsDAOImpl implements RoutesTripsDAO {

    private final TransitDataSource instance;
    /**
     * Gets the data source instance
     * @throws SQLException 
     */
    public RoutesTripsDAOImpl() throws SQLException {
        instance = TransitDataSource.getDataInstance();
    }
    /**
     * Gets a list of the vehicle's timestamps from each station
     * @param vehicleID
     * @return The list of timestamps
     * @throws SQLException 
     */
    @Override
    public List<VehicleStationTimetable> getAllVehicleStationTimes(int vehicleID) throws SQLException {
        String query = "SELECT VEHICLES.VEHICLE_ID, STOPS.STOP_NAME, STOPS.IS_STATION, SCHEDULED_STOP_TIMES.ARRIVAL, SCHEDULED_STOP_TIMES.DEPARTURE "
                + "FROM SCHEDULED_STOP_TIMES "
                + "INNER JOIN TRIP_SCHEDULES ON TRIP_SCHEDULES.ID = SCHEDULED_STOP_TIMES.TRIP_SCHEDULE_ID "
                + "INNER JOIN VEHICLES ON VEHICLES.CURRENT_ASSIGNED_TRIP = TRIP_SCHEDULES.ID "
                + "INNER JOIN ROUTES ON ROUTES.ID = TRIP_SCHEDULES.ROUTE_ID "
                + "INNER JOIN ROUTE_STOPS ON route_stops.ID = SCHEDULED_STOP_TIMES.SEQ_STOP_ID " 
                + "AND route_stops.ROUTE_ID = ROUTES.ID "
                + "INNER JOIN STOPS ON STOPS.ID = ROUTE_STOPS.STOP_ID WHERE VEHICLES.VEHICLE_ID = ?";
        List<VehicleStationTimetable> vehicleTimetableList = new ArrayList<>();
        ResultSet set;
        VehicleStationTimetable vehicleTimetable;
        try (PreparedStatement statement = instance.getConnection().prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY))
        {
            statement.setInt(1, vehicleID);
            set = statement.executeQuery();
            if (set.next()) {
                // Reset the pointer location to prevent row skips before printing table
                set.beforeFirst();
                while (set.next()) {
//                    Timestamp arrival = set.getTimestamp("ARRIVAL").;
                    vehicleTimetable = new VehicleStationTimetable();
                    vehicleTimetable.setVehicleId(set.getInt("VEHICLE_ID"));
                    vehicleTimetable.setStationName(set.getString("STOP_NAME"));
                    vehicleTimetable.setStation(set.getBoolean("IS_STATION"));
                    vehicleTimetable.setArrivalTime(set.getTimestamp("ARRIVAL"));
                    vehicleTimetable.setDepartureTime(set.getTimestamp("DEPARTURE"));
                    vehicleTimetableList.add(vehicleTimetable);
                }
            }
        }
        return vehicleTimetableList;
    }
    /**
     * Get list of headers from timestamp table
     * @return Header data
     * @throws SQLException 
     */
    @Override
    public List<String> getHeaders() throws SQLException {
        String query = "SELECT * FROM VEHICLE_TIMETABLES";
        List<String> headers = new ArrayList<>();
        ResultSet set;
        ResultSetMetaData sqlHeaders;
        try (
            PreparedStatement statement = instance.getConnection().prepareStatement(query);
        ) {
            set = statement.executeQuery();
            sqlHeaders = set.getMetaData();
            for (int col = 1; col <= sqlHeaders.getColumnCount(); col++) {
                headers.add(sqlHeaders.getColumnName(col));
            }
        }
        return headers;
    }
    /**
     * Get a list of route IDs
     * @return
     * @throws SQLException 
     */
    @Override
    public List<Integer> getRoutes() throws SQLException {
        List<Integer> routesList = new ArrayList<>();
        String query = "SELECT ID FROM TRIP_SCHEDULES";
        ResultSet set;
        try (PreparedStatement statement = instance.getConnection().prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY))
        {
            set = statement.executeQuery();
            if (set.next()) {
                // Reset the pointer location to prevent row skips before printing table
                set.beforeFirst();
                while (set.next()) {
//                    Timestamp arrival = set.getTimestamp("ARRIVAL").;
                    routesList.add(set.getInt("ID"));
                }
            }
        }
        return routesList;
    }
    
}
