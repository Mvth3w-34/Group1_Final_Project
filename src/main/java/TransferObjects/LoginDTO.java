/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TransferObjects;

/**
 *
 * @author johnt
 */
public class LoginDTO {
    private String username;
    private String password;
    
    public void setUsername(String user) {
        this.username = user;
    }
    public void setPassword(String pass) {
        this.password = pass;
    }
    public String getUsername() {
        return this.username;
    }
    public String getPassword() {
        return this.password;
    }
}
