/* filename: AssignVehicleToTrip.java
 * date: Apr. 5th, 2025
 * authors: Stephanie Prystupa-Maule
 * course: CST8288 O.O.P. with Design Patterns - Lab Section 023 
 * professor: Samira Ouaaz
 * coursework: Final Project - Public Transit Management System
 */

package ViewLayer.Trips;
        
import BusinessLayer.TripScheduleBusinessLogic;
import BusinessLayer.VehiclesBusinessLogic;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import TransferObjects.CredentialsDTO;
import TransferObjects.TripScheduleDTO;
import TransferObjects.VehicleDTO;

/**
 * Servlet for assigning vehicles to trip schedules
 * 
 * @author Stephanie Prystupa-Maule
 * @version 1.0
 * @since 04/05/2025
 */
public class AssignVehicleToTrip extends HttpServlet {
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
        
        // Initialize variables
        TripScheduleDTO trip = null;
        List<VehicleDTO> availableVehicles = null;
        String errorMessage = "";
        String successMessage = "";
        
        // Initialize business logic with credentials from session
        TripScheduleBusinessLogic tripScheduleLogic = new TripScheduleBusinessLogic(credentials);
        VehiclesBusinessLogic vehiclesLogic = new VehiclesBusinessLogic(credentials);
        
        // Get the tripID parameter
        String tripIDParam = request.getParameter("tripID");
        Integer tripID = null;
        
        // Check if tripID was provided
        if (tripIDParam != null && !tripIDParam.trim().isEmpty()) {
            try {
                tripID = Integer.parseInt(tripIDParam);
            } catch (NumberFormatException e) {
                errorMessage = "Invalid Trip ID format. Please enter a numeric value.";
            }
        }

        // Process form submission for vehicle assignment
        if ("POST".equalsIgnoreCase(request.getMethod())) {
            // Get the selected vehicle ID from the form
            String vehicleIDParam = request.getParameter("vehicleID");
            Integer vehicleID = null;
            
            if (vehicleIDParam != null && !vehicleIDParam.trim().isEmpty()) {
                try {
                    vehicleID = Integer.parseInt(vehicleIDParam);
                } catch (NumberFormatException e) {
                    errorMessage = "Invalid Vehicle ID format.";
                }
            } else {
                errorMessage = "No vehicle selected.";
            }
            
            // If we have valid IDs, update the assignment
            if (tripID != null && vehicleID != null && errorMessage.isEmpty()) {
                try {
                    // Get the vehicle and trip objects
                    VehicleDTO vehicle = vehiclesLogic.getVehicle(vehicleID);
                    trip = tripScheduleLogic.getTripSchedule(tripID);
                    
                    if (vehicle != null && trip != null) {
                        // Check if vehicle is already assigned to another trip
                        if (vehicle.getTripID() != null && !vehicle.getTripID().equals(tripID)) {
                            errorMessage = "Vehicle " + vehicle.getVIN() + " is already assigned to trip ID " + vehicle.getTripID();
                        } else {
                            // Update vehicle's trip ID
                            vehicle.setTripID(tripID);
                            vehiclesLogic.updateVehicleTripID(vehicle);
                            
                            // Update trip with vehicle reference
                            trip.setVehicle(vehicle);
                            
                            successMessage = "Vehicle " + vehicle.getVIN() + " successfully assigned to trip ID " + tripID;
                        }
                    } else {
                        errorMessage = "Vehicle or trip not found.";
                    }
                } catch (SQLException e) {
                    errorMessage = "Database error: " + e.getMessage();
                } catch (Exception e) {
                    errorMessage = "An error occurred during assignment: " + e.getMessage();
                }
            }
        }
        
        // For both GET and POST, we need trip details if tripID is provided
        if (tripID != null && errorMessage.isEmpty()) {
            try {
                // Get the trip schedule
                trip = tripScheduleLogic.getTripSchedule(tripID);
                
                if (trip == null) {
                    errorMessage = "No trip schedule found with ID: " + tripID;
                } else {
                    // Get list of unassigned vehicles
                    availableVehicles = vehiclesLogic.getUnassignedVehicles();
                }
            } catch (SQLException e) {
                errorMessage = "Database error: " + e.getMessage();
            } catch (Exception e) {
                errorMessage = "An error occurred while retrieving data: " + e.getMessage();
            }
        }
        
