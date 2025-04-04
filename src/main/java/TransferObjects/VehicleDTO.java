
package TransferObjects;

import DataAccessLayer.VehicleData.VehicleBuilder;

/**
 *
 * @author johnt
 */

// steph's version
public class VehicleDTO {

    public enum VehicleType {
        BUS,
        ELECTRIC_TRAIN,
        DIESEL_TRAIN
    }
    private VehicleType vehicleType;
    private int vehicleID;
    private String vehicleNumber; // VIN
    private String fuelType;
    private float fuelConsumptionRate;
    private int maxPassengers;
    private Integer assignedTripID = null; // null default value
    
    /**
     * Creates the vehicles
     * @param type
     * @param ID
     * @param num
     * @param fuelType
     * @param fuelRate
     * @param maxPass
     * @param tripId
     */
    public VehicleDTO(VehicleType type, int ID, String num, String fuelType,
float fuelRate, int maxPass, int tripID) {
        this.vehicleType = type;
        this.vehicleID = ID;
        this.vehicleNumber = num;
        this.fuelType = fuelType;
        this.fuelConsumptionRate = fuelRate;
        this.maxPassengers = maxPass;
        this.assignedTripID = tripID;
    }
    public static VehicleBuilder setupVehicle() {
        return new VehicleBuilder();
    }
    public VehicleType getVehicleType() {
        return this.vehicleType;
    }
    public int getVehicleID() {
        return this.vehicleID;
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
    // setter for fuel rate, as the value is updated regularly
    public void setFuelRate(float fuelRate) {
        this.fuelConsumptionRate = fuelRate;
    }
    public int getMaxPassengers() {
        return this.maxPassengers;
    }
    public Integer getTripID() {
        return this.assignedTripID;
    }
    // setter for assigned trip, as the value is updated regularly
    public void setTripID(int tripID) {
        this.assignedTripID = tripID;
    }

}
