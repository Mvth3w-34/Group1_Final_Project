/* filename: ActualTripDTO.java
 * date: Apr. 5th, 2025
 * authors: Stephanie Prystupa-Maule
 * course: CST8288 O.O.P. with Design Patterns - Lab Section 023 
 * professor: Samira Ouaaz
 * coursework: Final Project - Public Transit Management System
 */
package TransferObjects;

import TransferObjects.VehicleDTO;

import java.sql.Date;

/**
 * Data Transfer Object for Actual Trips.
 * Represents data from the ACTUAL_TRIPS table.
 * Contains actual trip execution information along with references to scheduled trip,
 * operator, and vehicle data.
 * 
 * @author Stephanie Prystupa-Maule
 * @version 1.0
 * @since 04/05/2025
 */
public class ActualTripDTO {
    
    private Integer actualTripId;
    private Integer tripScheduleId;
    private Integer operatorId;
    private VehicleDTO vehicle;  // reference to vehicle object, never null
    private Date tripDate;
    private String tripStatus; // COMPLETED, CANCELLED, PARTIAL, IN_PROGRESS

    /**
     * Parameterized constructor for ActualTripDTO
     * Used by the Builder
     * 
     * @param actualTripId The unique identifier for the actual trip
     * @param scheduledTripId The reference to the scheduled trip
     * @param operatorId The operator who executed the trip
     * @param vehicle The vehicle used for the trip
     * @param tripDate The date when the trip was executed
     * @param tripStatus The status of the trip (COMPLETED, CANCELLED, PARTIAL, IN_PROGRESS)
     */
    private ActualTripDTO(Integer actualTripId, Integer scheduledTripId, Integer operatorId, 
                        VehicleDTO vehicle, Date tripDate, String tripStatus) {
        this.actualTripId = actualTripId;
        this.tripScheduleId = scheduledTripId;
        this.operatorId = operatorId;
        this.vehicle = vehicle;
        this.tripDate = tripDate;
        this.tripStatus = tripStatus;
    }
    
    /**
     * Gets the actual trip ID
     * 
     * @return The actual trip ID
     */
    public Integer getActualTripId() {
        return actualTripId;
    }
    
    /**
     * Sets the actual trip ID
     * 
     * @param actualTripId The actual trip ID to set
     */
    public void setActualTripId(Integer actualTripId) {
        this.actualTripId = actualTripId;
    }
    
    /**
     * Gets the scheduled trip ID associated with this actual trip
     * 
     * @return The scheduled trip ID
     */
    public Integer getTripScheduleId() {
        return tripScheduleId;
    }
    
    /**
     * Sets the scheduled trip ID associated with this actual trip
     * 
     * @param tripScheduleId The scheduled trip ID to set
     */
    public void setTripScheduleId(Integer tripScheduleId) {
        this.tripScheduleId = tripScheduleId;
    }
    
    /**
     * Gets the operator ID who executed this trip
     * 
     * @return The operator ID
     */
    public Integer getOperatorId() {
        return operatorId;
    }
    
    /**
     * Sets the operator ID who executed this trip
     * 
     * @param operatorId The operator ID to set
     */
    public void setOperatorId(Integer operatorId) {
        this.operatorId = operatorId;
    }
    
    /**
     * Gets the vehicle used for this trip
     * 
     * @return The VehicleDTO object
     */
    public VehicleDTO getVehicle() {
        return vehicle;
    }
    
    /**
     * Sets the vehicle used for this trip
     * 
     * @param vehicle The VehicleDTO to set
     */
    public void setVehicle(VehicleDTO vehicle) {
        this.vehicle = vehicle;
    }
    
    /**
     * Gets the vehicle ID used for this trip
     * 
     * @return The vehicle ID
     */
    public Integer getVehicleId() {
        return vehicle.getVehicleID();
    }
    
    /**
     * Gets the vehicle number (VIN)
     * 
     * @return The vehicle number (VIN)
     */
    public String getVIN() {
        return vehicle.getVIN();
    }
    
    /**
     * Gets the date when the trip was executed
     * 
     * @return The trip date
     */
    public Date getTripDate() {
        return tripDate;
    }
    
    /**
     * Sets the date when the trip was executed
     * 
     * @param tripDate The trip date to set
     */
    public void setTripDate(Date tripDate) {
        this.tripDate = tripDate;
    }
    
    /**
     * Gets the status of this trip
     * 
     * @return The trip status (COMPLETED, CANCELLED, PARTIAL, IN_PROGRESS)
     */
    public String getTripStatus() {
        return tripStatus;
    }
    
    /**
     * Sets the status of this trip
     * 
     * @param tripStatus The trip status to set (COMPLETED, CANCELLED, PARTIAL, IN_PROGRESS)
     */
    public void setTripStatus(String tripStatus) {
        this.tripStatus = tripStatus;
    }
    
    /**
     * Checks if the trip is completed
     * 
     * @return true if the trip is completed, false otherwise
     */
    public boolean isCompleted() {
        return "COMPLETED".equals(tripStatus);
    }
    
    /**
     * Checks if the trip is cancelled
     * 
     * @return true if the trip is cancelled, false otherwise
     */
    public boolean isCancelled() {
        return "CANCELLED".equals(tripStatus);
    }
    
    /**
     * Checks if the trip is in progress
     * 
     * @return true if the trip is in progress, false otherwise
     */
    public boolean isInProgress() {
        return "IN_PROGRESS".equals(tripStatus);
    }
    
    /**
     * Builder class for ActualTripDTO
     * Implements the Builder design pattern for easier object creation
     */
    public static class Builder {
        private Integer actualTripId;
        private Integer scheduledTripId;
        private Integer operatorId;
        private String operatorName;
        private VehicleDTO vehicle;
        private Date tripDate;
        private String tripStatus;
        
        public Builder() {
            // Initialize with default values
        }
        
        public Builder setActualTripId(Integer actualTripId) {
            this.actualTripId = actualTripId;
            return this;
        }
        
        public Builder setScheduledTripId(Integer scheduledTripId) {
            this.scheduledTripId = scheduledTripId;
            return this;
        }
        
        public Builder setOperatorId(Integer operatorId) {
            this.operatorId = operatorId;
            return this;
        }
        
        public Builder setOperatorName(String operatorName) {
            this.operatorName = operatorName;
            return this;
        }
        
        public Builder setVehicle(VehicleDTO vehicle) {
            this.vehicle = vehicle;
            return this;
        }
        
        public Builder setTripDate(Date tripDate) {
            this.tripDate = tripDate;
            return this;
        }
        
        public Builder setTripStatus(String tripStatus) {
            this.tripStatus = tripStatus;
            return this;
        }
        
        public ActualTripDTO build() {
            return new ActualTripDTO(actualTripId, scheduledTripId, operatorId, 
                                   vehicle, tripDate, tripStatus);
        }
    }
    
    /**
     * Creates a new Builder instance
     * 
     * @return A new Builder for ActualTripDTO
     */
    public static Builder builder() {
        return new Builder();
    }
}
