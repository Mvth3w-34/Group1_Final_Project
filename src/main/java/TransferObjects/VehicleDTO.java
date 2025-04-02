/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TransferObjects;

import DataAccessLayer.VehicleData.VehicleBuilder;

/**
 *
 * @author johnt
 */
public class VehicleDTO {

    public enum VehicleType {
        BUS,
        ELECTRIC_TRAIN,
        DIESEL_TRAIN
    }
    private VehicleType vehicleType;
    private int vehicleId;
    private String vehicleNumber; // VIN
    private String fuelType;
    private float fuelConsumptionRate;
    private int maxPassengers;
    private String assignedRoute;
    
    /**
     * Creates the vehicles
     * @param type
     * @param ID
     * @param num
     * @param fuelType
     * @param fuelRate
     * @param maxPass
     * @param route 
     */
    public VehicleDTO(VehicleType type, int ID, String num, String fuelType,
float fuelRate, int maxPass, String route) {
        this.vehicleType = type;
        this.vehicleId = ID;
        this.vehicleNumber = num;
        this.fuelType = fuelType;
        this.fuelConsumptionRate = fuelRate;
        this.maxPassengers = maxPass;
        this.assignedRoute = route;
    }
    public static VehicleBuilder setupVehicle() {
        return new VehicleBuilder();
    }
    public VehicleType getVehicleType() {
        return this.vehicleType;
    }
    public int getVehicleID() {
        return this.vehicleId;
    }
    public String getVIN() {
        return this.vehicleNumber;
    }
    public String getFuelType() {
        return this.fuelType;
    }
    public float getFuelRate() {
        return this.fuelConsumptionRate;
    }
    public int getMaxPassengers() {
        return this.maxPassengers;
    }
    public String getRoute() {
        return this.assignedRoute;
    }
    public void setRoute(String route) {
        this.assignedRoute = route;
    }
}
