/* filename: OperatorPerformanceView.java
 * date: Apr. 6th, 2025
 * authors: Stephanie Prystupa-Maule
 * course: CST8288 O.O.P. with Design Patterns - Lab Section 023 
 * professor: Samira Ouaaz
 * coursework: Final Project - Public Transit Management System
 */
package ViewLayer.Dashboard;

import TransferObjects.OperatorPerformanceDTO;
import java.util.List;

/**
 * Interface defining the contract for the Operator Performance view.
 * Implements the View component of the Model-View-Presenter pattern.
 * 
 * @author Stephanie Prystupa-Maule
 * @version 1.0
 * @since 04/06/2025
 */
public interface OperatorPerformanceView {
    
    /**
     * Display the list of operator performance data
     * 
     * @param operators The list of operator performance DTOs to display
     */
    void displayOperatorPerformance(List<OperatorPerformanceDTO> operators);
    
    /**
     * Display an error message to the user
     * 
     * @param errorMessage The error message to display
     */
    void displayErrorMessage(String errorMessage);
    
    /**
     * Display a success message to the user
     * 
     * @param message The success message to display
     */
    void displaySuccessMessage(String message);
    
    /**
     * Display summary statistics for operator performance
     * 
     * @param avgOntimePerformance The average on-time performance across all operators
     */
    void displaySummaryStatistics(double avgOntimePerformance);
    
    /**
     * Get the sort metric selected by the user
     * 
     * @return The selected sort metric
     */
    String getSortMetric();
    
    /**
     * Get the sort order selected by the user
     * 
     * @return true for ascending, false for descending
     */
    boolean getSortOrder();
    
    /**
     * Get the filter type selected by the user
     * 
     * @return The selected filter type (e.g., "all", "above", "below")
     */
    String getFilterType();
    
    /**
     * Get the threshold value entered by the user
     * 
     * @return The threshold value, or null if not specified
     */
    Double getThresholdValue();
    
    /**
     * Initialize the view with appropriate controls and settings
     */
    void initializeView();
    
    /**
     * Method to inform the view that data is being loaded
     */
    void showLoading();
    
    /**
     * Method to inform the view that data loading is complete
     */
    void hideLoading();
    
    /**
     * Set the presenter for this view (for callbacks)
     * 
     * @param presenter The presenter to set
     */
    void setPresenter(OperatorPerformancePresenter presenter);
}