/* filename: ViewTripSchedulesByRoute.java
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
 * Servlet for listing trip schedules filtered by route using session-based authentication
 * 
 * @author Stephanie Prystupa-Maule
 * @version 1.0
 * @since 04/05/2025
 */
public class ViewTripSchedulesByRoute extends HttpServlet {
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
        List<TripScheduleDTO> trips = null;
        String errorMessage = "";
        
        // Check for routeName in request parameters and attributes
        String routeName = request.getParameter("routeName");
        if (routeName == null || routeName.isEmpty()) {
            // If not in parameters, check attributes
            Object routeNameAttr = request.getAttribute("routeName");
            if (routeNameAttr != null) {
                routeName = routeNameAttr.toString();
            } else {
                routeName = "";
            }
        }        
        
        try {
            // Initialize business logic with credentials from session
            TripScheduleBusinessLogic tripScheduleLogic = new TripScheduleBusinessLogic(credentials);
            
            // If a route name is provided, get trips for that route
            if (routeName != null && !routeName.trim().isEmpty()) {
                trips = tripScheduleLogic.getTripSchedulesByRoute(routeName.trim());
            } else {
                // If no route specified, get all trips but set a flag for the UI
                trips = tripScheduleLogic.getAllTripSchedules();
                routeName = "";
            }
        } catch (SQLException e) {
            errorMessage = "Database error: " + e.getMessage();
        } catch (Exception e) {
            errorMessage = "An error occurred while retrieving trip schedules: " + e.getMessage();
        }
        
        try {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>View Trip Schedules By Route</title>");
            out.println("</head>");
            out.println("<body>");
            
            out.println("<h1>Trip Schedules By Route</h1>");
            
            // Navigation menu
            out.println("<div>");
            out.println("<a href='FrontController-URL?module=trip&action=view_all'>View All Trip Schedules</a> | ");
            out.println("<a href='FrontController-URL?module=trip&action=view_by_route'>View Schedules by Route</a> | ");
            out.println("<a href='FrontController-URL?module=trip&action=view_unassigned'>View Unassigned Trips</a> | ");
            out.println("<a href='FrontController-URL?module=trip&action=assign_vehicle'>Assign Vehicle to Trip</a> | ");
            out.println("<a href='FrontController-URL?module=trip&action=unassign_vehicle'>Unassign Vehicle From Trip</a> | ");            
            out.println("<a href='FrontController-URL?module=trip&action=return_to_menu'>Return to Main Menu</a>");
            out.println("</div>");   
            
            // Search form for filtering by route
            out.println("<div style='margin-top: 20px; margin-bottom: 20px;'>");
            out.println("<form action='FrontController-URL' method='get'>");
            out.println("<input type='hidden' name='module' value='trip'>");
            out.println("<input type='hidden' name='action' value='view_by_route'>");
            out.println("<label for='routeName'>Route Name:</label>");
            // Escape the routeName to prevent XSS vulnerabilities
            String escapedRouteName = routeName != null ? routeName.replace("\"", "&quot;") : "";
            out.println("<input type='text' id='routeName' name='routeName' value='" + escapedRouteName + "'>");
            out.println("<input type='submit' value='Filter'>");
            out.println("</form>");
            out.println("</div>");
            
            // Display route-specific heading if a route was specified
            if (routeName != null && !routeName.isEmpty()) {
                out.println("<h3>Trip Schedules for Route: " + routeName + "</h3>");
            } else {
                out.println("<h3>All Trip Schedules (Enter a route name to filter)</h3>");
            }
            
            // Display error message if any
            if (!errorMessage.isEmpty()) {
                out.println("<p style=\"color: red;\">Error: " + errorMessage + "</p>");
            }
            
            // Display trip schedules if found
            if (trips != null && !trips.isEmpty()) {
                out.println("<table border=\"1\">");
                out.println("<tr>");
                out.println("<th>Trip ID</th>");
                out.println("<th>Start Time</th>");
                out.println("<th>Route ID</th>");
                out.println("<th>Route Name</th>");
                out.println("<th>Direction</th>");
                out.println("<th>Vehicle Assigned</th>");
                out.println("<th>Vehicle Type</th>");
                out.println("<th>Vehicle Number</th>");
                out.println("<th>Actions</th>");
                out.println("</tr>");
                
                for (TripScheduleDTO trip : trips) {
                    // format output into table
                    out.println("<tr>");
                    out.println("<td>" + trip.getTripScheduleId() + "</td>");
                    out.println("<td>" + trip.getStartTime() + "</td>");
                    out.println("<td>" + trip.getRouteId() + "</td>");
                    out.println("<td>" + trip.getRouteName() + "</td>");
                    out.println("<td>" + trip.getDirection() + "</td>");
                    
                    // Vehicle information - check if vehicle is assigned
                    if (trip.hasVehicleAssigned()) {
                        out.println("<td>Yes</td>");
                        out.println("<td>" + trip.getVehicle().getVehicleType().name() + "</td>");
                        out.println("<td>" + trip.getVehicle().getVIN() + "</td>");
                    } else {
                        out.println("<td>No</td>");
                        out.println("<td>-</td>");
                        out.println("<td>-</td>");
                    }
                    
                    // actions column
                    out.println("<td>");
                    out.println("<a href=\"ViewTripSchedule-URL?tripID=" + trip.getTripScheduleId() + "\">View</a>");
                    
                    // If no vehicle assigned, show assign vehicle link
                    if (!trip.hasVehicleAssigned()) {
                        out.println(" | <a href=\"FrontController-URL?module=trip&action=assign_vehicle&tripID=" + trip.getTripScheduleId() + "\">Assign Vehicle</a>");
                    }
                    // If a vehicle is assigned, show unassign vehicle link
                    if (trip.hasVehicleAssigned()) {
                        out.println(" | <a href=\"FrontController-URL?module=trip&action=unassign_vehicle&tripID=" + trip.getTripScheduleId() + "\">Unassign Vehicle</a>");
                    }
                    
                    out.println("</td>"); // end actions column
                    out.println("</tr>");
                }
                out.println("</table>");
                
            } else if (trips != null && trips.isEmpty()) {
                // No trips found for the specified route
                if (routeName != null && !routeName.isEmpty()) {
                    out.println("<p>No trip schedules found for route: " + routeName + "</p>");
                } else {
                    out.println("<p>No trip schedules found in the database.</p>");
                }
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
        return "View Trip Schedules By Route Servlet";
    }
}
