/* filename: OperatorDTO.java
 * date: Apr. 5th, 2025
 * authors: Stephanie Prystupa-Maule, John Tieu
 * course: CST8288 O.O.P. with Design Patterns - Lab Section 023 
 * professor: Samira Ouaaz
 * coursework: Final Project - Public Transit Management System
 */
package TransferObjects;

//import TransferObjects.UserType;

/**
 * Data Transfer Object for Operators.
 * Represents data from the OPERATORS table.
 * Contains operator information along with related credential data.
 * 
 * @author johnt
 * @author Stephanie Prystupa-Maule
 * @version 2.0
 * @since 04/05/2025
 */
public class OperatorDTO {
    public Object getFirstname() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    public enum UserType {
        OPERATOR,
        MANAGER
    }
    
    private int operatorId;
    private String operatorName;
    private LoginDTO login;
    private int credentialId;
    private UserType userType;
    private String email;
    
    /**
     * Parameterized constructor for OperatorDTO
     * Used by the Builder
     * 
     * @param operatorId The unique identifier for the operator
     * @param operatorName The name of the operator
     * @param credentialId The credential identifier associated with this operator
     * @param userType The user type/role of the operator (OPERATOR or MANAGER)
     * @param email The email address of the operator
     */
    private OperatorDTO(int operatorId, String operatorName, int credentialId, 
                      UserType userType, String email) {
        this.operatorId = operatorId;
        this.operatorName = operatorName;
        this.credentialId = credentialId;
        this.userType = userType;
        this.email = email;
    }
    
    /**
     * No argument constructor for OperatorDTO
     * 
     */
    public OperatorDTO(){
    }
    
    /**
     * Gets the operator ID
     * 
     * @return The operator ID
     */
    public int getOperatorId() {
        return operatorId;
    }
    
    /**
     * Sets the operator ID
     * 
     * @param operatorId The operator ID to set
     */
    public void setOperatorId(int operatorId) {
        this.operatorId = operatorId;
    }
    
    /**
     * Gets the operator name
     * 
     * @return The operator name
     */
    public String getOperatorName() {
        return operatorName;
    }
    
    /**
     * Sets the operator name
     * 
     * @param operatorName The operator name to set
     */
    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }
    
    /**
     * Gets the credential ID associated with this operator
     * 
     * @return The credential ID
     */
    public int getCredentialId() {
        return credentialId;
    }
    
    /**
     * Sets the credential ID for this operator
     * 
     * @param credentialId The credential ID to set
     */
    public void setCredentialId(int credentialId) {
        this.credentialId = credentialId;
    }
    
    /**
     * Gets the user type/role
     * 
     * @return The user type (OPERATOR or MANAGER)
     */
    public UserType getUserType() {
        return userType;
    }
    
    /**
     * Sets the user type/role
     * 
     * @param userType The user type to set (OPERATOR or MANAGER)
     */
    public void setUserType(UserType userType) {
        this.userType = userType;
    }
    
    /**
     * Checks if this operator is a manager
     * 
     * @return true if the operator is a manager, false otherwise
     */
    public boolean isManager() {
        return userType == UserType.MANAGER;
    }
    
    /**
     * Gets the email address of the operator
     * 
     * @return The email address
     */
    public String getEmail() {
        return email;
    }
    
    /**
     * Sets the email address of the operator
     * 
     * @param email The email address to set
     */
    public void setEmail(String email) {
        this.email = email;
    }
    
    /**
     * Builder class for OperatorDTO
     * Implements the Builder design pattern for easier object creation
     */
    public static class Builder {
        private int operatorId;
        private String operatorName;
        private int credentialId;
        private LoginDTO login;
        private UserType userType;
        private String email;
        
        public Builder() {
            // Initialize with default values
        }
        
        public Builder setOperatorId(int operatorId) {
            this.operatorId = operatorId;
            return this;
        }
        
        public Builder setOperatorName(String operatorName) {
            this.operatorName = operatorName;
            return this;
        }
        
        public Builder setCredentialId(int credentialId) {
            this.credentialId = credentialId;
            return this;
        }
        
        public Builder setLogin(LoginDTO login) {
            this.login = login;
            return this;
        }
        
        public Builder setUserType(UserType userType) {
            this.userType = userType;
            return this;
        }
        
        public Builder setEmail(String email) {
            this.email = email;
            return this;
        }
        
        public OperatorDTO build() {
            return new OperatorDTO(operatorId, operatorName, credentialId, 
                                  userType, email);
        }
    }
    
    public void assignLogin(LoginDTO login) {
        this.login = login;
    }
    
    public LoginDTO getLogin() {
        return this.login;
    }
    
    
    /**
     * Creates a new Builder instance
     * 
     * @return A new Builder for OperatorDTO
     */
    public static Builder builder() {
        return new Builder();
    }
}
