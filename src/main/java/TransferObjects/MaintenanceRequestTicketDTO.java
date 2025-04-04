
package TransferObjects;

import java.time.LocalDateTime;

/**
 * This class will act as a DTO for a maintenance request 
 * 
 * @author Mathew Chebet
 */
public class MaintenanceRequestTicketDTO
{
    private int requestID;
    private LocalDateTime requestDate; //Date of a request
    private double quotedCost; //Estimated cost for a request
    private int operatorID; //id of the operator who submitted the request 
    private int vehicleComponentID; //id of the component that will be serviced  
    private String serviceDescription; //description of a service
    private boolean isComplete;
    
    /**
     * No argument constructor.
     */
    public MaintenanceRequestTicketDTO(){}
    
    /**
     * This method will set the ID of a maintenance request.
     * 
     * @param id, the ID of a maintenance request.
     */
    public void setRequestID(int id){
        this.requestID = id;
    }
    
    /**
     * This method will get the ID of a maintenance request.
     * 
     * @return requestID, the ID of a maintenance request.
     */
    public int getRequestID(){
        return requestID;
    } 
    
    
    /**
     * This method will set the a maintenance request was submitted.
     * 
     * @param date, the date a request is submitted in a format of yyyy-mm-dd.
     */
    public void setRequestDate(LocalDateTime date){
        this.requestDate = date;
    } 
    
    /**
     * This method will get date when a maintenance request was submitted.
     * 
     * @return requestDate, the date a request is submitted in a format of yyyy-mm-dd.
     */
    public LocalDateTime getRequestDate(){
        return requestDate;
    } 
    
    /**
     * This method will set estimated cost of a maintenance service.
     * 
     * @param cost, the estimated cost of a service.
     */
    public void setQuotedCost(double cost){
        this.quotedCost = cost;
    } 
    
    /**
     * This method will get the cost of a maintenance service.
     * 
     * @return quotedCost, the cost of a maintenance service.
     */
    public double getQuotedCost(){
        return quotedCost;
    } 
    
    /**
     * This method will set the id of the operator who placed the request.
     * 
     * @param id, the id of the operator who submitted the request.
     */
    public void setOperatorID(int id){
        this.operatorID = id;
    } 
    
    /**
     * This method will get the operator's id.
     * 
     * @return operatorID, the operator id.
     */
    public int getOperatorID(){
        return operatorID;
    } 
    
    /**
     * This method will set the id of a vehicle component.
     * 
     * @param id, the id of a vehicle component.
     */
    public void setVehicleComponentID(int id){
        this.vehicleComponentID = id;
    } 
    
    /**
     * This method will get the id of a vehicle component.
     * 
     * @return vehicleComponentID, the id for a vehicle component.
     */
    public int getVehicleComponentID(){
        return vehicleComponentID;
    } 
    
    /**
     * This method will set the description of a service for a maintenance request.
     * 
     * @param desc, the description for a maintenance service.
     */
    public void setServiceDescription(String desc){
        this.serviceDescription = desc;
    } 
    
    /**
     * This method will get the description of a service for a maintenance request.
     * 
     * @return serviceDescription, the description for a maintenance service.
     */
    public String getServiceDescription(){
        return serviceDescription;
    } 
    
    /**
     * This method will set the status of a maintenance request.
     * 
     * @param complete, the status of a maintenance request.
     */
    public void setIsComplete(boolean complete){
        this.isComplete = complete;
    } 
    
    /**
     * This method will get the status of a maintenance request.
     * 
     * @return isComplete, the status of maintenance request.
     */
    public boolean getIsComplete(){
        return isComplete;
    } 
    
    
}
