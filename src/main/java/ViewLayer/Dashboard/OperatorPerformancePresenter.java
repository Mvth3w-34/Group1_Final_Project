/* filename: OperatorPerformancePresenter.java
 * date: Apr. 6th, 2025
 * authors: Stephanie Prystupa-Maule
 * course: CST8288 O.O.P. with Design Patterns - Lab Section 023 
 * professor: Samira Ouaaz
 * coursework: Final Project - Public Transit Management System
 */
package ViewLayer.Dashboard;

import BusinessLayer.OperatorPerformanceBusinessLogic;
import BusinessLayer.TransitBusinessLayer;
//import TransferObjects.CredentialsDTO;
import TransferObjects.OperatorDTO;
import TransferObjects.OperatorPerformanceDTO;
import ViewLayer.Dashboard.OperatorPerformanceView;

import java.sql.SQLException;
import java.util.List;

/**
 * Presenter for Operator Performance in the MVP pattern.
 * Coordinates between the View and Business Logic layers.
 * 
 * @author Stephanie Prystupa-Maule
 * @version 1.0
 * @since 04/06/2025
 */
public class OperatorPerformancePresenter {

    private final OperatorPerformanceView view;
    private final OperatorPerformanceBusinessLogic businessLogic;

    // new session handling with raw credentials and authentication in session
    public OperatorPerformancePresenter(OperatorPerformanceView view, OperatorDTO operator, TransitBusinessLayer logicLayer) {
        this.view = view;
        this.businessLogic = new OperatorPerformanceBusinessLogic(operator, logicLayer);
        this.view.setPresenter(this);
    }    
    
      // old session handling, with encapsulation and resource management       
//    /**
//     * Constructor for the OperatorPerformancePresenter
//     * 
//     * @param view The view interface implementation
//     * @param credentials The credentials for data access authorization
//     */
//    public OperatorPerformancePresenter(OperatorPerformanceView view, CredentialsDTO credentials) {
//        this.view = view;
//        this.businessLogic = new OperatorPerformanceBusinessLogic(credentials);
//        this.view.setPresenter(this);
//    }

    /**
     * Loads and displays the operator performance data based on user filter settings
     */
    public void loadOperatorPerformance() {
        view.showLoading();
        
        try {
            String filterType = view.getFilterType();
            Double threshold = view.getThresholdValue();
            String sortMetric = view.getSortMetric();
            boolean ascending = view.getSortOrder();
            
            List<OperatorPerformanceDTO> operators;
            
            // Apply filters based on user selection
            if (threshold != null) {
                if ("above".equals(filterType)) {
                    operators = businessLogic.getOperatorsAboveOntimeThreshold(threshold);
                } else if ("below".equals(filterType)) {
                    operators = businessLogic.getOperatorsBelowOntimeThreshold(threshold);
                } else {
                    operators = businessLogic.getAllOperatorPerformance();
                }
            } else {
                operators = businessLogic.getAllOperatorPerformance();
            }
            
            // Apply sorting if needed
            if (sortMetric != null && !sortMetric.isEmpty()) {
                operators = businessLogic.sortByMetric(operators, sortMetric, ascending);
            }
            
            // Display the data in the view
            view.displayOperatorPerformance(operators);
            
            // Load and display summary statistics
            loadSummaryStatistics();
            
        } catch (SQLException e) {
            view.displayErrorMessage("Database error: " + e.getMessage());
        } catch (Exception e) {
            view.displayErrorMessage("An error occurred: " + e.getMessage());
        } finally {
            view.hideLoading();
        }
    }
    
    /**
     * Loads and displays summary statistics about overall performance
     */
    public void loadSummaryStatistics() {
        try {
            double avgOntimePerformance = businessLogic.calculateAverageOntimePerformance();
            view.displaySummaryStatistics(avgOntimePerformance);
        } catch (SQLException e) {
            // Just silently fail for summary stats, as they're supplementary
            // Or log the error if you have a logging framework
        }
    }
    
    /**
     * Retrieves detailed performance data for a specific operator
     * 
     * @param operatorId The ID of the operator to get details for
     * @return The operator performance details, or null if not found
     */
    public OperatorPerformanceDTO getOperatorDetails(int operatorId) {
        try {
            return businessLogic.getOperatorPerformanceById(operatorId);
        } catch (SQLException e) {
            view.displayErrorMessage("Error retrieving operator details: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Initializes the view with necessary data
     */
    public void initializeView() {
        view.initializeView();
        loadOperatorPerformance();
    }
    
    /**
     * Gets the top performing operators
     * 
     * @param count Number of top performers to retrieve
     * @return List of top performing operators
     */
    public List<OperatorPerformanceDTO> getTopPerformers(int count) {
        try {
            return businessLogic.getTopPerformingOperators(count);
        } catch (SQLException e) {
            view.displayErrorMessage("Error retrieving top performers: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Gets operators needing improvement
     * 
     * @param count Number of operators to retrieve
     * @return List of operators needing improvement
     */
    public List<OperatorPerformanceDTO> getOperatorsNeedingImprovement(int count) {
        try {
            return businessLogic.getOperatorsNeedingImprovement(count);
        } catch (SQLException e) {
            view.displayErrorMessage("Error retrieving operators needing improvement: " + e.getMessage());
            return null;
        }
    }
}