        try {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Assign Vehicle to Trip</title>");
            out.println("</head>");
            out.println("<body>");
            
            out.println("<h1>Assign Vehicle to Trip Schedule</h1>");
            
            // Navigation menu
            out.println("<div>");
            out.println("<a href='FrontController-URL?module=trip&action=view_all'>View All Trip Schedules</a> | ");
            out.println("<a href='FrontController-URL?module=trip&action=view_by_route'>View Schedules by Route</a> | ");
            out.println("<a href='FrontController-URL?module=trip&action=view_unassigned'>View Unassigned Trips</a> | ");
            out.println("<a href='FrontController-URL?module=trip&action=assign_vehicle'>Assign Vehicle to Trip</a> | ");
            out.println("<a href='FrontController-URL?module=trip&action=unassign_vehicle'>Unassign Vehicle From Trip</a> | ");            
            out.println("<a href='FrontController-URL?module=trip&action=return_to_menu'>Return to Main Menu</a>");
            out.println("</div>");   
            
            // Display success message if any
            if (!successMessage.isEmpty()) {
                out.println("<p style=\"color: green;\">" + successMessage + "</p>");
            }
            
            // Display error message if any
            if (!errorMessage.isEmpty()) {
                out.println("<p style=\"color: red;\">" + errorMessage + "</p>");
            }
            
            // If no tripID is specified, show form to search for a trip
            if (tripID == null) {
                out.println("<h3>Select a Trip Schedule</h3>");
                out.println("<form action=\"FrontController-URL\" method=\"get\">");
                out.println("<input type=\"hidden\" name=\"module\" value=\"trip\">");
                out.println("<input type=\"hidden\" name=\"action\" value=\"assign_vehicle\">");
                out.println("<label for=\"tripID\">Enter Trip ID:</label>");
                out.println("<input type=\"text\" name=\"tripID\" id=\"tripID\" required>");
                out.println("<input type=\"submit\" value=\"Continue\">");
                out.println("</form>");
                
                out.println("<p>Or view <a href=\"FrontController-URL?module=trip&action=view_unassigned\">unassigned trips</a> to select one.</p>");
            } 
            // If trip is found and doesn't have a vehicle assigned, show assignment form
            else if (trip != null && !trip.hasVehicleAssigned() && !availableVehicles.isEmpty()) {
                // Display trip details in the same format as ViewTripSchedule servlet
                out.println("<h3>Trip Schedule Details</h3>");
                out.println("<table border=\"1\">");
                out.println("<tr><th>Field</th><th>Value</th></tr>");
                out.println("<tr><td>Trip ID</td><td>" + trip.getTripScheduleId() + "</td></tr>");
                out.println("<tr><td>Start Time</td><td>" + trip.getStartTime() + "</td></tr>");
                out.println("<tr><td>Route ID</td><td>" + trip.getRouteId() + "</td></tr>");
                out.println("<tr><td>Route Name</td><td>" + trip.getRouteName() + "</td></tr>");
                out.println("<tr><td>Direction</td><td>" + trip.getDirection() + "</td></tr>");
                
                // Vehicle information section - should always show "No" in this section since we're assigning
                out.println("<tr><td>Vehicle Assigned</td><td>No</td></tr>");
                
                out.println("</table>");
                
                // Display form for vehicle selection
                out.println("<h3>Select a Vehicle to Assign</h3>");
                out.println("<form action=\"AssignVehicleToTrip-URL\" method=\"post\">");
                out.println("<input type=\"hidden\" name=\"tripID\" value=\"" + trip.getTripScheduleId() + "\">");
                out.println("<label for=\"vehicleID\">Select Vehicle:</label>");
                out.println("<select name=\"vehicleID\" id=\"vehicleID\" required>");
                
                // Add available vehicles to dropdown
                for (VehicleDTO vehicle : availableVehicles) {
                    out.println("<option value=\"" + vehicle.getVehicleID() + "\">" + 
                            vehicle.getVehicleType().name() + " - " + 
                            vehicle.getVIN() + ")</option>");
                }
                
                out.println("</select>");
                out.println("<input type=\"submit\" value=\"Assign Vehicle\">");
                out.println("</form>");
            } 
            // If trip has a vehicle already assigned
            else if (trip != null && trip.hasVehicleAssigned()) {
                // Display trip details with vehicle info in the same format as ViewTripSchedule servlet
                out.println("<h3>Trip Schedule Details</h3>");
                out.println("<table border=\"1\">");
                out.println("<tr><th>Field</th><th>Value</th></tr>");
                out.println("<tr><td>Trip ID</td><td>" + trip.getTripScheduleId() + "</td></tr>");
                out.println("<tr><td>Start Time</td><td>" + trip.getStartTime() + "</td></tr>");
                out.println("<tr><td>Route ID</td><td>" + trip.getRouteId() + "</td></tr>");
                out.println("<tr><td>Route Name</td><td>" + trip.getRouteName() + "</td></tr>");
                out.println("<tr><td>Direction</td><td>" + trip.getDirection() + "</td></tr>");
                
                // Vehicle information section
                out.println("<tr><td>Vehicle Assigned</td><td>Yes</td></tr>");
                out.println("<tr><td>Vehicle ID</td><td>" + trip.getVehicle().getVehicleID() + "</td></tr>");
                out.println("<tr><td>Vehicle Type</td><td>" + trip.getVehicle().getVehicleType().name() + "</td></tr>");
                out.println("<tr><td>Vehicle Number</td><td>" + trip.getVehicle().getVIN() + "</td></tr>");
                out.println("<tr><td>Maximum Passengers</td><td>" + trip.getVehicle().getMaxPassengers() + "</td></tr>");
                out.println("<tr><td>Fuel Type</td><td>" + trip.getVehicle().getFuelType() + "</td></tr>");
                out.println("<tr><td>Fuel Consumption Rate</td><td>" + String.format("%.2f", trip.getVehicle().getFuelRate()) + "</td></tr>");
                
                out.println("</table>");
                
                out.println("<p>This trip already has a vehicle assigned. To assign a different vehicle, first unassign the current vehicle.</p>");
                
                // Option to go back to unassigned trips
                out.println("<p><a href=\"FrontController-URL?module=trip&action=view_unassigned\">View Unassigned Trips</a></p>");
            }
            // No available vehicles
            else if (trip != null && availableVehicles != null && availableVehicles.isEmpty()) {
                out.println("<h3>Trip Schedule Details</h3>");
                out.println("<table border=\"1\">");
                out.println("<tr><th>Field</th><th>Value</th></tr>");
                out.println("<tr><td>Trip ID</td><td>" + trip.getTripScheduleId() + "</td></tr>");
                out.println("<tr><td>Start Time</td><td>" + trip.getStartTime() + "</td></tr>");
                out.println("<tr><td>Route ID</td><td>" + trip.getRouteId() + "</td></tr>");
                out.println("<tr><td>Route Name</td><td>" + trip.getRouteName() + "</td></tr>");
                out.println("<tr><td>Direction</td><td>" + trip.getDirection() + "</td></tr>");
                
                // Vehicle information section - always "No" in this case since we're in the "no vehicles available" section
                out.println("<tr><td>Vehicle Assigned</td><td>No</td></tr>");
                
                out.println("</table>");
                
                out.println("<p>There are no available vehicles to assign to this trip. All vehicles are currently assigned to other trips.</p>");
                out.println("<p>You may need to register more vehicles or unassign vehicles from other trips.</p>");
            }
            
            // Back to trips link
            out.println("<p><a href=\"FrontController-URL?module=trip&action=view_all\">Back to All Trip Schedules</a></p>");
            
            out.println("</body>");
            out.println("</html>");
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
        return "Assign Vehicle to Trip Schedule Servlet";
    }
}
