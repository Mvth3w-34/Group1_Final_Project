/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DataAccessLayer.MaintenanceRequest;

import TransferObjects.MaintenanceRequestTicketDTO;
import java.util.List;

/**
 *
 * @author Mathew Chebet 
 */
public interface MaintenanceRequestDAO
{
    List<MaintenanceRequestTicketDTO> getAllMaintenanceRequests();
    void insertMaintenanceRequest(MaintenanceRequestTicketDTO request);
    MaintenanceRequestTicketDTO getMaintenanceRequestByStatus(int id);
    void updateMaintenanceRequest(MaintenanceRequestTicketDTO request);
}
