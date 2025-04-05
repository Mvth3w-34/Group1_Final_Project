/* filename: ViewUnassignedTrips.java
 * date: Apr. 5th, 2025
 * authors: Stephanie Prystupa-Maule
 * course: CST8288 O.O.P. with Design Patterns - Lab Section 023 
 * professor: Samira Ouaaz
 * coursework: Final Project - Public Transit Management System
 */

package ViewLayer.Trips;
        
import BusinessLayer.TripScheduleBusinessLogic;
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


/**
 * Servlet for listing unassigned trip schedules using session-based authentication
 * 
 * @author Stephanie Prystupa-Maule
 * @version 1.0
 * @since 04/05/2025
 */
public class ViewUnassignedTrips extends HttpServlet {
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
        List<TripScheduleDTO> unassignedTrips = null;
        String errorMessage = "";
        
        try {
            // Initialize business logic with credentials from session
            TripScheduleBusinessLogic tripScheduleLogic = new TripScheduleBusinessLogic(credentials);
            
            // Get all unassigned trip schedules
            unassignedTrips = tripScheduleLogic.getUnassignedTrips();
        } catch (SQLException e) {
            errorMessage = "Database error: " + e.getMessage();
        } catch (Exception e) {
            errorMessage = "An error occurred while retrieving unassigned trip schedules: " + e.getMessage();
        }
        
        try {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>View Unassigned Trip Schedules</title>");
            out.println("</head>");
            out.println("<body>");
            
            out.println("<h1>Unassigned Trip Schedules</h1>");
            
            // Navigation menu
            out.println("<div>");
            out.println("<a href='FrontController-URL?module=trip&action=view_all'>View All Trip Schedules</a> | ");
            out.println("<a href='FrontController-URL?module=trip&action=view_by_route'>View Schedules by Route</a> | ");
            out.println("<a href='FrontController-URL?module=trip&action=view_unassigned'>View Unassigned Trips</a> | ");
            out.println("<a href='FrontController-URL?module=trip&action=assign_vehicle'>Assign Vehicle to Trip</a> | ");
            out.println("<a href='FrontController-URL?module=trip&action=unassign_vehicle'>Unassign Vehicle From Trip</a> | ");            
            out.println("<a href='FrontController-URL?module=trip&action=return_to_menu'>Return to Main Menu</a>");
            out.println("</div>");           
            
            // Display error message if any
            if (!errorMessage.isEmpty()) {
                out.println("<p style=\"color: red;\">Error: " + errorMessage + "</p>");
            }
            
            // Display all unassigned trip schedules if found
            if (unassignedTrips != null && !unassignedTrips.isEmpty()) {
                out.println("<h3>Trips Needing Vehicle Assignment:</h3>");
                out.println("<table border=\"1\">");
                out.println("<tr>");
                out.println("<th>Trip ID</th>");
                out.println("<th>Start Time</th>");
                out.println("<th>Route ID</th>");
                out.println("<th>Route Name</th>");
                out.println("<th>Direction</th>");
                // links to actions that can be performed on a record
                out.println("<th>Actions</th>");
                out.println("</tr>");
                
                for (TripScheduleDTO trip : unassignedTrips) {
                    // format output into table
                    out.println("<tr>");
                    out.println("<td>" + trip.getTripScheduleId() + "</td>");
                    out.println("<td>" + trip.getStartTime() + "</td>");
                    out.println("<td>" + trip.getRouteId() + "</td>");
                    out.println("<td>" + trip.getRouteName() + "</td>");
                    out.println("<td>" + trip.getDirection() + "</td>");
                    
                    // actions column
                    out.println("<td>");
                    out.println("<a href=\"ViewTripSchedule-URL?tripID=" + trip.getTripScheduleId() + "\">View</a> | ");
                    out.println("<a href=\"FrontController-URL?module=trip&action=assign_vehicle&tripID=" + trip.getTripScheduleId() + "\">Assign Vehicle</a>");
                    out.println("</td>"); // end actions column
                    out.println("</tr>");
                }
                out.println("</table>");
                
            } else if (unassignedTrips != null && unassignedTrips.isEmpty()) {
                out.println("<p>There are no unassigned trip schedules in the system.</p>");
                out.println("<p>All trips have vehicles assigned to them.</p>");
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
        return "View Unassigned Trip Schedules Servlet";
    }
}
