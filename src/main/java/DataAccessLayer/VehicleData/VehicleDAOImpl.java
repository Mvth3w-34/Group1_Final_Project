/* filename: VehicleDAOImpl.java
 * date: Apr. 3rd, 2025
 * authors: Stephanie Prystupa-Maule, John Tieu
 * course: CST8288 O.O.P. with Design Patterns - Lab Section 023 
 * professor: Samira Ouaaz
 * coursework: Final Project - Public Transit Management System
 */
package DataAccessLayer.VehicleData;

import TransferObjects.VehicleDTO;
import DataAccessLayer.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of the VehicleDAO interface that provides database operations
 * for managing records in the vehicles table.
 * Extends BaseDAOImpl to leverage common database functionality.
 * 
 * @author johnt
 * @author Stephanie Prystupa-Maule
 * @version 2.0
 * @since 04/03/2025
 * @see BaseDAOImpl
 * @see VehicleDAO
 */
public class VehicleDAOImpl extends BaseDAOImpl implements VehicleDAO {
    
    private final TransitDataSource instance;
    
    /**
     * @throws java.sql.SQLException If bad login credentials are provided
     */
    public VehicleDAOImpl() throws SQLException {
        instance = TransitDataSource.getDataInstance();
        // Test to see if login is successful upon login
        instance.getConnection();
    }
    
        
    /**
     * Gets a list of vehicles 
     * @param query The query for the vehicles table
     * @return The lits of vehicles
     * @throws SQLException 
     */
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
                                .setMaxPassenger(set.getInt("MAX_PASSENGERS"))
                                .setTripID(set.getInt("CURRENT_ASSIGNED_TRIP"))
                                .buildVehicle();
                        vehiclesList.add(vehicle);
                    } catch (IllegalArgumentException e) {
                        // Do nothing, which will skip the records with a bad vehicle type
                        // In theory, the DB is set up so that vehicle type is not null
                    }
                }
            }
        } catch (SQLException e) {
//            e.printStackTrace();
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
                + "FUEL_CONSUMPTION_RATE, MAX_PASSENGERS, CURRENT_ASSIGNED_TRIP)"
                + " VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = instance.getConnection().prepareStatement(insertQuery, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            statement.setString(1, vehicle.getVehicleType().name());
            statement.setString(2, vehicle.getVIN());
            statement.setString(3, vehicle.getFuelType());
            statement.setFloat(4, vehicle.getFuelRate());
            statement.setInt(5, vehicle.getMaxPassengers());
            if (vehicle.getTripID() == null) {
                statement.setNull(6, java.sql.Types.NULL);
            } else {
                statement.setInt(6, vehicle.getTripID());
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
    public void updateVehicle(String newFuel, String newRoute, int vehicleID) throws SQLException {
        String updateQuery = "UPDATE VEHICLES SET FUEL_TYPE = ?, CURRENT_ASSIGNED_TRIP = ? WHERE VEHICLE_ID = ?";
        try (PreparedStatement statement = instance.getConnection().prepareStatement(updateQuery, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            if (newFuel == null) {
                statement.setString(1, "N/A");
            } else {
                statement.setString(1, newFuel);
            }
            if (newRoute == null) {
                statement.setNull(2, java.sql.Types.NULL);
            } else {
                statement.setInt(2, Integer.parseInt(newRoute));
            }
            statement.setInt(3, vehicleID);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw e;
        }
    }
    /**
     * Removes a vehicle from the transit fleet
     * @param vehicleID The vehicle's ID to be removed
     * @throws SQLException on invalid login credentials
     */
    public void removeVehicle(int vehicleID) throws SQLException {
        String deleteQuery = "DELETE FROM VEHICLES WHERE VEHICLE_ID = ?";
        try (PreparedStatement statement = instance.getConnection().prepareStatement(deleteQuery, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            statement.setInt(1, vehicleID);
            statement.executeUpdate();
        }
    }
    
    /**
     * Returns a lit of headers metadata from vehicles table
     * @return
     * @throws SQLException 
     */
    @Override
    public List<String> getVehicleHeaders() throws SQLException {
        String query = "SELECT * FROM VEHICLE_FORMATTED";
        List<String> vHeaders = new ArrayList<>();
        ResultSet set;
        ResultSetMetaData sqlHeaders;
        try (
            PreparedStatement statement = instance.getConnection().prepareStatement(
                query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY
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
    
    /** Retrieves a vehicle from the database by Vehicle ID.
     * Executes a SELECT query on the vehicles table and return record that
     * matches the Vehicle ID.
     * 
     * @param vehicleID The ID of the vehicle to retrieve
     * @return VehicleDTO object containing the vehicle data for the matching record
     *          or null if not matching record is found
     */
    public VehicleDTO getVehicleByID(Integer vehicleID) throws SQLException {
        return executeOperation(new DatabaseOperation<VehicleDTO>() {
            @Override
            public VehicleDTO execute(Connection conn, PreparedStatement pstmt, ResultSet rs) throws SQLException {
                VehicleDTO vehicle = null;
                pstmt = conn.prepareStatement (
                         "SELECT vehicle_id, vehicle_type, vehicle_number, "
                        + "fuel_type, fuel_consumption_rate, max_passengers, "
                        + "current_assigned_trip FROM vehicles WHERE vehicle_id = ?");
                pstmt.setInt(1, vehicleID);
                rs = pstmt.executeQuery();

                if (rs.next()) {                   
                    // Check if current assigned trip is null before setting it to an int or null 
                    Integer tripID = rs.getObject("CURRENT_ASSIGNED_TRIP") != null ? 
                                     rs.getInt("CURRENT_ASSIGNED_TRIP") : null;

                    vehicle = VehicleDTO.setupVehicle()
                            .setID(rs.getInt("VEHICLE_ID"))
                            .setVehicleType(VehicleDTO.VehicleType.valueOf(rs.getString("VEHICLE_TYPE")))
                            .setVehicleNum(rs.getString("VEHICLE_NUMBER"))
                            .setFuelType(rs.getString("FUEL_TYPE"))
                            .setConsumptionRate(rs.getFloat("FUEL_CONSUMPTION_RATE"))
                            .setMaxPassenger(rs.getInt("MAX_PASSENGERS"))
                            .setTripID(tripID)
                            .buildVehicle();
                }
                return vehicle;
            }
            @Override
            public String getDescription() {
                return "getVehicleByID";
            }
        });
    }
    
    /**
     * Retrieves all vehicles from the database, ordered by Vehicle Type.
     * Executes a SELECT query on the vehicles table and maps the result set
     * to a list of VehicleDTO objects.
     * 
     * @return ArrayList of VehicleDTO objects containing all the vehicle data.
     *          If no matching records are found, returns an empty list
     * @throws SQLException 
     */
    @Override
    public List<VehicleDTO> getAllVehicles() throws SQLException {
        return executeOperation(new DatabaseOperation<List<VehicleDTO>>() {
            @Override
            public List<VehicleDTO> execute(Connection conn, PreparedStatement pstmt, ResultSet rs) throws SQLException {
                List<VehicleDTO> vehicles = new ArrayList<>();
            
                pstmt = conn.prepareStatement(
                    "SELECT vehicle_id, vehicle_type, vehicle_number, " +
                    "fuel_type, fuel_consumption_rate, max_passengers, " +
                    "current_assigned_trip FROM vehicles ORDER BY vehicle_type");
            
                rs = pstmt.executeQuery();
            
                while (rs.next()) {
                    // Check if current assigned trip is null before setting it to an int or null 
                    Integer tripID = rs.getObject("CURRENT_ASSIGNED_TRIP") != null ? 
                                     rs.getInt("CURRENT_ASSIGNED_TRIP") : null;
                    
                    VehicleDTO vehicle = VehicleDTO.setupVehicle()
                        .setID(rs.getInt("VEHICLE_ID"))
                        .setVehicleType(VehicleDTO.VehicleType.valueOf(rs.getString("VEHICLE_TYPE")))
                        .setVehicleNum(rs.getString("VEHICLE_NUMBER"))
                        .setFuelType(rs.getString("FUEL_TYPE"))
                        .setConsumptionRate(rs.getFloat("FUEL_CONSUMPTION_RATE"))
                        .setMaxPassenger(rs.getInt("MAX_PASSENGERS"))
                        .setTripID(tripID)
                        .buildVehicle();

                    vehicles.add(vehicle);
                }
            
                return vehicles;
            }
        
            @Override
            public String getDescription() {
                return "getAllVehicles";
            }
        });
    }

    /**
     * Updates all fields (except VehicleID) of a vehicle record in the database.
     * Executes an UPDATE query to the vehicles table.
     * 
     * @param vehicle The VehicleDTO containing the data to be updated
     * @throws SQLException 
     */
    @Override
    public void updateVehicle(VehicleDTO vehicle) throws SQLException{
        executeOperation(new DatabaseOperation<Void>() {
            @Override
            public Void execute(Connection conn, PreparedStatement pstmt, ResultSet rs) throws SQLException {
                pstmt = conn.prepareStatement(
                         "UPDATE vehicles SET vehicle_type = ?, vehicle_number = ?, "
                        +"fuel_type = ?, fuel_consumption_rate = ?, max_passengers = ?, "
                        +"current_assigned_trip = ? WHERE vehicle_id = ?");
                
                pstmt.setString(1, vehicle.getVehicleType().name());
                pstmt.setString(2, vehicle.getVIN());
                pstmt.setString(3, vehicle.getFuelType());
                pstmt.setFloat(4, vehicle.getFuelRate());
                pstmt.setInt(5, vehicle.getMaxPassengers());
                
                // Check if tripID is null
                if (vehicle.getTripID() == null) {
                    pstmt.setNull(6, java.sql.Types.INTEGER);
                } else {
                    pstmt.setInt(6, vehicle.getTripID());
                }
                pstmt.setInt(7, vehicle.getVehicleID());
                
                pstmt.executeUpdate();        
                return null;  
            }
            @Override
            public String getDescription() {
                return "updateVehicle";
            }
        });
    }
    
    /**
     * Updates the Fuel Consumption Rate of a vehicle record in the database.
     * Executes an UPDATE query to the vehicles table. Fuel Rates are recalculated
     * regularly based on trip and fuel usage data.
     * 
     * @param vehicle The VehicleDTO containing the data to be updated
     * @throws SQLException 
     */    
    @Override
    public void updateVehicleFuelRate(VehicleDTO vehicle) throws SQLException{
        executeOperation(new DatabaseOperation<Void>() {
            @Override
            public Void execute(Connection conn, PreparedStatement pstmt, ResultSet rs) throws SQLException {
                pstmt = conn.prepareStatement(
                         "UPDATE vehicles SET fuel_consumption_rate = ? "
                        +"WHERE vehicle_id = ?");
                
                pstmt.setFloat(1, vehicle.getFuelRate());
                pstmt.setInt(2, vehicle.getVehicleID());
              
                pstmt.executeUpdate();        
                return null;  
            }
            @Override
            public String getDescription() {
                return "updateVehicleFuelRate";
            }            
        });
    }
    /**
     * Updates the Assigned Trip ID of a vehicle record in the database.
     * Executes an UPDATE query to the vehicles table. Trip IDs are updated 
     * whenever a vehicle is assigned to a new scheduled trip.
     * 
     * @param vehicle The VehicleDTO containing the data to be updated
     * @throws SQLException 
     */     
    @Override
    public void updateVehicleTripID(VehicleDTO vehicle) throws SQLException{
        executeOperation(new DatabaseOperation<Void>() {
            @Override
            public Void execute(Connection conn, PreparedStatement pstmt, ResultSet rs) throws SQLException {
                pstmt = conn.prepareStatement(
                         "UPDATE vehicles SET current_assigned_trip = ? "
                        +"WHERE vehicle_id = ?");
                
                // Check if the tripID is null
                if (vehicle.getTripID() == null) {
                    pstmt.setNull(1, java.sql.Types.INTEGER);
                } else {
                    pstmt.setInt(1, vehicle.getTripID());
                }

                pstmt.setInt(2, vehicle.getVehicleID());
              
                pstmt.executeUpdate();        
                return null;  
            }
            @Override
            public String getDescription() {
                return "updateVehicleTripID";
            }            
        });
    }
    
    /**
     * Deletes (removes) a vehicle from the database.
     * Executes a DELETE query to remove the specified author from the database.
     * 
     * @param vehicle The VehicleDTO with the Vehicle ID of the record to be deleted
     * @throws SQLException 
     */
    @Override
    public void removeVehicle(VehicleDTO vehicle) throws SQLException {
        executeOperation(new DatabaseOperation<Void>() {
            @Override
            public Void execute(Connection conn, PreparedStatement pstmt, ResultSet rs) throws SQLException {
                pstmt = conn.prepareStatement(
                        "DELETE FROM vehicles WHERE vehicle_id = ?");
                pstmt.setInt(1, vehicle.getVehicleID());
                pstmt.executeUpdate();
                return null;
            }
            
            @Override
            public String getDescription() {
                return "removeVehicle";
            }
        });
    }
}    
    
//    @Override
//    public List<String> getVehicleHeaders() throws SQLException {
//        
//    } 
    
