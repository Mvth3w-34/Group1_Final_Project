/* filename: VehicleDAOImpl.java
 * date: Apr. 3rd, 2025
 * authors: Stephanie Prystupa-Maule, John Tieu
 * course: CST8288 O.O.P. with Design Patterns - Lab Section 023 
 * professor: Samira Ouaaz
 * coursework: Final Project - Public Transit Management System
 */
package DataAccessLayer.VehicleData;

import TransferObjects.VehicleDTO;
import DataAccessLayer.BaseDAOImpl;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
    
    /**
     * Retrieves a vehicle from the database by Vehicle ID.
     * Executes a SELECT query on the vehicles table and return record that
     * matches the Vehicle ID.
     * 
     * @param vehicleID The ID of the vehicle to retrieve
     * @return VehicleDTO object containing the vehicle data for the matching record
     *          or null if not matching record is found
     * @throws SQLException 
     */
    @Override
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
     * Adds (registers) a new vehicle to the database.
     * Executes an INSERT query to add the vehicle information to the database.
     * VehicleID is auto-generated by the DBMS.
     * 
     * @param vehicle The VehicleDTO object containing the data to be inserted
     * @throws SQLException 
     */
    @Override
    public void registerVehicle(VehicleDTO vehicle) throws SQLException{
        executeOperation(new DatabaseOperation<Void>() {
            @Override
            public Void execute(Connection conn, PreparedStatement pstmt, ResultSet rs) throws SQLException {
                pstmt = conn.prepareStatement(
                        "INSERT INTO vehicles (vehicle_type, vehicle_number, fuel_type, "
                        +"fuel_consumption_rate, max_passengers, current_assigned_trip) "
                        +"VALUES (?,?,?,?,?,?)");
                
                pstmt.setString(1, vehicle.getVehicleType().name());
                pstmt.setString(2, vehicle.getVIN());
                pstmt.setString(3, vehicle.getFuelType());
                pstmt.setFloat(4, vehicle.getFuelRate());
                pstmt.setInt(5, vehicle.getMaxPassengers());
                
                if (vehicle.getTripID() == null) {
                    pstmt.setNull(6, java.sql.Types.INTEGER);
                } else {
                    pstmt.setInt(6, vehicle.getTripID());
                }
                pstmt.executeUpdate();        
                return null;        
            }
                    
            @Override
            public String getDescription() {
                return "registerVehicle";
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
    
