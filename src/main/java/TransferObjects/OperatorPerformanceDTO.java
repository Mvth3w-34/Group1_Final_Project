/* filename: OperatorPerformanceDTO.java
 * date: Apr. 6th, 2025
 * authors: Stephanie Prystupa-Maule
 * course: CST8288 O.O.P. with Design Patterns - Lab Section 023 
 * professor: Samira Ouaaz
 * coursework: Final Project - Public Transit Management System
 */
package TransferObjects;

/**
 * Data Transfer Object for Operator Performance metrics.
 * Represents data from the VW_OPERATOR_PERFORMANCE view.
 * Contains operator performance metrics including on-time performance,
 * early/late statistics, and variance data.
 * 
 * @author Stephanie Prystupa-Maule
 * @version 1.0
 * @since 04/06/2025
 */
public class OperatorPerformanceDTO {
    
    private Integer operatorId;
    private String operatorName;
    private Integer totalTrips;
    private Double avgCombinedVariance;
    
    // On-time metrics
    private Double pctOntimeArrivals;
    private Double pctOntimeDepartures;
    
    // Early metrics
    private Double pctEarlyArrivals;
    private Double avgEarlyArrivalTime;
    private Double pctEarlyDepartures;
    private Double avgEarlyDepartureTime;
    
    // Late metrics
    private Double pctLateArrivals;
    private Double avgLateArrivalTime;
    private Double pctLateDepartures;
    private Double avgLateDepartureTime;
    
    /**
     * Parameterized constructor for OperatorPerformanceDTO
     * Used by the Builder
     * 
     * @param operatorId The unique identifier for the operator
     * @param operatorName The name of the operator
     * @param totalTrips Total number of trips completed by the operator
     * @param avgCombinedVariance Average combined variance (in seconds)
     * @param pctOntimeArrivals Percentage of on-time arrivals
     * @param pctOntimeDepartures Percentage of on-time departures
     * @param pctEarlyArrivals Percentage of early arrivals
     * @param avgEarlyArrivalTime Average early arrival time (in seconds)
     * @param pctEarlyDepartures Percentage of early departures
     * @param avgEarlyDepartureTime Average early departure time (in seconds)
     * @param pctLateArrivals Percentage of late arrivals
     * @param avgLateArrivalTime Average late arrival time (in seconds)
     * @param pctLateDepartures Percentage of late departures
     * @param avgLateDepartureTime Average late departure time (in seconds)
     */
    private OperatorPerformanceDTO(Integer operatorId, String operatorName, 
                                  Integer totalTrips, Double avgCombinedVariance,
                                  Double pctOntimeArrivals, Double pctOntimeDepartures,
                                  Double pctEarlyArrivals, Double avgEarlyArrivalTime,
                                  Double pctEarlyDepartures, Double avgEarlyDepartureTime,
                                  Double pctLateArrivals, Double avgLateArrivalTime,
                                  Double pctLateDepartures, Double avgLateDepartureTime) {
        this.operatorId = operatorId;
        this.operatorName = operatorName;
        this.totalTrips = totalTrips;
        this.avgCombinedVariance = avgCombinedVariance;
        this.pctOntimeArrivals = pctOntimeArrivals;
        this.pctOntimeDepartures = pctOntimeDepartures;
        this.pctEarlyArrivals = pctEarlyArrivals;
        this.avgEarlyArrivalTime = avgEarlyArrivalTime;
        this.pctEarlyDepartures = pctEarlyDepartures;
        this.avgEarlyDepartureTime = avgEarlyDepartureTime;
        this.pctLateArrivals = pctLateArrivals;
        this.avgLateArrivalTime = avgLateArrivalTime;
        this.pctLateDepartures = pctLateDepartures;
        this.avgLateDepartureTime = avgLateDepartureTime;
    }
    
    /**
     * Gets the operator ID
     * 
     * @return The operator ID
     */
    public Integer getOperatorId() {
        return operatorId;
    }
    
    /**
     * Sets the operator ID
     * 
     * @param operatorId The operator ID to set
     */
    public void setOperatorId(Integer operatorId) {
        this.operatorId = operatorId;
    }
    
    /**
     * Gets the operator name
     * 
     * @return The operator name
     */
    public String getOperatorName() {
        return operatorName;
    }
    
