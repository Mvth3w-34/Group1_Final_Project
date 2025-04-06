/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TransferObjects;

import java.sql.Timestamp;

/**
 *
 * @author johnt
 */
public class RouteTripDTO {
    
    private int tripScheduleId;
    private int routeId;
    private int tripTemplateId;
    private Timestamp startTime;
    
    public void setTripScheduleID(int id) {
        this.tripScheduleId = id;
    }
    
    public void setRouteID(int id) {
        this.routeId = id;
    }
    
    public void setTripTemplateID(int id) {
        this.tripScheduleId = id;
    }
    
    public void setStartTime(Timestamp start) {
        this.startTime = start;
    }
    
    public int getTripScheduleID() {
        return this.tripScheduleId;
    }
    
    public int getRouteID() {
        return this.routeId;
    }
    
    public int getTripTemplateID() {
        return this.tripTemplateId;
    }
    
    public Timestamp getStartTime() {
        return this.startTime;
    }
    
}
