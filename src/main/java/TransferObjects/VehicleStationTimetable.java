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
