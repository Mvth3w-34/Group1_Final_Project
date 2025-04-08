/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author mario
 */
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.sql.*;

public class CredentialsTest {

    @Test
    public void testTestUserExists() {
        String url = "jdbc:mysql://localhost:3306/transit";
        String user = "mario";
        String password = "mario123";

        String query = "SELECT * FROM CREDENTIALS WHERE USERNAME = ? AND PASSWORD = ?";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, "user1");
            stmt.setString(2, "pass1");

            try (ResultSet rs = stmt.executeQuery()) {
                assertTrue(rs.next(), "Expected to find user 'testUser' with password 'testPass'");

                int credentialId = rs.getInt("CREDENTIAL_ID");
                System.out.println("✅ testUser found with CredentialID: " + credentialId);
            }

        } catch (SQLException e) {
            fail("❌ Database error: " + e.getMessage());
        }
    }
}
