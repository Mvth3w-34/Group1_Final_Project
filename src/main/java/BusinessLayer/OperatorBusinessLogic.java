/* filename: OperatorBusinessLogic.java
 * date: Apr. 5th, 2025
 * author: Stephanie Prystupa-Maule
 * course: CST8288 O.O.P. with Design Patterns - Lab Section 023 
 * professor: Samira Ouaaz
 * coursework: Final Project - Public Transit Management System
 */
package BusinessLayer;

import DataAccessLayer.OperatorData.OperatorDAO;
import DataAccessLayer.OperatorData.OperatorDAOImpl;
import TransferObjects.CredentialsDTO;
import TransferObjects.OperatorDTO;
import java.sql.SQLException;
import java.util.List;

/**
 * Business logic layer for managing Operator data operations.
 * Handles the business rules for operator operations,
 * serving as an intermediary between the presentation and data access layers.
 * 
 * @author Stephanie Prystupa-Maule
 * @version 1.0
 * @since 04/05/2025
 */
public class OperatorBusinessLogic {
    
    private OperatorDAO operatorDAO = null;
    
    /**
     * Constructor that sets the credentials for the Data Source and initializes
     * the Operator DAO
     * 
     * @param creds The CredentialsDTO containing the user login credentials
     */
    public OperatorBusinessLogic(CredentialsDTO creds) {
        try{
            operatorDAO = new OperatorDAOImpl();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }
    
    /**
     * Retrieves an operator from the database by its ID.
     * 
     * @param operatorID Integer value of the operator ID
     * @return OperatorDTO The OepratorDTO object matching the operatorID
     * @throws SQLException 
     */
    public OperatorDTO getOperatorByID(Integer operatorID) throws SQLException{
        return operatorDAO.getOperatorByID(operatorID);
    }
    
    /**
     * Retrieves all operators from the database.
     * 
     * @return List of OperatorDTO objects
     * @throws SQLException 
     */
    public List<OperatorDTO> getAllOperators() throws SQLException {
        return operatorDAO.getAllOperators();
    }   
    
    /**
     * Adds a new operator record to the database.
     * 
     * @param operator The OperatorDTO to be added
     * @throws SQLException 
     */
    public void addOperator(OperatorDTO operator) throws SQLException {
        operatorDAO.addOperator(operator);
    }

    /**
     * Updates an operator record in the database.
     * 
     * @param operator The OperatorDTO to be updated
     * @throws SQLException 
     */
    public void updateOperator(OperatorDTO operator) throws SQLException {
        operatorDAO.updateOperator(operator);
    }    
    
    
    /**
     * Deletes an operator record from the database.
     * 
     * @param operator The OperatorDTO to be deleted
     * @throws SQLException 
     */
    public void deleteOperator(OperatorDTO operator) throws SQLException {
        operatorDAO.deleteOperator(operator);
    }  
    
    /**
     * Filters operators based on whether they are managers.
     * 
     * @param operators List of OperatorDTO objects to filter
     * @param isManager true to filter for managers,
     *                  false to filter for non-managers
     * @return List of filtered OperatorDTO objects
     */
    public List<OperatorDTO> filterByUserType(List<OperatorDTO> operators, boolean isManager) {
        return operators.stream()
                .filter(operator -> operator.isManager() == isManager)
                .collect(java.util.stream.Collectors.toList());
    }
    
    /**
     * Gets Operators that are Managers
     * 
     * @return List of OperatorDTO objects with Manager userType
     * @throws SQLException 
     */
    public List<OperatorDTO> getManagers() throws SQLException {
        List<OperatorDTO> allOperators = getAllOperators();
        return filterByUserType(allOperators, true);
    }
    
    /**
     * Gets Operators that are not Managers
     * 
     * @return List of OperatorDTO objects that don't have Manager userType
     * @throws SQLException 
     */
    public List<OperatorDTO> getNonManagers() throws SQLException {
        List<OperatorDTO> allOperators = getAllOperators();
        return filterByUserType(allOperators, false);
    }
}
