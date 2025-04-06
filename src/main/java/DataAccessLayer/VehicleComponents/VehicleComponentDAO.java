/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package DataAccessLayer.VehicleComponents;

import TransferObjects.VehicleComponentDTO;
import java.util.List;

/**
 * This class will act as a DTO for a maintenance request 
 * 
 * @author Mathew Chebet
 */
public interface VehicleComponentDAO
{
    VehicleComponentDTO getComponentByIDs(int vehicleID,int componentID);
    List<VehicleComponentDTO> getComponentsByVehicleID(int id);
    void addVehicleComponent(VehicleComponentDTO component);
    void updateVehicleComponent(VehicleComponentDTO component);
}
