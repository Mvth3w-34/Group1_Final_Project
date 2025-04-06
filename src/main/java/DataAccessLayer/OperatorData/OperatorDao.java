/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package DataAccessLayer.OperatorData;
import TransferObjects.OperatorDTO;
import java.sql.SQLException;
import java.util.*;
import DataAccessLayer.TransitDaoInterface;

/**
 *
 * @author johnt
 */
public interface OperatorDao extends TransitDaoInterface {
    public List<OperatorDTO> getAllOperators() throws SQLException;
    public void registerOperator(OperatorDTO operator) throws SQLException;

}
