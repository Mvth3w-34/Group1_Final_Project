/* filename: RemoveVehicle.java
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
 * Servlet for removing a vehicle using session-based authentication
 * 
 * @author Stephanie Prystupa-Maule
 */
public class RemoveVehicle extends HttpServlet {
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
        String successMessage = "";
        String errorMessage = "";
        VehicleDTO vehicle = null;
        
        // Get the vehicleID parameter
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
        
        try {
            // Initialize business logic with credentials from session
            VehiclesBusinessLogic vehiclesLogic = new VehiclesBusinessLogic(credentials);
            
            // Process form submission for vehicle deletion
            if ("POST".equalsIgnoreCase(request.getMethod()) && vehicleID != null) {
                // Get the confirmation parameter
                String confirm = request.getParameter("confirm");
                
                if (confirm != null && confirm.equals("yes")) {
                    // First, get the vehicle to ensure it exists
                    vehicle = vehiclesLogic.getVehicle(vehicleID);
                    
                    if (vehicle != null) {
                        // Remove the vehicle
                        vehiclesLogic.removeVehicle(vehicle);
                        successMessage = "Vehicle with ID " + vehicleID + " has been successfully removed.";
                        vehicleID = null; // Reset vehicle ID to show the search form again
                    } else {
                        errorMessage = "No vehicle found with ID: " + vehicleID;
                    }
                } else {
                    // User didn't confirm, just fetch the vehicle again to display details
                    vehicle = vehiclesLogic.getVehicle(vehicleID);
                    if (vehicle == null) {
                        errorMessage = "No vehicle found with ID: " + vehicleID;
                    }
                }
            } 
            // Handle GET request to display vehicle details before confirmation
            else if (vehicleID != null) {
                vehicle = vehiclesLogic.getVehicle(vehicleID);
                if (vehicle == null) {
                    errorMessage = "No vehicle found with ID: " + vehicleID;
                }
            }
        } catch (SQLException e) {
            errorMessage = "Database error: " + e.getMessage();
        } catch (Exception e) {
            errorMessage = "An error occurred: " + e.getMessage();
        }
        
        try {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Remove Vehicle</title>");
            out.println("</head>");
            out.println("<body>");
            
            out.println("<h1>Remove Vehicle</h1>");
            
            // Navigation menu
            out.println("<div style='margin-bottom:20px;'>");
            out.println("<a href='FrontController-URL?action=view_all'>View All Vehicles</a> | ");
            out.println("<a href='FrontController-URL?action=view'>Search Vehicle</a> | ");
            out.println("<a href='FrontController-URL?action=update'>Update Vehicle</a> | ");
            out.println("<a href='FrontController-URL?action=register'>Register Vehicle</a> | ");
            out.println("<a href='FrontController-URL?action=remove'>Remove Vehicle</a>");
            out.println("</div>");
            
            // Display success or error message if applicable
            if (!successMessage.isEmpty()) {
                out.println("<p style='color: green;'>" + successMessage + "</p>");
            }
            if (!errorMessage.isEmpty()) {
                out.println("<p style='color: red;'>" + errorMessage + "</p>");
            }
            
            // If no vehicle is selected or after successful deletion, show search form
            if (vehicleID == null) {
                out.println("<h3>Enter Vehicle ID to Remove:</h3>");
                out.println("<form action='RemoveVehicle-URL' method='get'>");
                out.println("<label for='vehicleID'>Vehicle ID: </label>");
                out.println("<input type='text' name='vehicleID' id='vehicleID' required>");
                out.println("<input type='submit' value='Search'>");
                out.println("</form>");
            } 
            // If vehicle is found, display details and confirmation form
            else if (vehicle != null) {
                out.println("<h3>Vehicle Details:</h3>");
                out.println("<table border='1'>");
                out.println("<tr><td>Vehicle ID:</td><td>" + vehicle.getVehicleID() + "</td></tr>");
                out.println("<tr><td>Vehicle Type:</td><td>" + vehicle.getVehicleType().name() + "</td></tr>");
                out.println("<tr><td>Vehicle Number:</td><td>" + vehicle.getVIN() + "</td></tr>");
                out.println("<tr><td>Fuel Type:</td><td>" + vehicle.getFuelType() + "</td></tr>");
                out.println("<tr><td>Fuel Consumption Rate:</td><td>" + vehicle.getFuelRate() + "</td></tr>");
                out.println("<tr><td>Maximum Passengers:</td><td>" + vehicle.getMaxPassengers() + "</td></tr>");
                out.println("<tr><td>Current Assigned Trip:</td><td>" + (vehicle.getTripID() == null ? "None" : vehicle.getTripID()) + "</td></tr>");
                out.println("</table>");
                
                out.println("<h3>Confirm Deletion:</h3>");
                out.println("<p style='color: red;'>Are you sure you want to remove this vehicle? This action cannot be undone.</p>");
                
                out.println("<form action='RemoveVehicle-URL' method='post'>");
                out.println("<input type='hidden' name='vehicleID' value='" + vehicle.getVehicleID() + "'>");
                out.println("<input type='hidden' name='confirm' value='yes'>");
                out.println("<input type='submit' value='Yes, Remove Vehicle' style='background-color: #ff6666;'>");
                out.println("</form>");
                
                out.println("<a href='FrontController-URL?action=remove'>Cancel</a>");
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
        return "Remove Vehicle Servlet";
    }
}