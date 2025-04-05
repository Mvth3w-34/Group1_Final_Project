/* filename: ViewVehicle.java
 * date: Apr. 4th, 2025
 * authors: Stephanie Prystupa-Maule
 * course: CST8288 O.O.P. with Design Patterns - Lab Section 023 
 * professor: Samira Ouaaz
 * coursework: Final Project - Public Transit Management System
 */
package ViewLayer.Vehicles;
        
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
import TransferObjects.VehicleDTO;


/**
 * Servlet for viewing a single vehicle by ID using session-based authentication
 * 
 * @author Stephanie Prystupa-Maule
 */
public class ViewVehicle extends HttpServlet {
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
        VehicleDTO vehicle = null;
        String errorMessage = "";
        
        // Get the vehicleID parameter (either from the request or from a form submission)
        String vehicleIDParam = request.getParameter("vehicleID");
        Integer vehicleID = null;
        
        // Check if vehicleID was provided
        if (vehicleIDParam != null && !vehicleIDParam.trim().isEmpty()) {
            try {
                vehicleID = Integer.parseInt(vehicleIDParam);
            } catch (NumberFormatException e) {
                errorMessage = "Invalid Vehicle ID format. Please enter a numeric value.";
            }
        }
        
        // Only query the database if we have a valid vehicleID
        if (vehicleID != null) {
            try {
                // Initialize business logic with credentials from session
                VehiclesBusinessLogic vehiclesLogic = new VehiclesBusinessLogic(credentials);
                
                // Get the specific vehicle
                vehicle = vehiclesLogic.getVehicle(vehicleID);
                
                if (vehicle == null) {
                    errorMessage = "No vehicle found with ID: " + vehicleID;
                }
            } catch (SQLException e) {
                errorMessage = "Database error: " + e.getMessage();
            } catch (Exception e) {
                errorMessage = "An error occurred while retrieving vehicle: " + e.getMessage();
            }
        }
        
        try {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>View Vehicle Details</title>");
            out.println("</head>");
            out.println("<body>");
            
            out.println("<h1>Vehicle Details</h1>");
            
            // Navigation menu
            out.println("<div style='margin-bottom:20px;'>");
            out.println("<a href='FrontController-URL?action=view_all'>View All Vehicles</a> | ");
            out.println("<a href='FrontController-URL?action=view'>Search Vehicle</a> | ");
            out.println("<a href='FrontController-URL?action=update'>Update Vehicle</a> | ");
            out.println("<a href='FrontController-URL?action=register'>Register Vehicle</a> | ");
            out.println("<a href='FrontController-URL?action=remove'>Remove Vehicle</a>");
            out.println("</div>");
            
            // Display search form
            out.println("<form action=\"ViewVehicle-URL\" method=\"get\">");
            out.println("<label for=\"vehicleID\">Enter Vehicle ID:</label>");
            out.println("<input type=\"text\" name=\"vehicleID\" id=\"vehicleID\">");
            out.println("<input type=\"submit\" value=\"Search\">");
            out.println("</form>");
            
            // Display error message if any
            if (!errorMessage.isEmpty()) {
                out.println("<p style=\"color: red;\">" + errorMessage + "</p>");
            }
            
            // Display vehicle details if found
            if (vehicle != null) {
                out.println("<h3>Vehicle Details:</h3>");
                out.println("<table border=\"1\">");
                out.println("<tr><th>Field</th><th>Value</th></tr>");
                out.println("<tr><td>Vehicle ID</td><td>" + vehicle.getVehicleID() + "</td></tr>");
                out.println("<tr><td>Vehicle Type</td><td>" + vehicle.getVehicleType().name() + "</td></tr>");
                out.println("<tr><td>Vehicle Number</td><td>" + vehicle.getVIN() + "</td></tr>");
                out.println("<tr><td>Fuel Type</td><td>" + vehicle.getFuelType() + "</td></tr>");
                out.println("<tr><td>Fuel Consumption Rate</td><td>" + String.format("%.2f", vehicle.getFuelRate()) + "</td></tr>");
                out.println("<tr><td>Maximum Passengers</td><td>" + vehicle.getMaxPassengers() + "</td></tr>");
                out.println("<tr><td>Current Assigned Trip</td><td>" + (vehicle.getTripID() == null ? "None" : vehicle.getTripID()) + "</td></tr>");
                out.println("</table>");
            }
            
            // Add link back to view all vehicles
            out.println("<p><a href=\"FrontController-URL?action=view_all\">Back to All Vehicles</a></p>");
            
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
        return "View Vehicle Details Servlet";
    }
}
