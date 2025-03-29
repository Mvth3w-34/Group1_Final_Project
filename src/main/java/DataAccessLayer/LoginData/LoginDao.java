/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package DataAccessLayer.LoginData;

import TransferObjects.LoginDTO;
import java.sql.SQLException;
import java.util.List;
import DataAccessLayer.TransitDaoInterface;

/**
 *
 * @author johnt
 */
public interface LoginDao extends TransitDaoInterface {
    
    /**
     * Gets a list of all stored credentials in the DB
     * @return A list of credentials
     * @throws SQLException 
     */
    public List<LoginDTO> getAllCredentials() throws SQLException;
    
}
