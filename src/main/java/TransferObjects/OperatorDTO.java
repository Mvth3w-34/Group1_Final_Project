/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TransferObjects;

/**
 *
 * @author johnt
 */
public class OperatorDTO {
    private int operatorID;
    private String name;
    private LoginDTO login;
    private UserType type;
    private String email;
    
    
    public enum UserType {
        OPERATOR,
        MANAGER
    }
    /**
     * Assigns the operator's ID
     * @param id 
     */
    
    public void setOperatorID(int id) {
        this.operatorID = id;
    }
    /**
     * Gives the operator a name
     * @param name 
     */
    
    public void setName(String name) {
        this.name = name;
    }
    /**
     * Assigns a login identifier to link the operator to the username/password
     * @param login 
     */
    
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
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public int getOperatorID() {
        return this.operatorID;
    }
    
    public String getName() {
        return this.name;
    }
    
    public LoginDTO getLogin() {
        return this.login;
    }
    public UserType getOperatorType() {
        return this.type;
    }
    
    public String getEmail() {
        return this.email;
    }
}
