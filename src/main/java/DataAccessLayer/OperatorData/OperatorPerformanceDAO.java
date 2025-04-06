/* filename: OperatorPerformanceDAO.java
 * date: Apr. 6th, 2025
 * authors: Stephanie Prystupa-Maule, John Tieu
 * course: CST8288 O.O.P. with Design Patterns - Lab Section 023 
 * professor: Samira Ouaaz
 * coursework: Final Project - Public Transit Management System
 */
package DataAccessLayer.OperatorData;

import TransferObjects.OperatorPerformanceDTO;
import java.sql.SQLException;
import java.util.List;

/**
 * Data Access Object interface for Operator Performance
 * Defines the contract for retrieving data from the vw_operator_performance view
 * that collects data on arrival and departure punctuality by operator
 * 
 * @author Stephanie Prystupa-Maule
 * @version 1.0
 * @since 04/06/2025
 */
public interface OperatorPerformanceDAO {
    
    /**
     * Retrieves the record of an operator's performance by operator ID.
     * 
     * @param operatorId Integer value of the operator ID
     * @return OperatorPerformanceDTO The OperatorPerformanceDTO object matching the operatorId
     * @throws SQLException 
     */
    OperatorPerformanceDTO getOperatorPerformanceByID(Integer operatorId) throws SQLException;
    
    /**
     * Retrieves all operator performance records from the database
     * 
     * @return List of OperatorPerformanceDTO objects
     * @throws SQLException 
     */
    List<OperatorPerformanceDTO> getAllOperatorPerformance() throws SQLException;
        
}
