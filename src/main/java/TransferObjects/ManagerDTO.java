/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TransferObjects;
import BusinessLayer.TransitBusinessLayer;

/**
 *
 * @author johnt
 */
public class ManagerDTO extends OperatorDTO {
    
    private UserType type = UserType.MANAGER;
    
    @Override
    public void registerVehicle(TransitBusinessLayer logicLayer) {
        TransitBusinessLayer logic = logicLayer;
    }
}
