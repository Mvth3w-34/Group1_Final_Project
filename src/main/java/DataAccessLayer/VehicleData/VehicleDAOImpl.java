package DataAccessLayer.VehicleData;

import DataAccessLayer.TransitDataSource;
import TransferObjects.VehicleDTO;
import TransferObjects.LoginDTO;
import java.util.*;
import java.sql.*;

/**
 * A list of database functions, each with errors that signal when to display
 * a error message on the HTML webpage
 * @author John Tieu
 */
public class VehicleDAOImpl implements VehicleDAO {
    
    private final TransitDataSource instance;
    
    /**
     * Establishes an initial login to the DB.
     * @throws java.sql.SQLException If bad login credentials are provided
     */
    public VehicleDAOImpl() throws SQLException {
        instance = TransitDataSource.getDataInstance();
        // Test to see if login is successful upon login
        instance.getConnection();
    }
    
    /**
     * Obtains a list of all the valid vehicles in the database
     * @return A list containing valid fleet vehicles
     * @throws SQLException on invalid login credentials
     */
    @Override
    public List<VehicleDTO> getAllVehicles() throws SQLException {
        List<VehicleDTO> vehiclesList = new ArrayList<>();
        String query = "SELECT * FROM VEHICLES";
        try {
            vehiclesList = getVehiclesQuery(query);
        } catch (SQLException e) {
            throw e;
        }
        return vehiclesList;
    }

    private List<VehicleDTO> getVehiclesQuery(String query) throws SQLException {
        List<VehicleDTO> vehiclesList = new ArrayList<>();
        ResultSet set;
        VehicleDTO vehicle;
        
        try (PreparedStatement statement = instance.getConnection().prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY))
        {
            set = statement.executeQuery();
            if (set.next()) {
                // Reset the pointer location to prevent row skips before printing table
                set.beforeFirst();
                while (set.next()) {
                    // Creates a vehicle object using a builder with inputs from Vehicles table
                    try {
                        // Add the vehicle from the DB to the list to be printed to HTML
                        vehicle = VehicleDTO.setupVehicle()
                                .setID(set.getInt("VEHICLE_ID"))
                                .setVehicleType(VehicleDTO.VehicleType.valueOf(set.getString("VEHICLE_TYPE")))
                                .setVehicleNum(set.getString("VEHICLE_NUMBER"))
                                .setFuelType(set.getString("FUEL_TYPE"))
                                .setConsumptionRate(set.getFloat("FUEL_CONSUMPTION_RATE"))
                                .setMaxPassenger(set.getInt("MAXIMUM_PASSENGERS"))
                                .setRoute(set.getString("CURRENT_ROUTE"))
                                .registerVehicle();
                        vehiclesList.add(vehicle);
                    } catch (IllegalArgumentException e) {
                        // Do nothing, which will skip the records with a bad vehicle type
                        // In theory, the DB is set up so that vehicle type is not null
                    }
                }
            }
        } catch (SQLException e) {
            throw e;
        }
        return vehiclesList;
    }
    /**
     * Registers a new vehicle to the transit fleet database
     * @param vehicle The new vehicle to be added
     * @throws SQLException on invalid login credentials
     */
    @Override
    public void registerVehicle(VehicleDTO vehicle) throws SQLException {
        String insertQuery = "INSERT INTO VEHICLES (VEHICLE_TYPE, VEHICLE_NUMBER, FUEL_TYPE, "
                + "FUEL_CONSUMPTION_RATE, MAXIMUM_PASSENGERS, CURRENT_ROUTE)"
                + " VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = instance.getConnection().prepareStatement(insertQuery, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            statement.setString(1, vehicle.getVehicleType().name());
            statement.setString(2, vehicle.getVIN());
            statement.setString(3, vehicle.getFuelType());
            statement.setFloat(4, vehicle.getFuelRate());
            statement.setInt(5, vehicle.getMaxPassengers());
            if (vehicle.getRoute() == null) {
                statement.setNull(6, java.sql.Types.NULL);
            } else {
                statement.setString(6, vehicle.getRoute());
            }
            statement.executeUpdate();
        }
    }

    /**
     * Updates an existing vehicle's route and fuel type, assuming that the
     * fuel consumption rate is constant
     * @param newFuel The new fuel type for the vehicle
     * @param newRoute The vehicle's new route
     * @param vehicle The selected vehicle
     * @throws SQLException on invalid login credentials
     */
    @Override
    public void updateVehicle(String newFuel, String newRoute, VehicleDTO vehicle) throws SQLException {
        String updateQuery = "UPDATE VEHICLES SET FUEL_TYPE = ?, CURRENT_ROUTE = ? WHERE VEHICLE_ID = ?";
        try (PreparedStatement statement = instance.getConnection().prepareStatement(updateQuery, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            statement.setString(1, newFuel);
            statement.setString(2, newRoute);
            statement.setInt(3, vehicle.getVehicleID());
            statement.executeUpdate();
        }
    }
    /**
     * Removes a vehicle from the transit fleet
     * @param vehicleID The vehicle's ID to be removed
     * @throws SQLException on invalid login credentials
     */
    @Override
    public void removeVehicle(int vehicleID) throws SQLException {
        String deleteQuery = "DELETE FROM VEHICLES WHERE VEHICLE_ID = ?";
        try (PreparedStatement statement = instance.getConnection().prepareStatement(deleteQuery, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            statement.setInt(1, vehicleID);
            statement.executeUpdate();
        }
    }

    @Override
    public void closeConnection() {
        instance.closeConnection();
    }
    /**
     * Returns a lit of headers metadata from vehicles table
     * @return
     * @throws SQLException 
     */
    @Override
    public List<String> getVehicleHeaders() throws SQLException {
        List<String> vHeaders = new ArrayList<>();
        ResultSet set;
        ResultSetMetaData sqlHeaders;
        try (
            PreparedStatement statement = instance.getConnection().prepareStatement(
                "SELECT * FROM VEHICLES", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY
            );
        ) {
            set = statement.executeQuery();
            sqlHeaders = set.getMetaData();
            for (int col = 1; col <= sqlHeaders.getColumnCount(); col++) {
                vHeaders.add(sqlHeaders.getColumnName(col));
            }
        }
        return vHeaders;
    }
}
