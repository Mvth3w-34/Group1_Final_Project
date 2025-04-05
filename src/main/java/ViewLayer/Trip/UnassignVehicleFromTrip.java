/* filename: UnassignVehicleFromTrip.java
 * date: Apr. 5th, 2025
 * authors: Stephanie Prystupa-Maule
 * course: CST8288 O.O.P. with Design Patterns - Lab Section 023 
 * professor: Samira Ouaaz
 * coursework: Final Project - Public Transit Management System
 */

package ViewLayer.Trip;
        
import BusinessLayer.TripScheduleBusinessLogic;
import BusinessLayer.VehiclesBusinessLogic;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import TransferObjects.CredentialsDTO;
import TransferObjects.TripScheduleDTO;
import TransferObjects.VehicleDTO;

/**
 * Servlet for unassigning vehicles from trip schedules
 * 
 * @author Stephanie Prystupa-Maule
 * @version 1.0
 * @since 04/05/2025
 */
public class UnassignVehicleFromTrip extends HttpServlet {
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

        // Process form submission for vehicle unassignment
        if ("POST".equalsIgnoreCase(request.getMethod()) && request.getParameter("confirm") != null) {
            // Confirm parameter indicates the user has confirmed the unassignment
            if (tripID != null && errorMessage.isEmpty()) {
                try {
                    // Get the trip with vehicle information
                    trip = tripScheduleLogic.getTripSchedule(tripID);
                    
                    if (trip != null && trip.hasVehicleAssigned()) {
                        // Get the vehicle to unassign
                        VehicleDTO vehicle = vehiclesLogic.getVehicle(trip.getVehicle().getVehicleID());
                        
                        if (vehicle != null) {
                            // Update vehicle's trip ID to null (unassign)
                            vehicle.setTripID(null);
                            vehiclesLogic.updateVehicleTripID(vehicle);
                            
                            // Update success message
                            successMessage = "Vehicle " + vehicle.getVIN() + " successfully unassigned from trip ID " + tripID;
                            
                            // Refresh trip data to reflect changes
                            trip = tripScheduleLogic.getTripSchedule(tripID);
                        } else {
                            errorMessage = "Vehicle not found.";
                        }
                    } else if (trip != null) {
                        errorMessage = "Trip ID " + tripID + " does not have a vehicle assigned.";
                    } else {
                        errorMessage = "Trip not found.";
                    }
                } catch (SQLException e) {
                    errorMessage = "Database error: " + e.getMessage();
                } catch (Exception e) {
                    errorMessage = "An error occurred during unassignment: " + e.getMessage();
                }
            }
        }
        
        // For both GET and POST, we need trip details if tripID is provided
        if (tripID != null && errorMessage.isEmpty() && trip == null) {
            try {
                // Get the trip schedule
                trip = tripScheduleLogic.getTripSchedule(tripID);
                
                if (trip == null) {
                    errorMessage = "No trip schedule found with ID: " + tripID;
                } else if (!trip.hasVehicleAssigned()) {
                    errorMessage = "Trip ID " + tripID + " does not have a vehicle assigned.";
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
            out.println("<title>Unassign Vehicle from Trip</title>");
            out.println("</head>");
            out.println("<body>");
            
            out.println("<h1>Unassign Vehicle from Trip Schedule</h1>");
            
            // Navigation menu
            out.println("<div style='margin-bottom:20px;'>");
            out.println("<a href='FrontController-URL?module=trip&action=view_all'>View All Trip Schedules</a> | ");
            out.println("<a href='FrontController-URL?module=trip&action=view_by_route'>View Schedules by Route</a> | ");
            out.println("<a href='FrontController-URL?module=trip&action=view_unassigned'>View Unassigned Trips</a> | ");
            out.println("<a href='FrontController-URL?module=trip&action=assign_vehicle'>Assign Vehicle to Trip</a> | ");
            out.println("<a href='FrontController-URL?module=trip&action=unassign_vehicle'>Unassign Vehicle from Trip</a> | ");
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
                out.println("<input type=\"hidden\" name=\"action\" value=\"unassign_vehicle\">");
                out.println("<label for=\"tripID\">Enter Trip ID:</label>");
                out.println("<input type=\"text\" name=\"tripID\" id=\"tripID\" required>");
                out.println("<input type=\"submit\" value=\"Continue\">");
                out.println("</form>");
                
                out.println("<p>Or view <a href=\"FrontController-URL?module=trip&action=view_all\">all trip schedules</a> to select one.</p>");
            } 
            // If trip is found and has a vehicle assigned, show confirmation form
            else if (trip != null && trip.hasVehicleAssigned() && successMessage.isEmpty()) {
                // Display trip details with vehicle info
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
                
                // Display confirmation form for unassignment
                out.println("<h3>Confirm Vehicle Unassignment</h3>");
                out.println("<p>Are you sure you want to unassign vehicle " + trip.getVehicle().getVIN() + 
                        " (" + trip.getVehicle().getVehicleType().name() + ") from this trip?</p>");
                out.println("<form action=\"UnassignVehicleFromTrip-URL\" method=\"post\">");
                out.println("<input type=\"hidden\" name=\"tripID\" value=\"" + trip.getTripScheduleId() + "\">");
                out.println("<input type=\"hidden\" name=\"confirm\" value=\"true\">");
                out.println("<input type=\"submit\" value=\"Confirm Unassignment\">");
                out.println("<a href=\"FrontController-URL?module=trip&action=view_all\" style=\"margin-left: 10px;\">Cancel</a>");
                out.println("</form>");
            } 
            // If the unassignment was successful, show the updated trip details
            else if (trip != null && !trip.hasVehicleAssigned() && !successMessage.isEmpty()) {
                // Display trip details without vehicle info since it was just unassigned
                out.println("<h3>Updated Trip Schedule Details</h3>");
                out.println("<table border=\"1\">");
                out.println("<tr><th>Field</th><th>Value</th></tr>");
                out.println("<tr><td>Trip ID</td><td>" + trip.getTripScheduleId() + "</td></tr>");
                out.println("<tr><td>Start Time</td><td>" + trip.getStartTime() + "</td></tr>");
                out.println("<tr><td>Route ID</td><td>" + trip.getRouteId() + "</td></tr>");
                out.println("<tr><td>Route Name</td><td>" + trip.getRouteName() + "</td></tr>");
                out.println("<tr><td>Direction</td><td>" + trip.getDirection() + "</td></tr>");
                out.println("<tr><td>Vehicle Assigned</td><td>No</td></tr>");
                out.println("</table>");
                
                // Option to assign a new vehicle
                out.println("<p><a href=\"FrontController-URL?module=trip&action=assign_vehicle&tripID=" + 
                        trip.getTripScheduleId() + "\">Assign a new vehicle to this trip</a></p>");
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
        return "Unassign Vehicle from Trip Schedule Servlet";
    }
}
