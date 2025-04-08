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

public class OperatorTest {

    @Test
    public void testBobSmithExists() {
        String url = "jdbc:mysql://localhost:3306/transit";
        String user = "mario";
        String password = "mario123";

        String query = "SELECT * FROM OPERATORS WHERE OPERATOR_NAME = ?";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, "Bob Smith");

            try (ResultSet rs = stmt.executeQuery()) {
                assertTrue(rs.next(), "Expected to find operator named 'Bob Smith'");

                int operatorId = rs.getInt("OPERATOR_ID");
                String type = rs.getString("OPERATOR_USER");

                assertEquals("MANAGER", type);
                System.out.println("✅ Bob Smith found (ID: " + operatorId + ", Type: " + type + ")");
            }

        } catch (SQLException e) {
            fail("❌ Database error: " + e.getMessage());
        }
    }

    /**
     * This test checks that a non-existent operator (by name) is not found in the database.
     */

    @Test
    public void testNonExistentOperatorNotFound() {
        String url = "jdbc:mysql://localhost:3306/transit";
        String user = "mario";
        String password = "mario123";

        String query = "SELECT * FROM OPERATORS WHERE OPERATOR_NAME = ?";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, "Ghost User");

            try (ResultSet rs = stmt.executeQuery()) {
                assertFalse(rs.next(), "No operator should be found with name 'Ghost User'");
                System.out.println("✅ Operator 'Ghost User' was correctly not found.");
            }

        } catch (SQLException e) {
            fail("❌ Database error: " + e.getMessage());
        }
    }
    
    /**
     * This test checks that the operator 'Joe Clarke' has the correct email in the database.
     * This test should fail since email is wrong
     */
//    @Test
//    public void testOperatorEmailIsCorrect() {
//        String url = "jdbc:mysql://localhost:3306/transit";
//        String user = "mario";
//        String password = "mario123";
//
//        String query = "SELECT * FROM OPERATORS WHERE OPERATOR_NAME = ?";
//
//        try (Connection conn = DriverManager.getConnection(url, user, password);
//             PreparedStatement stmt = conn.prepareStatement(query)) {
//
//            stmt.setString(1, "Joe Clarke");
//
//            try (ResultSet rs = stmt.executeQuery()) {
//                assertTrue(rs.next(), "Expected to find operator 'Joe Clarke'");
//
//                String email = rs.getString("EMAIL");
//                assertEquals("joedarke@darke.com", email);
//                System.out.println("✅ Joe Clarke has correct email: " + email);
//            }
//
//        } catch (SQLException e) {
//            fail("❌ Database error: " + e.getMessage());
//        }
//    }
//
//
    }