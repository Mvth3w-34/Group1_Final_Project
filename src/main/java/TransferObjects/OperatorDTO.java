/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TransferObjects;

import BusinessLayer.TransitBusinessLayer;
import DataAccessLayer.OperatorData.User;

/**
 *
 * @author johnt
 */
public class OperatorDTO implements User {
    private int operatorID;
    private String name;
    private LoginDTO login;
    private UserType type = UserType.OPERATOR;
    private String email;
    
    
    public enum UserType {
        OPERATOR,
        MANAGER
    }
    /**
     * Assigns the operator's ID
     * @param id 
     */
    @Override
    public void setOperatorID(int id) {
        this.operatorID = id;
    }
    /**
     * Gives the operator a name
     * @param name 
     */
    @Override
    public void setName(String name) {
        this.name = name;
    }
    /**
     * Assigns a login identifier to link the operator to the username/password
     * @param login 
     */
    @Override
    public void assignLogin(LoginDTO login) {
        this.login = login;
    }
    /**
     * Assigns the operator to be an operator or a manager
     * @param type 
     */
    public void setUserType(UserType type) {
        this.type = type;
    }
    /**
     * Sets the operator's email
     * @param email 
     */
    @Override
    public void setEmail(String email) {
        this.email = email;
    }
    @Override
    public int getOperatorID() {
        return this.operatorID;
    }
    @Override
    public String getName() {
        return this.name;
    }
    @Override
    public LoginDTO getLogin() {
        return this.login;
    }
    public UserType getOperatorType() {
        return this.type;
    }
    @Override
    public String getEmail() {
        return this.email;
    }
    public void registerVehicle(TransitBusinessLayer logicLayer) { 
        // No access to register vehicle
    }
}
