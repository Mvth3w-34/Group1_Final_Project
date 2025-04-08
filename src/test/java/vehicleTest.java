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

/**
 * This test checks that all vehicles in the database have a valid fuel consumption rate
 * greater than 0 and support at least one passenger.
 */
public class vehicleTest {

    @Test
    public void testVehiclesHaveValidConsumptionAndPassengerCount() {
        String url = "jdbc:mysql://localhost:3306/transit";
        String user = "mario";
        String password = "mario123";

        String query = "SELECT * FROM VEHICLES";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int vehicleId = rs.getInt("VEHICLE_ID");
                double fuelRate = rs.getDouble("FUEL_CONSUMPTION_RATE");
                int maxPassengers = rs.getInt("MAXIMUM_PASSENGERS");

                assertTrue(fuelRate > 0, "Fuel rate should be positive for Vehicle ID: " + vehicleId);
                assertTrue(maxPassengers >= 1, "Max passengers should be at least 1 for Vehicle ID: " + vehicleId);
                System.out.println("✅ Vehicle " + vehicleId + " passed validation.");
            }

        } catch (SQLException e) {
            fail("❌ Database error: " + e.getMessage());
        }
    }
}
