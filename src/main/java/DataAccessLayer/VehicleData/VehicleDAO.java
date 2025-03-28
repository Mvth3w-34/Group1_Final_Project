/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package DataAccessLayer.VehicleData;
import TransferObjects.*;
import java.sql.SQLException;
import java.util.*;

/**
 *
 * @author johnt
 */
public interface VehicleDAO {
    
    public List<VehicleDTO> getAllVehicles() throws SQLException;
    
    public void registerVehicle(VehicleDTO vehicle) throws SQLException;
    
    public void updateVehicle(String newFuel, String newRoute, VehicleDTO vehicle) throws SQLException;
    
    public void removeVehicle(int vehicleID) throws SQLException;
    
}
