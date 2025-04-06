/* filename: ActualStopTimeDTO.java
 * date: Apr. 5th, 2025
 * authors: Stephanie Prystupa-Maule
 * course: CST8288 O.O.P. with Design Patterns - Lab Section 023 
 * professor: Samira Ouaaz
 * coursework: Final Project - Public Transit Management System
 */
package TransferObjects;

import java.sql.Time;

/**
 * Data Transfer Object for Actual Stop Times.
 * Represents data from the ACTUAL_STOP_TIMES table.
 * Contains arrival and departure times for a specific stop during an actual trip,
 * along with variances from the scheduled times.
 * 
 * @author Stephanie Prystupa-Maule
 * @version 1.0
 * @since 04/05/2025
 */
public class ActualStopTimeDTO {
    
    private Integer actualStopTimeId;
    private Integer actualTripId;
    private Integer seqStopId;
    private Integer stopId;  // Optional, for querying a ROUTE_STOPS join
    private String stopName; // Optional, for querying a STOPS join
    private Boolean isStation; // Optional, for querying a STOPS join
    private Integer stopSequence; // Optional, for querying a ROUTE_STOPS join
    private Time scheduledArrival;
    private Time scheduledDeparture;
    private Time actualArrival;
    private Time actualDeparture;
    private Integer arrivalVariance;  // in seconds, positive means late, negative means early
    private Integer departureVariance; // in seconds, positive means late, negative means early
    
    /**
     * Parameterized constructor for ActualStopTimeDTO
     * Used by the Builder
     * 
     * @param actualStopTimeId The unique identifier for the actual stop time
     * @param actualTripId The actual trip this stop time belongs to
     * @param seqStopId The route stop sequence ID
     * @param stopId The stop ID (optional)
     * @param stopName The name of the stop (optional)
     * @param isStation Whether the stop is a station (optional)
     * @param stopSequence The sequence number of the stop in the route (optional)
     * @param scheduledArrival The scheduled arrival time
     * @param scheduledDeparture The scheduled departure time
     * @param actualArrival The actual arrival time
     * @param actualDeparture The actual departure time
     * @param arrivalVariance The variance between scheduled and actual arrival time in seconds
     * @param departureVariance The variance between scheduled and actual departure time in seconds
     */
    private ActualStopTimeDTO(Integer actualStopTimeId, Integer actualTripId, Integer seqStopId,
                            Integer stopId, String stopName, Boolean isStation, Integer stopSequence,
                            Time scheduledArrival, Time scheduledDeparture,
                            Time actualArrival, Time actualDeparture,
                            Integer arrivalVariance, Integer departureVariance) {
        this.actualStopTimeId = actualStopTimeId;
        this.actualTripId = actualTripId;
        this.seqStopId = seqStopId;
        this.stopId = stopId;
        this.stopName = stopName;
        this.isStation = isStation;
        this.stopSequence = stopSequence;
        this.scheduledArrival = scheduledArrival;
        this.scheduledDeparture = scheduledDeparture;
        this.actualArrival = actualArrival;
        this.actualDeparture = actualDeparture;
        this.arrivalVariance = arrivalVariance;
        this.departureVariance = departureVariance;
    }
    
    /**
     * Gets the actual stop time ID
     * 
     * @return The actual stop time ID
     */
    public Integer getActualStopTimeId() {
        return actualStopTimeId;
    }
    
    /**
     * Sets the actual stop time ID
     * 
     * @param actualStopTimeId The actual stop time ID to set
     */
    public void setActualStopTimeId(Integer actualStopTimeId) {
        this.actualStopTimeId = actualStopTimeId;
    }
    
    /**
     * Gets the actual trip ID this stop time belongs to
     * 
     * @return The actual trip ID
     */
    public Integer getActualTripId() {
        return actualTripId;
    }
    
    /**
     * Sets the actual trip ID this stop time belongs to
     * 
     * @param actualTripId The actual trip ID to set
     */
    public void setActualTripId(Integer actualTripId) {
        this.actualTripId = actualTripId;
    }
    
    /**
     * Gets the route stop sequence ID
     * 
     * @return The route stop sequence ID
     */
    public Integer getSeqStopId() {
        return seqStopId;
    }
    
    /**
     * Sets the route stop sequence ID
     * 
     * @param seqStopId The route stop sequence ID to set
     */
    public void setSeqStopId(Integer seqStopId) {
        this.seqStopId = seqStopId;
    }
    
    /**
     * Gets the stop ID
     * 
     * @return The stop ID, may be null if not joined with ROUTE_STOPS
     */
    public Integer getStopId() {
        return stopId;
    }
    
    /**
     * Sets the stop ID
     * 
     * @param stopId The stop ID to set
     */
    public void setStopId(Integer stopId) {
        this.stopId = stopId;
    }
    
    /**
     * Gets the stop name
     * 
     * @return The stop name, may be null if not joined with STOPS
     */
    public String getStopName() {
        return stopName;
    }
    
    /**
     * Sets the stop name
     * 
     * @param stopName The stop name to set
     */
    public void setStopName(String stopName) {
        this.stopName = stopName;
    }

    /**
     * Checks if the stop is a station
     * 
     * @return true if the stop is a station, false otherwise, may be null if not joined with STOPS
     */
    public Boolean getIsStation() {
        return isStation;
    }
    
    /**
     * Sets whether the stop is a station
     * 
     * @param isStation true if the stop is a station, false otherwise
     */
    public void setIsStation(Boolean isStation) {
        this.isStation = isStation;
    }
    
    /**
     * Gets the sequence number of the stop in the route
     * 
     * @return The stop sequence number, may be null if not joined with ROUTE_STOPS
     */
    public Integer getStopSequence() {
        return stopSequence;
    }
    
