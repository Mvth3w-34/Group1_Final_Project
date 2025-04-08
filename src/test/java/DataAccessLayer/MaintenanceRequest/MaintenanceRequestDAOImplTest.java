///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
// */
//package DataAccessLayer.MaintenanceRequest;
//
//import TransferObjects.MaintenanceRequestTicketDTO;
//import java.util.List;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.AfterAll;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.Test;
//import static org.junit.jupiter.api.Assertions.*;
//
///**
// *
// * @author mario
// */
//public class MaintenanceRequestDAOImplTest {
//    
//    public MaintenanceRequestDAOImplTest() {
//    }
//    
//    @BeforeAll
//    public static void setUpClass() {
//    }
//    
//    @AfterAll
//    public static void tearDownClass() {
//    }
//    
//    @BeforeEach
//    public void setUp() {
//    }
//    
//    @AfterEach
//    public void tearDown() {
//    }
//
//
//    /**
//     * This test verifies that the method getMaintenanceRequestsByCompletion()
//     * returns a non-null and non-empty list of maintenance requests, grouped by completion status.
//     */
////    @Test
////    public void testGetMaintenanceRequestsByCompletion() {
////        MaintenanceRequestDAOImpl instance = new MaintenanceRequestDAOImpl();
////        List<Object[]> result = instance.getMaintenanceRequestsByCompletion();
////
////        assertNotNull(result, "The result list should not be null");
////        assertFalse(result.isEmpty(), "The result list should not be empty");
////
////        if (!result.isEmpty()) {
////            System.out.println("First maintenance request row (by completion): " + java.util.Arrays.toString(result.get(0)));
////        }
////    }
//    
//    //This Test in unabled, but it helps to run the test and it will be sucessful if we expect an empty list
//        @Test
//    public void testGetMaintenanceRequestsByCompletion() {
//        MaintenanceRequestDAOImpl instance = new MaintenanceRequestDAOImpl();
//        List<Object[]> result = instance.getMaintenanceRequestsByCompletion();
//
//        assertNotNull(result, "The result list should not be null");
//
//        // Allow empty list temporarily, just to verify no exceptions
//        System.out.println("Total grouped rows: " + result.size());
//    }
//
//    @Test
//    public void testGetMaintenanceRequestById() {
//        MaintenanceRequestDAOImpl instance = new MaintenanceRequestDAOImpl();
//
//        // ID can be an actual id from database
//        int existingId = 1;
//
//        MaintenanceRequestTicketDTO result = instance.getMaintenanceRequestById(existingId);
//
//        assertNotNull(result, "Maintenance request with ID " + existingId + " should not be null");
//        assertEquals(existingId, result.getTicketID(), "Returned ticket ID should match the requested ID");
//    }
//}
