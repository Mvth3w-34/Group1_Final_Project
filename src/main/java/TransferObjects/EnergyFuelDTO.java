/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TransferObjects;
/**
 *
 * @author Mario Valencia
 */
import java.util.Date;

public class EnergyFuelDTO {
    private int logId;
    private String vehicleId;
    private Date logDate;
    private float fuelConsumed;
    private float energyConsumed;
    private float fuelThreshold;
    private float energyThreshold;

    // Constructors
    public EnergyFuelDTO() {}

    public EnergyFuelDTO(String vehicleId, Date logDate, float fuelConsumed, float energyConsumed) {
        this.vehicleId = vehicleId;
        this.logDate = logDate;
        this.fuelConsumed = fuelConsumed;
        this.energyConsumed = energyConsumed;
    }

    // Getters and setters
    public int getLogId() {
        return logId;
    }

    public void setLogId(int logId) {
        this.logId = logId;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public Date getLogDate() {
        return logDate;
    }

    public void setLogDate(Date logDate) {
        this.logDate = logDate;
    }

    public float getFuelConsumed() {
        return fuelConsumed;
    }

    public void setFuelConsumed(float fuelConsumed) {
        this.fuelConsumed = fuelConsumed;
    }

    public float getEnergyConsumed() {
        return energyConsumed;
    }

    public void setEnergyConsumed(float energyConsumed) {
        this.energyConsumed = energyConsumed;
    }

    public float getFuelThreshold() {
        return fuelThreshold;
    }

    public void setFuelThreshold(float fuelThreshold) {
        this.fuelThreshold = fuelThreshold;
    }

    public float getEnergyThreshold() {
        return energyThreshold;
    }

    public void setEnergyThreshold(float energyThreshold) {
        this.energyThreshold = energyThreshold;
    }
}
