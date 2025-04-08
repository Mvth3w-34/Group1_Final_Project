///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
// */
//
///**
// *
// * @author mario
// */
//import static org.junit.jupiter.api.Assertions.*;
//import org.junit.jupiter.api.Test;
//
//import java.sql.*;
//
///**
// * This test checks that every VEHICLE_ID found in ENERGYFUELLOG exists in the VEHICLES table.
// */
//public class EnergyFuelVehicleIntegrityTest {
//
//    @Test
//    public void testEnergyFuelLogVehicleIdsExistInVehicles() {
//        String url = "jdbc:mysql://localhost:3306/transit";
//        String user = "mario";
//        String password = "mario123";
//
//        String query = "SELECT DISTINCT VEHICLE_ID FROM ENERGYFUELLOG";
//
//        try (Connection conn = DriverManager.getConnection(url, user, password);
//             PreparedStatement stmt = conn.prepareStatement(query);
//             ResultSet rs = stmt.executeQuery()) {
//
//            while (rs.next()) {
//                int vehicleId = rs.getInt("VEHICLE_ID");
//
//                // Validar que este ID existe en VEHICLES
//                String checkQuery = "SELECT COUNT(*) FROM VEHICLES WHERE VEHICLE_ID = ?";
//                try (PreparedStatement checkStmt = conn.prepareStatement(checkQuery)) {
//                    checkStmt.setInt(1, vehicleId);
//                    ResultSet checkRs = checkStmt.executeQuery();
//
//                    if (checkRs.next()) {
//                        int count = checkRs.getInt(1);
//                        assertTrue(count > 0, "Vehicle ID " + vehicleId + " in ENERGYFUELLOG does not exist in VEHICLES");
//                        System.out.println("✅ VEHICLE_ID " + vehicleId + " found in VEHICLES");
//                    }
//                }
//            }
//
//        } catch (SQLException e) {
//            fail("❌ Database error: " + e.getMessage());
//        }
//    }
//}
