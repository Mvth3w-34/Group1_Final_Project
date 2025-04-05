
package DataAccessLayer.VehicleData;
import TransferObjects.VehicleDTO;

/**
 *
 * @author johnt
 */

// steph's version
public class VehicleBuilder {
    private VehicleDTO.VehicleType vehicleType;
    private int vehicleID;
    private String vehicleNumber; // VIN
    private String fuelType;
    private float fuelConsumptionRate;
    private int maxPassengers;
    private Integer assignedTripID;

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
     * @param ID
     * @return The Vehicle Builder
     */
    public VehicleBuilder setID(int ID) {
        this.vehicleID = ID;
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
     * Sets the current assigned trip of the vehicle.
     * Integer used instead of int to account for null values
     * 
     * @param tripID The trip ID to assign, can be null
     * @return The Vehicle Builder
     */
    public VehicleBuilder setTripID(Integer tripID) {
        this.assignedTripID = tripID;
        return this;
    }
    
    /* 
     * TODO review name change
     * build vehicle instead of register vehicle, because a VehicleDTO may be
     * used in a variety of ways (e.g. viewing list of vehicles), not just in
     * registering new vehicles
     */ 
       
    /**
     * Builds the vehicle
     * @return The vehicle
     */
    public VehicleDTO buildVehicle() {
        return new VehicleDTO(vehicleType, vehicleID, vehicleNumber,
            fuelType, fuelConsumptionRate, 
                maxPassengers, assignedTripID);
    }
    
//    /**
//     * Registers the vehicle
//     * @return The vehicle
//     */
//    public VehicleDTO registerVehicle() {
//        return new VehicleDTO(vehicleType, vehicleID, vehicleNumber,
//            fuelType, fuelConsumptionRate, 
//                maxPassengers, assignedTripID);
//    }
    
}
