/* filename: ViewTripSchedule.java
 * date: Apr. 5th, 2025
 * authors: Stephanie Prystupa-Maule
 * course: CST8288 O.O.P. with Design Patterns - Lab Section 023 
 * professor: Samira Ouaaz
 * coursework: Final Project - Public Transit Management System
 */
package ViewLayer.Trip;
        
import BusinessLayer.TripScheduleBusinessLogic;
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


/**
 * Servlet for viewing a single trip schedule by ID using session-based authentication
 * 
 * @author Stephanie Prystupa-Maule
 * @version 1.0
 * @since 04/05/2025
 */
public class ViewTripSchedule extends HttpServlet {
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
        
        // Get the tripID parameter (either from the request or from a form submission)
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
        
        // Only query the database if we have a valid tripID
        if (tripID != null) {
            try {
                // Initialize business logic with credentials from session
                TripScheduleBusinessLogic tripScheduleLogic = new TripScheduleBusinessLogic(credentials);
                
                // Get the specific trip schedule
                trip = tripScheduleLogic.getTripSchedule(tripID);
                
                if (trip == null) {
                    errorMessage = "No trip schedule found with ID: " + tripID;
                }
            } catch (SQLException e) {
                errorMessage = "Database error: " + e.getMessage();
            } catch (Exception e) {
                errorMessage = "An error occurred while retrieving trip schedule: " + e.getMessage();
            }
        }
        
        try {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>View Trip Schedule Details</title>");
            out.println("</head>");
            out.println("<body>");
            
            out.println("<h1>Trip Schedule Details</h1>");
            
            // Navigation menu
            out.println("<div>");
            out.println("<a href='FrontController-URL?module=trip&action=view_all'>View All Trip Schedules</a> | ");
            out.println("<a href='FrontController-URL?module=trip&action=view_by_route'>View Schedules by Route</a> | ");
            out.println("<a href='FrontController-URL?module=trip&action=view_unassigned'>View Unassigned Trips</a> | ");
            out.println("<a href='FrontController-URL?module=trip&action=assign_vehicle'>Assign Vehicle to Trip</a> | ");
            out.println("<a href='FrontController-URL?module=trip&action=unassign_vehicle'>Unassign Vehicle From Trip</a> | ");            
            out.println("<a href='FrontController-URL?module=trip&action=return_to_menu'>Return to Main Menu</a>");
            out.println("</div>");   
            
            // Display search form
            out.println("<form action=\"ViewTripSchedule-URL\" method=\"get\">");
            out.println("<label for=\"tripID\">Enter Trip ID:</label>");
            out.println("<input type=\"text\" name=\"tripID\" id=\"tripID\">");
            out.println("<input type=\"submit\" value=\"Search\">");
            out.println("</form>");
            
            // Display error message if any
            if (!errorMessage.isEmpty()) {
                out.println("<p style=\"color: red;\">" + errorMessage + "</p>");
            }
            
            // Display trip schedule details if found
            if (trip != null) {
                out.println("<h3>Trip Schedule Details:</h3>");
                out.println("<table border=\"1\">");
                out.println("<tr><th>Field</th><th>Value</th></tr>");
                out.println("<tr><td>Trip ID</td><td>" + trip.getTripScheduleId() + "</td></tr>");
                out.println("<tr><td>Start Time</td><td>" + trip.getStartTime() + "</td></tr>");
                out.println("<tr><td>Route ID</td><td>" + trip.getRouteId() + "</td></tr>");
                out.println("<tr><td>Route Name</td><td>" + trip.getRouteName() + "</td></tr>");
                out.println("<tr><td>Direction</td><td>" + trip.getDirection() + "</td></tr>");
                
                // Vehicle information section
                out.println("<tr><td>Vehicle Assigned</td><td>" + (trip.hasVehicleAssigned() ? "Yes" : "No") + "</td></tr>");
                
                if (trip.hasVehicleAssigned()) {
                    out.println("<tr><td>Vehicle ID</td><td>" + trip.getVehicle().getVehicleID() + "</td></tr>");
                    out.println("<tr><td>Vehicle Type</td><td>" + trip.getVehicle().getVehicleType().name() + "</td></tr>");
                    out.println("<tr><td>Vehicle Number</td><td>" + trip.getVehicle().getVIN() + "</td></tr>");
                    out.println("<tr><td>Maximum Passengers</td><td>" + trip.getVehicle().getMaxPassengers() + "</td></tr>");
                    out.println("<tr><td>Fuel Type</td><td>" + trip.getVehicle().getFuelType() + "</td></tr>");
                    out.println("<tr><td>Fuel Consumption Rate</td><td>" + String.format("%.2f", trip.getVehicle().getFuelRate()) + "</td></tr>");
                }
                
                out.println("</table>");
                
                // Additional action links
                out.println("<div style='margin-top:20px;'>");
                
                // Show assign vehicle link if no vehicle is assigned
                if (!trip.hasVehicleAssigned()) {
                    out.println("<a href=\"FrontController-URL?module=trip&action=assign_vehicle&tripID=" + trip.getTripScheduleId() + "\">Assign Vehicle to this Trip</a>");
                }
                
                out.println("</div>");
            }
            
            // Add links back to views
            out.println("<p><a href=\"FrontController-URL?module=trip&action=view_all\">Back to All Trip Schedules</a></p>");
            if (trip != null) {
                out.println("<p><a href=\"FrontController-URL?module=trip&action=view_by_route&routeName=" + trip.getRouteName() + "\">View All Trips for Route " + trip.getRouteName() + "</a></p>");
            }
            
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
        return "View Trip Schedule Details Servlet";
    }
}
