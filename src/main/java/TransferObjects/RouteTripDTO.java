/* filename: RouteTripDTO.java
 * date: Apr. 6th, 2025
 * authors: John Tieu
 * course: CST8288 O.O.P. with Design Patterns - Lab Section 023 
 * professor: Samira Ouaaz
 * coursework: Final Project - Public Transit Management System
 */
package TransferObjects;

import java.sql.Timestamp;

/**
 *
 * @author John Tieu
 * @version 1.0
 * @since 21
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
