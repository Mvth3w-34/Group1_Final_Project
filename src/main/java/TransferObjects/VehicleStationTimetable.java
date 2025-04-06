/* filename: VehichleStationTimeTable.java
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
 * 
 * @author John Tieu
 * @version 1.0
 * @since 21
 */
public class VehicleStationTimetable {
    private int vehicleId;
    private String stationName;
    private boolean isStation;
    private Timestamp arrivalTime;
    private Timestamp departureTime;
    
    public void setVehicleId(int id) {
        this.vehicleId = id;
    }
    
    public void setStationName(String name) {
        this.stationName = name;
    }
    
    public void setStation(boolean station) {
        this.isStation = station;
    }
    
    public void setArrivalTime(Timestamp time) {
        this.arrivalTime = time;
    }
    
    public void setDepartureTime(Timestamp time) {
        this.departureTime = time;
    }
    
    public int getVehicleID() {
        return this.vehicleId;
    }
    
    public String getStationName() {
        return this.stationName;
    }
    
    public boolean checkStation() {
        return this.isStation;
    }
    
    public Timestamp getArrivalTime() {
        return this.arrivalTime;
    }
    
    public Timestamp getDepartureTime() {
        return this.departureTime;
    }
}
