/* filename: VehichleComponentDTO.java
 * date: Apr. 6th, 2025
 * authors: Mathew Chebet
 * course: CST8288 O.O.P. with Design Patterns - Lab Section 023 
 * professor: Samira Ouaaz
 * coursework: Final Project - Public Transit Management System
 */
package TransferObjects;

/**
 * This class will act as a DTO for a maintenance request 
 * 
 * @author Mathew Chebet
 * @version 1.0
 * @since 21
 */
public class VehicleComponentDTO
{
    private int vehicleComponentID;
    private double hoursUsed;
    private int vehicleID;
    private int componentID;
    
    /**
     * This is a no argument constructor.
     */
    public VehicleComponentDTO(){}
    
    /**
     * This method will set the vehicle component ID.
     * 
     * @param id, this is the vehicle component ID 
     */
    public void setVehicleComponentID(int id){
        this.vehicleComponentID = id;
    }
    
    /**
     * This method will get the vehicle component ID.
     * 
     * @return vehicleComponentID, this is the vehicle component ID 
     */
    public int getVehicleComponentID(){
        return vehicleComponentID;
    }
    
    /**
     * This method will set the hours used for a vehicle component.
     * 
     * @param hours, this is the hours of use for a vehicle component 
     */
    public void setHoursUsed(double hours){
        this.hoursUsed = hours;
    }
    
    /**
     * This method will get the hours used for a vehicle component.
     * 
     * @return hoursUsed, this is the hours used for a vehicle component
     */
    public double getHoursUsed(){
        return hoursUsed;
    }
    
    /**
     * This method will set the vehicle ID.
     * 
     * @param id, this is the vehicle ID. 
     */
    public void setVehicleID(int id){
        this.vehicleID = id;
    }
    
    /**
     * This method will get the vehicle ID.
     * 
     * @return vehicleID, this is the vehicle ID 
     */
    public int getVehicleID(){
        return vehicleID;
    }
    
    /**
     * This method will set the component ID.
     * 
     * @param id, this is the component ID. 
     */
    public void setComponentID(int id){
        this.componentID = id;
    }
    
    /**
     * This method will get the component ID.
     * 
     * @return vehicleID, this is the component ID 
     */
    public int getComponentID(){
        return componentID;
    }
}
