/* filename: SimulateTrip.java
 * date: Apr. 6th, 2025
 * authors: Stephanie Prystupa-Maule
 * course: CST8288 O.O.P. with Design Patterns - Lab Section 023 
 * professor: Samira Ouaaz
 * coursework: Final Project - Public Transit Management System
 */

package ViewLayer.Trip;

import BusinessLayer.TripExecutionBusinessLogic;
import BusinessLayer.TripScheduleBusinessLogic;
import BusinessLayer.VehiclesBusinessLogic;
import BusinessLayer.OperatorBusinessLogic;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import TransferObjects.CredentialsDTO;
import TransferObjects.TripScheduleDTO;
import TransferObjects.VehicleDTO;
import TransferObjects.OperatorDTO;

/**
 * Servlet for simulating a trip with random time variances
 * 
 * @author Stephanie Prystupa-Maule
 * @version 1.1
 * @since 04/06/2025
 */
public class SimulateTrip extends HttpServlet {
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
    
        // Get session and check for credentials
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("credentials") == null) {
            // If no valid session - redirect to login
            response.sendRedirect("index.html?error=timeout");
            return;
        }
        
        // Get credentials from session
        CredentialsDTO credentials = (CredentialsDTO) session.getAttribute("credentials");
        
        // Initialize business logic with credentials from session
        TripExecutionBusinessLogic tripExecutionLogic = new TripExecutionBusinessLogic(credentials);
        TripScheduleBusinessLogic tripScheduleLogic = new TripScheduleBusinessLogic(credentials);
        VehiclesBusinessLogic vehiclesLogic = new VehiclesBusinessLogic(credentials);
        OperatorBusinessLogic operatorLogic = new OperatorBusinessLogic(credentials);
        
        // Determine if this is a GET (display form) or POST (process form) request
        String method = request.getMethod();
        
        // Get parameters that might be passed from other pages
        String preselectedTripId = request.getParameter("scheduledTripId");
        String preselectedVehicleId = request.getParameter("vehicleId");
        
        try {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Simulate Trip Execution</title>");

            out.println("</head>");
            out.println("<body>");
            
            out.println("<h1>Simulate Trip Execution</h1>");
            
            // Navigation menu
            out.println("<div style='margin-bottom:20px;'>");
            out.println("<a href='FrontController-URL?module=trip&action=view_all'>View All Trip Schedules</a> | ");
            out.println("<a href='FrontController-URL?module=trip&action=view_by_route'>View Schedules by Route</a> | ");
            out.println("<a href='FrontController-URL?module=trip&action=view_unassigned'>View Unassigned Trips</a> | ");
            out.println("<a href='FrontController-URL?module=trip&action=assign_vehicle'>Assign Vehicle to Trip</a> | ");
            out.println("<a href='FrontController-URL?module=trip&action=simulate_trip'>Simulate Trip</a> | ");
            out.println("<a href='FrontController-URL?module=trip&action=return_to_menu'>Return to Main Menu</a>");
            out.println("</div>");
            
            // If this is a POST request, process the form
            if (method.equals("POST")) {
                String scheduledTripIdStr = request.getParameter("scheduledTripId");
                String operatorIdStr = request.getParameter("operatorId");
                String vehicleIdStr = request.getParameter("vehicleId");
                String tripDateStr = request.getParameter("tripDate");
                String punctualityLevel = request.getParameter("punctualityLevel");
                
                // Validation flag
                boolean isValid = true;
                StringBuilder errorMessage = new StringBuilder();
                
                // Validate and parse the parameters
                Integer scheduledTripId = null;
                try {
                    scheduledTripId = Integer.parseInt(scheduledTripIdStr);
                } catch (NumberFormatException e) {
                    isValid = false;
                    errorMessage.append("Invalid Trip ID format.<br>");
                }
                
                Integer operatorId = null;
                try {
                    operatorId = Integer.parseInt(operatorIdStr);
                } catch (NumberFormatException e) {
                    isValid = false;
                    errorMessage.append("Invalid Operator ID format.<br>");
                }
                
                Integer vehicleId = null;
                try {
                    vehicleId = Integer.parseInt(vehicleIdStr);
                } catch (NumberFormatException e) {
                    isValid = false;
                    errorMessage.append("Invalid Vehicle ID format.<br>");
                }
                
                Date tripDate = null;
                try {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    java.util.Date parsedDate = dateFormat.parse(tripDateStr);
                    tripDate = new Date(parsedDate.getTime());
                } catch (ParseException e) {
                    isValid = false;
                    errorMessage.append("Invalid date format. Please use YYYY-MM-DD format.<br>");
                }
                
                if (punctualityLevel == null || 
                    !(punctualityLevel.equals("HIGH") || 
                      punctualityLevel.equals("MEDIUM") || 
                      punctualityLevel.equals("LOW"))) {
                    isValid = false;
                    errorMessage.append("Invalid punctuality level. Must be HIGH, MEDIUM, or LOW.<br>");
                }
                
                // If the validation passed, simulate the trip
                if (isValid) {
                    try {
                        tripExecutionLogic.simulateTrip(
                            scheduledTripId, 
                            operatorId, 
                            vehicleId, 
                            tripDate, 
                            punctualityLevel
                        );
                        
                        out.println("<p style='color: green;'>Trip simulation successful!</p>");
                    } catch (SQLException e) {
                        out.println("<p style='color: red;'>Database error: " + e.getMessage() + "</p>");
                    } catch (IllegalArgumentException e) {
                        out.println("<p style='color: red;'>Validation error: " + e.getMessage() + "</p>");
                    }
                } else {
                    out.println("<p style='color: red;'>" + errorMessage.toString() + "</p>");
                }
            }
            
            // Get data for form dropdowns
            List<TripScheduleDTO> tripSchedules = null;
            List<VehicleDTO> vehicles = null;
            List<OperatorDTO> operators = null;
            
            try {
                tripSchedules = tripScheduleLogic.getAllTripSchedules();
                vehicles = vehiclesLogic.getAllVehicles();
                operators = operatorLogic.getNonManagers(); // Get only non-manager operators
            } catch (SQLException e) {
                out.println("<p style='color: red;'>Error loading data: " + e.getMessage() + "</p>");
            }
            
            // Display the form
            out.println("<form action='SimulateTrip-URL' method='post'>");
            out.println("<table>");
            
            // Trip Schedule selection
            out.println("<tr>");
            out.println("<td><label for='scheduledTripId'>Select Trip Schedule:</label></td>");
            out.println("<td><select name='scheduledTripId' id='scheduledTripId' required>");
            if (tripSchedules != null) {
                for (TripScheduleDTO trip : tripSchedules) {
                    boolean isSelected = preselectedTripId != null && 
                                         String.valueOf(trip.getTripScheduleId()).equals(preselectedTripId);
                    
                    out.println("<option value='" + trip.getTripScheduleId() + "'" + 
                                (isSelected ? " selected" : "") + ">");
                    out.println("ID: " + trip.getTripScheduleId() + 
                               " - Route: " + trip.getRouteName() + 
                               " - " + trip.getDirection() +
                               " at " + trip.getStartTime());
                    out.println("</option>");
                }
            }
            out.println("</select></td>");
            out.println("</tr>");
            
            // Operator dropdown selection from database
            out.println("<tr>");
            out.println("<td><label for='operatorId'>Select Operator:</label></td>");
            out.println("<td><select name='operatorId' id='operatorId' required>");
            if (operators != null && !operators.isEmpty()) {
                for (OperatorDTO operator : operators) {
                    out.println("<option value='" + operator.getOperatorId() + "'>");
                    out.println(operator.getOperatorName() + " (ID: " + operator.getOperatorId() + ")");
                    out.println("</option>");
                }
            } else {
                out.println("<option value=''>No operators available</option>");
            }
            out.println("</select></td>");
            out.println("</tr>");
            
            // Vehicle selection
            out.println("<tr>");
            out.println("<td><label for='vehicleId'>Select Vehicle:</label></td>");
            out.println("<td><select name='vehicleId' id='vehicleId' required>");
            if (vehicles != null) {
                for (VehicleDTO vehicle : vehicles) {
                    boolean isSelected = preselectedVehicleId != null && 
                                         String.valueOf(vehicle.getVehicleID()).equals(preselectedVehicleId);
                    
                    out.println("<option value='" + vehicle.getVehicleID() + "'" + 
                                (isSelected ? " selected" : "") + ">");
                    out.println("ID: " + vehicle.getVehicleID() + 
                               " - Type: " + vehicle.getVehicleType() + 
                               " - VIN: " + vehicle.getVIN());
                    out.println("</option>");
                }
            }
            out.println("</select></td>");
            out.println("</tr>");
            
            // Trip Date input
            out.println("<tr>");
            out.println("<td><label for='tripDate'>Trip Date:</label></td>");
            
            // Get current date in the required format
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String currentDate = dateFormat.format(new java.util.Date());
            
            out.println("<td><input type='date' name='tripDate' id='tripDate' required value='" + currentDate + "'></td>");
            out.println("</tr>");
            
            // Punctuality Level selection
            out.println("<tr>");
            out.println("<td><label for='punctualityLevel'>Punctuality Level:</label></td>");
            out.println("<td><select name='punctualityLevel' id='punctualityLevel' required>");
            out.println("<option value='HIGH'>HIGH - Small variances (-60 to 120 seconds)</option>");
            out.println("<option value='MEDIUM'>MEDIUM - Moderate variances (-180 to 300 seconds)</option>");
            out.println("<option value='LOW'>LOW - Large variances (-300 to 900 seconds)</option>");
            out.println("</select></td>");
            out.println("</tr>");
            
            // Submit button
            out.println("<tr>");
            out.println("<td colspan='2'><input type='submit' value='Simulate Trip'></td>");
            out.println("</tr>");
            
            out.println("</table>");
            
            out.println("</form>");
            
            // Link back to view all trip schedules
            out.println("<p><a href=\"FrontController-URL?module=trip&action=view_all\">Back to All Trip Schedules</a></p>");
            
            out.println("</body>");
            out.println("</html>");
        } catch (Exception e) {
            out.println("<p style='color: red;'>Error: " + e.getMessage() + "</p>");
        } finally {
            out.close();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Servlet for simulating trip execution";
    }
}
