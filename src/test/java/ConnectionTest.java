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

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//This is a connection test, it tests the user/password working properly in order to run the proyect
public class ConnectionTest {

    @Test
    public void testDatabaseConnectionIsValid() {
        String url = "jdbc:mysql://localhost:3306/transit";
        String user = "mario";
        String password = "mario123";

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            assertNotNull(conn, "Connection should not be null");
            assertTrue(conn.isValid(2), "Connection should be valid");
            System.out.println("✅ Connection established successfully.");
        } catch (SQLException e) {
            fail("❌ Connection failed: " + e.getMessage());
        }
    }
}

