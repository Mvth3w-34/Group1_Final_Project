/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package BusinessLayer;

import TransferObjects.EnergyFuelDTO;
import TransferObjects.OperatorDTO;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 *
 * @author mario
 */
public class TransitBusinessLayerTest {
    
    public TransitBusinessLayerTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }

    @Test
    public void testValidateCredentials() throws Exception {
        TransitBusinessLayer instance = new TransitBusinessLayer();
        OperatorDTO result = instance.validateCredentials("testUser", "testPass");

        assertNotNull(result, "Should return a valid operator");
        assertEquals("admin", result.getLogin().getUsername());
    }
    @Test
    public void testGetAllEnergyFuelLogs() throws Exception {
        TransitBusinessLayer instance = new TransitBusinessLayer();
        List<EnergyFuelDTO> result = instance.getAllEnergyFuelLogs();

        // Check that the result is not null
        assertNotNull(result, "The list of energy fuel logs should not be null");

        // Optional: print the first log (for debugging)
        if (!result.isEmpty()) {
            System.out.println("First log: " + result.get(0).getVehicleId());
        }
}

    @Test
    public void testRegisterVehicle() throws Exception {
        System.out.println("registerVehicle");

        String vType = "Bus";                       // Example vehicle type
        String vin = "TESTVIN12345";                // Use a unique VIN for testing
        String fuelType = "Diesel";                 // Valid fuel type
        float fuelRate = 10.5F;                     // Average fuel consumption
        int maxPass = 50;                           // Capacity

        TransitBusinessLayer instance = new TransitBusinessLayer();

        // Act: Try to register a vehicle
        assertDoesNotThrow(() -> {
            instance.registerVehicle(vType, vin, fuelType, fuelRate, maxPass);
        }, "registerVehicle should not throw an exception with valid input");

        // Optionally, verify if vehicle exists (if you have a getVehicleById method)
        // VehicleDTO registered = instance.getVehicleById(vin);
        // assertNotNull(registered);
    }

    /**
     * This test checks that the getRoutesID() method returns a non-null
     * and possibly non-empty list of route IDs. These IDs represent 
     * the currently available routes in the system.
     */
    @Test
    public void testGetRoutesID() throws Exception {
        TransitBusinessLayer instance = new TransitBusinessLayer();

        List<Integer> result = instance.getRoutesID();

        assertNotNull(result, "The list of route IDs should not be null");

        System.out.println("Route IDs: " + result);
    }

    /**
     * This test verifies that a new operator account can be successfully registered 
     * using valid input. It checks that the method does not throw an exception 
     * when called with proper values.
     */
    @Test
    public void testRegisterAccount() throws Exception {
        String name = "Test User";
        String email = "testuser@example.com";
        String username = "testuser123";
        String password = "testpass";
        String userType = "OPERATOR"; // or "MANAGER"

        TransitBusinessLayer instance = new TransitBusinessLayer();
        assertDoesNotThrow(() -> {
            instance.registerAccount(name, email, username, password, userType);
        }, "Account registration should not throw an exception with valid data");


    }
}
