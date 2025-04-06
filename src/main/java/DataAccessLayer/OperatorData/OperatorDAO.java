/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package DataAccessLayer.OperatorData;
import TransferObjects.OperatorDTO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 *
 * @author johnt
 */
public interface OperatorDAO{
    public List<OperatorDTO> getAllOperators() throws SQLException;
    OperatorDTO getOperatorByID(Integer operatorID) throws SQLException;
    public void addOperator(OperatorDTO operator) throws SQLException;
    public void closeConnection();
    void deleteOperator(OperatorDTO operator) throws SQLException;
    void updateOperator(OperatorDTO operator) throws SQLException;
    OperatorDTO extractOperatorFromResultSet(ResultSet rs) throws SQLException;
}
