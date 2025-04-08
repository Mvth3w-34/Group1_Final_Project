/* filename: CredentialsDTO.java
 * date: Mar. 25th, 2025
 * updated by: Stephanie Prystupa-Maule
 * original authors: George Kriger (2022)
 * course: CST8288 O.O.P. with Design Patterns - Lab Section 023 
 * professor: Samira Ouaaz
 * coursework: Final Project - Public Transit Management System
 */

package TransferObjects;

/**
 * Data Transfer Object for DBMS Credentials
 * Encapsulates data transferred between layers of the application.
 * 
 * @author Stephanie Prystupa-Maule
 * @version 1.0
 * @since 03/25/2025
 */

public class CredentialsDTO {
    private String username;
    private String password;

    /**
     * Gets the username
     * @return username portion of the credentials
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username
     * @param username username portion of the credentials
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the password
     * @return password portion of the credentials
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password
     * @param password password portion of the credentials
     */
    public void setPassword(String password) {
        this.password = password;
    }  
}

