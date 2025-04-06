/* filename: LoginDTO.java
 * date: Apr. 6th, 2025
 * authors: John Tieu
 * course: CST8288 O.O.P. with Design Patterns - Lab Section 023 
 * professor: Samira Ouaaz
 * coursework: Final Project - Public Transit Management System
 */
package TransferObjects;

/**
 *
 * @author johnt
 */
public class LoginDTO {
    private int loginID;
    private String username;
    private String password;
    
    public void setLoginID(int id) {
        this.loginID = id;
    }
    
    public void setUsername(String user) {
        this.username = user;
    }
    public void setPassword(String pass) {
        this.password = pass;
    }
    public int getLoginID() {
        return this.loginID;
    }
    public String getUsername() {
        return this.username;
    }
    public String getPassword() {
        return this.password;
    }
}
