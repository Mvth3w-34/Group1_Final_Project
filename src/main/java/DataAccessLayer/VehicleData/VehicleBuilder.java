/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DataAccessLayer.VehicleData;
import TransferObjects.VehicleDTO;

/**
 *
 * @author johnt
 */
public class VehicleBuilder {
    private VehicleDTO.VehicleType vehicleType;
    private int vehicleId;
    private String vehicleNumber; // VIN
    private String fuelType;
    private float fuelConsumptionRate;
    private int maxPassengers;
    private String assignedRoute;

    /**
     * Sets the vehicle type
     * @param type
     * @return The Vehicle Builder
     */
    public VehicleBuilder setVehicleType(VehicleDTO.VehicleType type) {
        this.vehicleType = type;
        return this;
    }
    /**
     * Sets the vehicle ID (for DB purposes)
     * @param id
     * @return The Vehicle Builder
     */
    public VehicleBuilder setID(int id) {
        this.vehicleId = id;
        return this;
    }
    /**
     * Sets the vehicle number (VIN)
     * @param num
     * @return The Vehicle Builder
     */
    public VehicleBuilder setVehicleNum(String num) {
        this.vehicleNumber = num;
        return this;
    }
    /**
     * Sets the vehicle's fuel type
     * @param fuelType
     * @return The Vehicle Builder
     */
    public VehicleBuilder setFuelType(String fuelType) {
        this.fuelType = fuelType;
        return this;
    }
    /**
     * Sets the vehicle's fuel consumption rate
     * @param rate
     * @return The Vehicle Builder
     */
    public VehicleBuilder setConsumptionRate(float rate) {
        this.fuelConsumptionRate = rate;
        return this;
    }
    /**
     * Sets the maximum passenger that the vehicle can hold
     * @param maxPass
     * @return The Vehicle Builder
     */
    public VehicleBuilder setMaxPassenger(int maxPass) {
        this.maxPassengers = maxPass;
        return this;
    }
    /**
     * Sets the route of the vehicle
     * @param route
     * @return The Vehicle Builder
     */
    public VehicleBuilder setRoute(String route) {
        this.assignedRoute = route;
        return this;
    }
    /**
     * Registers the vehicle
     * @return The vehicle
     */
    public VehicleDTO registerVehicle() {
        return new VehicleDTO(vehicleType, vehicleId, vehicleNumber,
            fuelType, fuelConsumptionRate, 
                maxPassengers, assignedRoute);
    }
    
}
