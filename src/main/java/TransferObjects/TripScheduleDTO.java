/* filename: TripScheduleDTO.java
 * date: Apr. 5th, 2025
 * authors: Stephanie Prystupa-Maule
 * course: CST8288 O.O.P. with Design Patterns - Lab Section 023 
 * professor: Samira Ouaaz
 * coursework: Final Project - Public Transit Management System
 */
package TransferObjects;

import java.sql.Time;

/**
 * Data Transfer Object for Trip Schedules.
 * Represents data from the VW_TRIP_SCHEDULES view.
 * Contains trip schedule information along with related route and vehicle data.
 * 
 * @author Stephanie Prystupa-Maule
 * @version 1.0
 * @since 04/05/2025
 */
public class TripScheduleDTO {
    
    private Integer tripScheduleId;
    private Time startTime;
    private Integer routeId;
    private String routeName;
    private String direction;
    private VehicleDTO vehicle;  // reference to vehicle object 
    
    
    /**
     * Parameterized constructor for TripScheduleDTO
     * Used by the Builder
     * 
     * @param tripScheduleId The unique identifier for the trip schedule
     * @param startTime The scheduled start time of the trip
     * @param routeId The route identifier
     * @param routeName The name of the route
     * @param direction The direction of the route (1 for outbound, 2 for inbound)
     * @param vehicle The vehicle assigned to this trip (can be null if no vehicle assigned)
     */
    private TripScheduleDTO(Integer tripScheduleId, Time startTime, Integer routeId, 
                          String routeName, String direction, VehicleDTO vehicle) {
        this.tripScheduleId = tripScheduleId;
        this.startTime = startTime;
        this.routeId = routeId;
        this.routeName = routeName;
        this.direction = direction;
        this.vehicle = vehicle;
    }
    
    /**
     * Gets the trip schedule ID
     * 
     * @return The trip schedule ID
     */
    public Integer getTripScheduleId() {
        return tripScheduleId;
    }
    
    /**
     * Sets the trip schedule ID
     * 
     * @param tripScheduleId The trip schedule ID to set
     */
    public void setTripScheduleId(Integer tripScheduleId) {
        this.tripScheduleId = tripScheduleId;
    }
    
    /**
     * Gets the start time of the trip schedule
     * 
     * @return The start time
     */
    public Time getStartTime() {
        return startTime;
    }
    
    /**
     * Sets the start time of the trip schedule
     * 
     * @param startTime The start time to set
     */
    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }
    
    /**
     * Gets the route ID  the trip schedule
     * 
     * @return The route ID associated with the trip schedule
     */
    public Integer getRouteId() {
        return routeId;
    }
    
    /**
     * Sets the route ID of the trip schedule
     * 
     * @param routeId The route ID to set for the trip schedule
     */
    public void setRouteId(Integer routeId) {
        this.routeId = routeId;
    }
    
    /**
     * Gets the route name associated with the trip schedule
     * 
     * @return The route name associated with the trip schedule
     */
    public String getRouteName() {
        return routeName;
    }
    
    /**
     * Sets the route name of the trip schedule
     * 
     * @param routeName The route name to set for the trip schedule
     */
    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }
    
    /**
     * Gets the direction of the route in the trip schedule
     * 
     * @return The direction (1 for outbound, 2 for inbound)
     */
    public String getDirection() {
        return direction;
    }
    
    /**
     * Sets the direction of the route in the trip schedule
     * 
     * @param direction The direction to set (1 for outbound, 2 for inbound)
     */
    public void setDirection(String direction) {
        this.direction = direction;
    }
    
    /**
     * Gets the vehicle assigned to this trip
     * 
     * @return The VehicleDTO object, or null if no vehicle is assigned
     */
    public VehicleDTO getVehicle() {
        return vehicle;
    }
    
    /**
     * Sets the vehicle assigned to this trip
     * 
     * @param vehicle The VehicleDTO to assign to this trip
     */
    public void setVehicle(VehicleDTO vehicle) {
        this.vehicle = vehicle;
    }
    
    /**
     * Determines if a vehicle is assigned to this trip
     * 
     * @return true if a vehicle is assigned, false otherwise
     */
    public boolean hasVehicleAssigned() {
        return (vehicle != null);
    }
    
    /**
     * Builder class for TripScheduleDTO
     * Implements the Builder design pattern for easier object creation
     */
    public static class Builder {
        private Integer tripScheduleId;
        private Time startTime;
        private Integer routeId;
        private String routeName;
        private String direction;
        private VehicleDTO vehicle;
        
        public Builder() {
            // Initialize with default values
        }
        
        public Builder setTripScheduleId(Integer tripScheduleId) {
            this.tripScheduleId = tripScheduleId;
            return this;
        }
        
        public Builder setStartTime(Time startTime) {
            this.startTime = startTime;
            return this;
        }
        
        public Builder setRouteId(Integer routeId) {
            this.routeId = routeId;
            return this;
        }
        
        public Builder setRouteName(String routeName) {
            this.routeName = routeName;
            return this;
        }
        
        public Builder setDirection(String direction) {
            this.direction = direction;
            return this;
        }
        
        public Builder setVehicle(VehicleDTO vehicle) {
            this.vehicle = vehicle;
            return this;
        }
        
        public TripScheduleDTO build() {
            return new TripScheduleDTO(tripScheduleId, startTime, routeId, 
                                      routeName, direction, vehicle);
        }
    }
    
    /**
     * Creates a new Builder instance
     * 
     * @return A new Builder for TripScheduleDTO
     */
    public static Builder builder() {
        return new Builder();
    }
}
