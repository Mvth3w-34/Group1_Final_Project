/* filename: OperatorPerformanceBusinessLogic.java
 * date: Apr. 6th, 2025
 * authors: Stephanie Prystupa-Maule
 * course: CST8288 O.O.P. with Design Patterns - Lab Section 023 
 * professor: Samira Ouaaz
 * coursework: Final Project - Public Transit Management System
 */
package BusinessLayer;

import DataAccessLayer.OperatorData.OperatorPerformanceDAO;
import DataAccessLayer.OperatorData.OperatorPerformanceDAOImpl;
import DataAccessLayer.TransitDataSource;
import TransferObjects.CredentialsDTO;
import TransferObjects.OperatorPerformanceDTO;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Business logic layer for managing Operator Performance data operations.
 * Handles performance metrics, analysis, and data retrieval for the operator performance dashboard.
 * 
 * @author Stephanie Prystupa-Maule
 * @version 1.0
 * @since 04/06/2025
 */
public class OperatorPerformanceBusinessLogic {
    
    private OperatorPerformanceDAO operatorPerformanceDAO = null;
    
    /**
     * Constructor that sets the credentials for the Data Source and initializes
     * the OperatorPerformance DAO
     * 
     * @param creds The CredentialsDTO containing the user login credentials
     */
    public OperatorPerformanceBusinessLogic(CredentialsDTO creds) {
        operatorPerformanceDAO = new OperatorPerformanceDAOImpl();
    }
    
    /**
     * Retrieves performance metrics for an operator by ID.
     * 
     * @param operatorId Integer value of the operator ID
     * @return OperatorPerformanceDTO with performance metrics
     * @throws SQLException 
     */
    public OperatorPerformanceDTO getOperatorPerformanceById(Integer operatorId) throws SQLException {
        return operatorPerformanceDAO.getOperatorPerformanceByID(operatorId);
    }
    
    /**
     * Retrieves performance metrics for all operators.
     * 
     * @return List of OperatorPerformanceDTO objects
     * @throws SQLException 
     */
    public List<OperatorPerformanceDTO> getAllOperatorPerformance() throws SQLException {
        return operatorPerformanceDAO.getAllOperatorPerformance();
    }
    
    /**
     * Gets operators with on-time performance above a threshold.
     * 
     * @param threshold The minimum on-time percentage required
     * @return List of OperatorPerformanceDTO objects meeting the threshold
     * @throws SQLException 
     */
    public List<OperatorPerformanceDTO> getOperatorsAboveOntimeThreshold(double threshold) throws SQLException {
        List<OperatorPerformanceDTO> allOperators = getAllOperatorPerformance();
        return allOperators.stream()
                .filter(op -> op.getOverallOntimePerformance() >= threshold)
                .collect(Collectors.toList());
    }
    
    /**
     * Gets operators with on-time performance below a threshold.
     * 
     * @param threshold The maximum on-time percentage allowed
     * @return List of OperatorPerformanceDTO objects below the threshold
     * @throws SQLException 
     */
    public List<OperatorPerformanceDTO> getOperatorsBelowOntimeThreshold(double threshold) throws SQLException {
        List<OperatorPerformanceDTO> allOperators = getAllOperatorPerformance();
        return allOperators.stream()
                .filter(op -> op.getOverallOntimePerformance() < threshold)
                .collect(Collectors.toList());
    }
    
    /**
     * Gets the top-performing operators based on overall on-time performance.
     * 
     * @param count The number of top operators to return
     * @return List of the top-performing OperatorPerformanceDTO objects
     * @throws SQLException 
     */
    public List<OperatorPerformanceDTO> getTopPerformingOperators(int count) throws SQLException {
        List<OperatorPerformanceDTO> allOperators = getAllOperatorPerformance();
        return allOperators.stream()
                .sorted(Comparator.comparing(OperatorPerformanceDTO::getOverallOntimePerformance).reversed())
                .limit(count)
                .collect(Collectors.toList());
    }
    
    /**
     * Gets the operators who need improvement based on overall on-time performance.
     * 
     * @param count The number of operators to return
     * @return List of the lowest-performing OperatorPerformanceDTO objects
     * @throws SQLException 
     */
    public List<OperatorPerformanceDTO> getOperatorsNeedingImprovement(int count) throws SQLException {
        List<OperatorPerformanceDTO> allOperators = getAllOperatorPerformance();
        return allOperators.stream()
                .sorted(Comparator.comparing(OperatorPerformanceDTO::getOverallOntimePerformance))
                .limit(count)
                .collect(Collectors.toList());
    }
    
    /**
     * Sorts operator performance data by a specified metric.
     * 
     * @param operators List of operator performance data to sort
     * @param metric The metric to sort by (e.g., "ontime", "early", "late")
     * @param ascending True for ascending order, false for descending
     * @return Sorted list of operator performance data
     */
    public List<OperatorPerformanceDTO> sortByMetric(List<OperatorPerformanceDTO> operators, String metric, boolean ascending) {
        Comparator<OperatorPerformanceDTO> comparator;
        
        switch (metric.toLowerCase()) {
            case "ontime":
                comparator = Comparator.comparing(OperatorPerformanceDTO::getOverallOntimePerformance);
                break;
            case "early":
                comparator = Comparator.comparing(op -> (op.getPctEarlyArrivals() + op.getPctEarlyDepartures()) / 2);
                break;
            case "late":
                comparator = Comparator.comparing(op -> (op.getPctLateArrivals() + op.getPctLateDepartures()) / 2);
                break;
            case "variance":
                comparator = Comparator.comparing(OperatorPerformanceDTO::getAvgCombinedVariance);
                break;
            case "trips":
                comparator = Comparator.comparing(OperatorPerformanceDTO::getTotalTrips);
                break;
            case "name":
                comparator = Comparator.comparing(OperatorPerformanceDTO::getOperatorName);
                break;
            default:
                comparator = Comparator.comparing(OperatorPerformanceDTO::getOperatorId);
        }
        
        if (!ascending) {
            comparator = comparator.reversed();
        }
        
        return operators.stream()
                .sorted(comparator)
                .collect(Collectors.toList());
    }
    
    /**
     * Calculates the average on-time performance across all operators.
     * 
     * @return Average on-time performance percentage
     * @throws SQLException 
     */
    public double calculateAverageOntimePerformance() throws SQLException {
        List<OperatorPerformanceDTO> allOperators = getAllOperatorPerformance();
        return allOperators.stream()
                .mapToDouble(OperatorPerformanceDTO::getOverallOntimePerformance)
                .average()
                .orElse(0.0);
    }
}