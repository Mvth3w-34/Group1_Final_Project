/* filename: OperatorDAO.java
 * date: Apr. 5th, 2025
 * authors: Stephanie Prystupa-Maule, John Tieu
 * course: CST8288 O.O.P. with Design Patterns - Lab Section 023 
 * professor: Samira Ouaaz
 * coursework: Final Project - Public Transit Management System
 */
package DataAccessLayer.OperatorData;

import TransferObjects.OperatorDTO;
import java.sql.SQLException;
import java.util.List;

/**
 * Data Access Object interface for Operators
 * Defines the contract for database operations on operators table.
 * 
 * @author johnt
 * @author Stephanie Prystupa-Maule
 * @version 2.0
 * @since 04/05/2025
 */
public interface OperatorDAO {
    /**
     * Retrieves an operator from the database by its ID.
     * 
     * @param operatorID Integer value of the operator ID
     * @return OperatorDTO The OepratorDTO object matching the operatorID
     * @throws SQLException 
     */
    OperatorDTO getOperatorByID(Integer operatorID) throws SQLException;
    
    /**
     * Retrieves all operators from the database.
     * 
     * @return List of OperatorDTO objects
     * @throws SQLException 
     */
    List<OperatorDTO> getAllOperators() throws SQLException;
    
    /**
     * Adds a new operator record to the database.
     * 
     * @param operator The OperatorDTO to be added
     * @throws SQLException 
     */
    void addOperator(OperatorDTO operator) throws SQLException;
    
    /**
     * Updates an operator record in the database.
     * 
     * @param operator The OperatorDTO to be updated
     * @throws SQLException 
     */
    void updateOperator(OperatorDTO operator) throws SQLException;
    
    /**
     * Deletes an operator record from the database.
     * 
     * @param operator The OperatorDTO to be deleted
     * @throws SQLException 
     */
    void deleteOperator(OperatorDTO operator) throws SQLException;
    
}