    /**
     * Sets the sequence number of the stop in the route
     * 
     * @param stopSequence The stop sequence number to set
     */
    public void setStopSequence(Integer stopSequence) {
        this.stopSequence = stopSequence;
    }
    
    /**
     * Gets the scheduled arrival time
     * 
     * @return The scheduled arrival time
     */
    public Time getScheduledArrival() {
        return scheduledArrival;
    }
    
    /**
     * Sets the scheduled arrival time
     * 
     * @param scheduledArrival The scheduled arrival time to set
     */
    public void setScheduledArrival(Time scheduledArrival) {
        this.scheduledArrival = scheduledArrival;
    }
    
    /**
     * Gets the scheduled departure time
     * 
     * @return The scheduled departure time
     */
    public Time getScheduledDeparture() {
        return scheduledDeparture;
    }
    
    /**
     * Sets the scheduled departure time
     * 
     * @param scheduledDeparture The scheduled departure time to set
     */
    public void setScheduledDeparture(Time scheduledDeparture) {
        this.scheduledDeparture = scheduledDeparture;
    }
    
    /**
     * Gets the actual arrival time
     * 
     * @return The actual arrival time
     */
    public Time getActualArrival() {
        return actualArrival;
    }
    
    /**
     * Sets the actual arrival time
     * 
     * @param actualArrival The actual arrival time to set
     */
    public void setActualArrival(Time actualArrival) {
        this.actualArrival = actualArrival;
    }
    
    /**
     * Gets the actual departure time
     * 
     * @return The actual departure time
     */
    public Time getActualDeparture() {
        return actualDeparture;
    }
    
    /**
     * Sets the actual departure time
     * 
     * @param actualDeparture The actual departure time to set
     */
    public void setActualDeparture(Time actualDeparture) {
        this.actualDeparture = actualDeparture;
    }
    
    /**
     * Gets the variance between scheduled and actual arrival time in seconds
     * 
     * @return The arrival variance in seconds (positive means late, negative means early)
     */
    public Integer getArrivalVariance() {
        return arrivalVariance;
    }
    
    /**
     * Sets the variance between scheduled and actual arrival time in seconds
     * 
     * @param arrivalVariance The arrival variance to set
     */
    public void setArrivalVariance(Integer arrivalVariance) {
        this.arrivalVariance = arrivalVariance;
    }
    
    /**
     * Gets the variance between scheduled and actual departure time in seconds
     * 
     * @return The departure variance in seconds (positive means late, negative means early)
     */
    public Integer getDepartureVariance() {
        return departureVariance;
    }
    
    /**
     * Sets the variance between scheduled and actual departure time in seconds
     * 
     * @param departureVariance The departure variance to set
     */
    public void setDepartureVariance(Integer departureVariance) {
        this.departureVariance = departureVariance;
    }
    
    /**
     * Builder class for ActualStopTimeDTO
     * Implements the Builder design pattern for easier object creation
     */
    public static class Builder {
        private Integer actualStopTimeId;
        private Integer actualTripId;
        private Integer seqStopId;
        private Integer stopId;
        private String stopName;
        private Boolean isStation;
        private Integer stopSequence;
        private Time scheduledArrival;
        private Time scheduledDeparture;
        private Time actualArrival;
        private Time actualDeparture;
        private Integer arrivalVariance;
        private Integer departureVariance;
        
        public Builder() {
            // Initialize with default values
        }
        
        public Builder setActualStopTimeId(Integer actualStopTimeId) {
            this.actualStopTimeId = actualStopTimeId;
            return this;
        }
        
        public Builder setActualTripId(Integer actualTripId) {
            this.actualTripId = actualTripId;
            return this;
        }
        
        public Builder setSeqStopId(Integer seqStopId) {
            this.seqStopId = seqStopId;
            return this;
        }
        
        public Builder setStopId(Integer stopId) {
            this.stopId = stopId;
            return this;
        }
        
        public Builder setStopName(String stopName) {
            this.stopName = stopName;
            return this;
        }

        public Builder setIsStation(Boolean isStation) {
            this.isStation = isStation;
            return this;
        }        
        
        public Builder setStopSequence(Integer stopSequence) {
            this.stopSequence = stopSequence;
            return this;
        }
        
        public Builder setScheduledArrival(Time scheduledArrival) {
            this.scheduledArrival = scheduledArrival;
            return this;
        }
        
        public Builder setScheduledDeparture(Time scheduledDeparture) {
            this.scheduledDeparture = scheduledDeparture;
            return this;
        }
        
        public Builder setActualArrival(Time actualArrival) {
            this.actualArrival = actualArrival;
            return this;
        }
        
        public Builder setActualDeparture(Time actualDeparture) {
            this.actualDeparture = actualDeparture;
            return this;
        }
        
        public Builder setArrivalVariance(Integer arrivalVariance) {
            this.arrivalVariance = arrivalVariance;
            return this;
        }
        
        public Builder setDepartureVariance(Integer departureVariance) {
            this.departureVariance = departureVariance;
            return this;
        }
        
        public ActualStopTimeDTO build() {
            return new ActualStopTimeDTO(actualStopTimeId, actualTripId, seqStopId,
                                       stopId, stopName, isStation, stopSequence,
                                       scheduledArrival, scheduledDeparture,
                                       actualArrival, actualDeparture,
                                       arrivalVariance, departureVariance);
        }
    }
    
    /**
     * Creates a new Builder instance
     * 
     * @return A new Builder for ActualStopTimeDTO
     */
    public static Builder builder() {
        return new Builder();
    }
}
