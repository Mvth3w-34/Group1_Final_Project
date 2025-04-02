/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DataAccessLayer.MaintenanceRequest;

import DataAccessLayer.TransitDataSource;
import TransferObjects.LoginDTO;
import TransferObjects.MaintenanceRequestTicketDTO;
import TransferObjects.OperatorDTO;
import static java.lang.reflect.Array.set;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import static sun.security.jgss.GSSUtil.login;

/**
 *
 * @author Mathew Chebet
 */
public class MaintenanceRequestDAOImpl implements MaintenanceRequestDAO
{
    
    private final TransitDataSource connection;
    
    /**
     * A no argument constructor 
     */
    public MaintenanceRequestDAOImpl(TransitDataSource connection){
        this.connection=connection;
    }    
    
    /**
     * This method returns a list of all of the known maintenance requests.
     * 
     * @return requests, an array list of maintenance requests
     */
    @Override
    public List<MaintenanceRequestTicketDTO> getAllMaintenanceRequests(){
        
        ArrayList<MaintenanceRequestTicketDTO> requests =new ArrayList<>();
        String query = "SELECT * FROM MAINTENANCE_REQUESTS";
        ResultSet  results;
        try (PreparedStatement statement = connection.getConnection().prepareStatement(query))
        {
            results = statement.executeQuery();
            while (results.next()) {
                try {
                    MaintenanceRequestTicketDTO request = new MaintenanceRequestTicketDTO();
                    
                    request.setRequestDate(results.getDate("REQUEST_DATE"));
                    request.setQuotedCost(results.getDouble("QUOTED_PRICE"));
                    request.setOperatorID(results.getInt("OPERATOR_ID"));
                    request.setVehicleComponentID(results.getInt("VEHICLE_COMPONENT_ID"));
                    request.setServiceDescription(results.getString("SERVICE_DESCRIPTION"));
                    request.setIsComplete(results.getString("IS_COMPLETED").equalsIgnoreCase("YES"));
                    
                    requests.add(request);
                } catch (IllegalArgumentException e) {
                    // Skip the operator with an invalid user type
                }
            }
        } catch (SQLException e) {
            
        }
        
        return requests;
    }
    
    /**
     * This method will insert a maintenance request entry into the database.
     * 
     * @param request, the maintenance request object
     */
    @Override
    public void insertMaintenanceRequest(MaintenanceRequestTicketDTO request){

        String query = "INSERT INTO MAINTENANCE_REQUESTS(REQUEST_DATE,QUOTED_COST, OPERATOR_ID,VEHICLE_COMPONENT_ID, SERVICE_DESCRIPTION, IS_COMPLETED)"
                + "VALUES(?,?,?,?,?,?)";
        
        ResultSet  results;
        try (PreparedStatement statement = connection.getConnection().prepareStatement(query)){
            statement.setDate(1, (Date) request.getRequestDate());
            statement.setDouble(2, request.getQuotedCost());
            statement.setInt(3, request.getOperatorID());
            statement.setInt(4, request.getVehicleComponentID());
            statement.setString(5, request.getServiceDescription());
            statement.setString(6,request.getIsComplete() ? "YES": "NO"); 
            
            statement.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    
    /**
     * This method will return a maintenance request which is incomplete based on its id.
     * 
     * @param id, the request id
     * @return request, a maintenance request object
     */
    @Override
    public MaintenanceRequestTicketDTO getMaintenanceRequestById(int id){
        MaintenanceRequestTicketDTO request = null;
        String query = "SELECT * FROM MAINTENANCE_REQUESTS WHERE REQUEST_ID = ? AND IS_COMPLETE='NO'";
        ResultSet results;
        try (PreparedStatement statement = connection.getConnection().prepareStatement(query))
        {
            results = statement.executeQuery();
            while (results.next()) {
                try {
                    statement.setInt(1, id);
                    request = new MaintenanceRequestTicketDTO();
                    
                    request.setRequestDate(results.getDate("REQUEST_DATE"));
                    request.setQuotedCost(results.getDouble("QUOTED_PRICE"));
                    request.setOperatorID(results.getInt("OPERATOR_ID"));
                    request.setVehicleComponentID(results.getInt("VEHICLE_COMPONENT_ID"));
                    request.setServiceDescription(results.getString("SERVICE_DESCRIPTION"));
                    request.setIsComplete(results.getString("IS_COMPLETED").equalsIgnoreCase("YES"));
                    
                } catch (IllegalArgumentException e) {
                    // Skip the operator with an invalid user type
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return request;
    }
    
    /**
     * This method updates an incomplete maintenance request to mark it as complete.
     * 
     * @param request, the maintenance request that will be updated
     */
    @Override
    public void updateMaintenanceRequest(MaintenanceRequestTicketDTO request){
        String query = "UPDATE MAINTENANCE_REQUESTS SET IS_COMPLETED = 'YES' WHERE REQUEST_ID = ? ";
        
        try (PreparedStatement statement = connection.getConnection().prepareStatement(query)){
            statement.setInt(1, request.getRequestID());
            statement.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}