    /**
     * Sets the operator name
     * 
     * @param operatorName The operator name to set
     */
    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }
    
    /**
     * Gets the total number of trips completed by the operator
     * 
     * @return The total number of trips
     */
    public Integer getTotalTrips() {
        return totalTrips;
    }
    
    /**
     * Sets the total number of trips completed by the operator
     * 
     * @param totalTrips The total number of trips to set
     */
    public void setTotalTrips(Integer totalTrips) {
        this.totalTrips = totalTrips;
    }
    
    /**
     * Gets the average combined variance in seconds
     * 
     * @return The average combined variance
     */
    public Double getAvgCombinedVariance() {
        return avgCombinedVariance;
    }
    
    /**
     * Sets the average combined variance in seconds
     * 
     * @param avgCombinedVariance The average combined variance to set
     */
    public void setAvgCombinedVariance(Double avgCombinedVariance) {
        this.avgCombinedVariance = avgCombinedVariance;
    }
    
    /**
     * Gets the percentage of on-time arrivals
     * 
     * @return The percentage of on-time arrivals
     */
    public Double getPctOntimeArrivals() {
        return pctOntimeArrivals;
    }
    
    /**
     * Sets the percentage of on-time arrivals
     * 
     * @param pctOntimeArrivals The percentage of on-time arrivals to set
     */
    public void setPctOntimeArrivals(Double pctOntimeArrivals) {
        this.pctOntimeArrivals = pctOntimeArrivals;
    }
    
    /**
     * Gets the percentage of on-time departures
     * 
     * @return The percentage of on-time departures
     */
    public Double getPctOntimeDepartures() {
        return pctOntimeDepartures;
    }
    
    /**
     * Sets the percentage of on-time departures
     * 
     * @param pctOntimeDepartures The percentage of on-time departures to set
     */
    public void setPctOntimeDepartures(Double pctOntimeDepartures) {
        this.pctOntimeDepartures = pctOntimeDepartures;
    }
    
    /**
     * Gets the percentage of early arrivals
     * 
     * @return The percentage of early arrivals
     */
    public Double getPctEarlyArrivals() {
        return pctEarlyArrivals;
    }
    
    /**
     * Sets the percentage of early arrivals
     * 
     * @param pctEarlyArrivals The percentage of early arrivals to set
     */
    public void setPctEarlyArrivals(Double pctEarlyArrivals) {
        this.pctEarlyArrivals = pctEarlyArrivals;
    }
    
    /**
     * Gets the average early arrival time in seconds
     * 
     * @return The average early arrival time
     */
    public Double getAvgEarlyArrivalTime() {
        return avgEarlyArrivalTime;
    }
    
    /**
     * Sets the average early arrival time in seconds
     * 
     * @param avgEarlyArrivalTime The average early arrival time to set
     */
    public void setAvgEarlyArrivalTime(Double avgEarlyArrivalTime) {
        this.avgEarlyArrivalTime = avgEarlyArrivalTime;
    }
    
    /**
     * Gets the percentage of early departures
     * 
     * @return The percentage of early departures
     */
    public Double getPctEarlyDepartures() {
        return pctEarlyDepartures;
    }
    
    /**
     * Sets the percentage of early departures
     * 
     * @param pctEarlyDepartures The percentage of early departures to set
     */
    public void setPctEarlyDepartures(Double pctEarlyDepartures) {
        this.pctEarlyDepartures = pctEarlyDepartures;
    }
    
    /**
     * Gets the average early departure time in seconds
     * 
     * @return The average early departure time
     */
    public Double getAvgEarlyDepartureTime() {
        return avgEarlyDepartureTime;
    }
    
    /**
     * Sets the average early departure time in seconds
     * 
     * @param avgEarlyDepartureTime The average early departure time to set
     */
    public void setAvgEarlyDepartureTime(Double avgEarlyDepartureTime) {
        this.avgEarlyDepartureTime = avgEarlyDepartureTime;
    }
    
    /**
     * Gets the percentage of late arrivals
     * 
     * @return The percentage of late arrivals
     */
    public Double getPctLateArrivals() {
        return pctLateArrivals;
    }
    
    /**
     * Sets the percentage of late arrivals
     * 
     * @param pctLateArrivals The percentage of late arrivals to set
     */
    public void setPctLateArrivals(Double pctLateArrivals) {
        this.pctLateArrivals = pctLateArrivals;
    }
    
    /**
     * Gets the average late arrival time in seconds
     * 
     * @return The average late arrival time
     */
    public Double getAvgLateArrivalTime() {
        return avgLateArrivalTime;
    }
    
    /**
     * Sets the average late arrival time in seconds
     * 
     * @param avgLateArrivalTime The average late arrival time to set
     */
    public void setAvgLateArrivalTime(Double avgLateArrivalTime) {
        this.avgLateArrivalTime = avgLateArrivalTime;
    }
    
    /**
     * Gets the percentage of late departures
     * 
     * @return The percentage of late departures
     */
    public Double getPctLateDepartures() {
        return pctLateDepartures;
    }
    
    /**
     * Sets the percentage of late departures
     * 
     * @param pctLateDepartures The percentage of late departures to set
     */
    public void setPctLateDepartures(Double pctLateDepartures) {
        this.pctLateDepartures = pctLateDepartures;
    }
    
    /**
     * Gets the average late departure time in seconds
     * 
     * @return The average late departure time
     */
    public Double getAvgLateDepartureTime() {
        return avgLateDepartureTime;
    }
    
    /**
     * Sets the average late departure time in seconds
     * 
     * @param avgLateDepartureTime The average late departure time to set
     */
    public void setAvgLateDepartureTime(Double avgLateDepartureTime) {
        this.avgLateDepartureTime = avgLateDepartureTime;
    }
    
    /**
     * Calculates the overall on-time performance percentage
     * (average of on-time arrivals and departures)
     * 
     * @return The overall on-time performance percentage
     */
    public Double getOverallOntimePerformance() {
        return (pctOntimeArrivals + pctOntimeDepartures) / 2.0;
    }
    
    /**
     * Calculates the overall punctuality score (0-100)
     * A weighted score based on on-time, early, and late percentages
     * 
     * @return The punctuality score
     */
    public Double getPunctualityScore() {
        // On-time is good (weight: 1.0)
        // Early is somewhat bad (weight: -0.5)
        // Late is bad (weight: -1.0)
        return getOverallOntimePerformance() - 
               (pctEarlyArrivals + pctEarlyDepartures) / 4.0 - 
               (pctLateArrivals + pctLateDepartures) / 2.0;
    } 
    
    /**
     * Builder class for OperatorPerformanceDTO
     * Implements the Builder design pattern for easier object creation
     */
    public static class Builder {
        private Integer operatorId;
        private String operatorName;
        private Integer totalTrips;
        private Double avgCombinedVariance;
        private Double pctOntimeArrivals;
        private Double pctOntimeDepartures;
        private Double pctEarlyArrivals;
        private Double avgEarlyArrivalTime;
        private Double pctEarlyDepartures;
        private Double avgEarlyDepartureTime;
        private Double pctLateArrivals;
        private Double avgLateArrivalTime;
        private Double pctLateDepartures;
        private Double avgLateDepartureTime;
        
        public Builder() {
            // Initialize with default values
        }
        
        public Builder setOperatorId(Integer operatorId) {
            this.operatorId = operatorId;
            return this;
        }
        
        public Builder setOperatorName(String operatorName) {
            this.operatorName = operatorName;
            return this;
        }
        
        public Builder setTotalTrips(Integer totalTrips) {
            this.totalTrips = totalTrips;
            return this;
        }
        
        public Builder setAvgCombinedVariance(Double avgCombinedVariance) {
            this.avgCombinedVariance = avgCombinedVariance;
            return this;
        }
        
        public Builder setPctOntimeArrivals(Double pctOntimeArrivals) {
            this.pctOntimeArrivals = pctOntimeArrivals;
            return this;
        }
        
        public Builder setPctOntimeDepartures(Double pctOntimeDepartures) {
            this.pctOntimeDepartures = pctOntimeDepartures;
            return this;
        }
        
        public Builder setPctEarlyArrivals(Double pctEarlyArrivals) {
            this.pctEarlyArrivals = pctEarlyArrivals;
            return this;
        }
        
        public Builder setAvgEarlyArrivalTime(Double avgEarlyArrivalTime) {
            this.avgEarlyArrivalTime = avgEarlyArrivalTime;
            return this;
        }
        
        public Builder setPctEarlyDepartures(Double pctEarlyDepartures) {
            this.pctEarlyDepartures = pctEarlyDepartures;
            return this;
        }
        
        public Builder setAvgEarlyDepartureTime(Double avgEarlyDepartureTime) {
            this.avgEarlyDepartureTime = avgEarlyDepartureTime;
            return this;
        }
        
        public Builder setPctLateArrivals(Double pctLateArrivals) {
            this.pctLateArrivals = pctLateArrivals;
            return this;
        }
        
        public Builder setAvgLateArrivalTime(Double avgLateArrivalTime) {
            this.avgLateArrivalTime = avgLateArrivalTime;
            return this;
        }
        
        public Builder setPctLateDepartures(Double pctLateDepartures) {
            this.pctLateDepartures = pctLateDepartures;
            return this;
        }
        
        public Builder setAvgLateDepartureTime(Double avgLateDepartureTime) {
            this.avgLateDepartureTime = avgLateDepartureTime;
            return this;
        }
        
        public OperatorPerformanceDTO build() {
            return new OperatorPerformanceDTO(
                operatorId, operatorName, totalTrips, avgCombinedVariance,
                pctOntimeArrivals, pctOntimeDepartures,
                pctEarlyArrivals, avgEarlyArrivalTime,
                pctEarlyDepartures, avgEarlyDepartureTime,
                pctLateArrivals, avgLateArrivalTime,
                pctLateDepartures, avgLateDepartureTime);
        }
    }
    
    /**
     * Creates a new Builder instance
     * 
     * @return A new Builder for OperatorPerformanceDTO
     */
    public static Builder builder() {
        return new Builder();
    }
}
