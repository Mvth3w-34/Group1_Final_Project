/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DataAccessLayer.VehicleComponents;

import DataAccessLayer.TransitDataSource;
import TransferObjects.MaintenanceRequestTicketDTO;
import TransferObjects.VehicleComponentDTO;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class is an implementation of the VehicleComponent DAO interface.
 * 
 * @author Mathew Chebet
 */
public class VehicleComponentDAOImpl implements VehicleComponentDAO
{
    private final TransitDataSource connection;
    
    /**
     * A single argument constructor. 
     * 
     */
    public VehicleComponentDAOImpl(TransitDataSource connection){
        this.connection = connection;
    }
    
    /**
     * This method returns a list of all of the known vehicle components for a specific vehicle.
     * 
     * @return components, an array list of components
     */
    @Override
    public List<VehicleComponentDTO> getComponentsByVehicleID(int id){
        ArrayList<VehicleComponentDTO> components =new ArrayList<>();
        String query = "SELECT * FROM VEHICLE_COMPONENT WHERE VEHICLE_ID = ?";
        ResultSet results;
        try (PreparedStatement statement = connection.getConnection().prepareStatement(query))
        {
            statement.setInt(1, id);
            
            results = statement.executeQuery();
            while (results.next()) {
                try {
                    VehicleComponentDTO component = new VehicleComponentDTO();
                    component.setVehicleComponentID(results.getInt("VEHICLE_COMPONENT_ID"));
                    component.setHoursUsed(results.getInt("HOURS_USED"));
                    component.setVehicleID(results.getInt("VEHICLE_ID"));
                    component.setComponentID(results.getInt("COMPONENT_ID"));
                    
                    components.add(component);
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return components;
    }
    
    /**
     * This method adds a vehicle component.
     * 
     * @param component, a vehicle component object
     */
    @Override
    public void addVehicleComponent(VehicleComponentDTO component){
        String query = "INSERT INTO VEHICLE_COMPONENT(HOURS_USED,VEHICLE_ID,COMPONENT_ID,VEHICLE_COMPONENT_ID)"
                + "VALUES(?,?,?)";
        
        ResultSet results;
        try (PreparedStatement statement = connection.getConnection().prepareStatement(query)){
            statement.setDouble(1, component.getHoursUsed());
            statement.setInt(2, component.getVehicleID());
            statement.setInt(3, component.getComponentID());
            
            statement.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * This method updates the hours used for a vehicle component.
     * 
     * @param component, a vehicle component object
     */
    @Override
    public void updateVehicleComponent(VehicleComponentDTO component){
        String query = "UPDATE VEHICLE_COMPONENT SET HOURS_USED = ? WHERE VEHICLE_COMPONENT_ID = ?";
        
        try (PreparedStatement statement = connection.getConnection().prepareStatement(query)){
            statement.setDouble(1, component.getHoursUsed());
            statement.setInt(2, component.getVehicleComponentID());
            statement.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
