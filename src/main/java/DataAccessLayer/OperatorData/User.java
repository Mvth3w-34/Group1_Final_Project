/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package DataAccessLayer.OperatorData;

import TransferObjects.LoginDTO;

/**
 *
 * @author johnt
 */
public interface User {
    public enum UserType {
        OPERATOR,
        MANAGER
    }
    /**
     * Assigns the operator's ID
     * @param id 
     */
    public void setOperatorID(int id);
    /**
     * Gives the operator a name
     * @param name 
     */
    public void setName(String name);
    /**
     * Assigns a login identifier to link the operator to the username/password
     * @param login 
     */
    public void assignLogin(LoginDTO login);
    /**
     * Sets the operator's email
     * @param email 
     */
    public void setEmail(String email);
    public int getOperatorID();
    public String getName();
    public LoginDTO getLogin();
//    public UserType getOperatorType();
    public String getEmail();
}
