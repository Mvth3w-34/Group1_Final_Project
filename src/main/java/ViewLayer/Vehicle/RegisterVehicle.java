/* filename: RegisterVehicle.java
 * date: Apr. 5th, 2025
 * authors: Stephanie Prystupa-Maule
 * course: CST8288 O.O.P. with Design Patterns - Lab Section 023 
 * professor: Samira Ouaaz
 * coursework: Final Project - Public Transit Management System
 */
package ViewLayer.Vehicle;
        
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
 * Servlet for registering a new vehicle using session-based authentication
 * 
 * @author Stephanie Prystupa-Maule
 * @version 2.0
 * @since 04/05/2025
 */
public class RegisterVehicle extends HttpServlet {
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
        
        // Process form submission
        if ("POST".equalsIgnoreCase(request.getMethod())) {
            try {
                // Get form parameters
                String vehicleTypeStr = request.getParameter("vehicleType");
                String vehicleNumber = request.getParameter("vehicleNumber");
                String fuelType = request.getParameter("fuelType");
                String fuelRateStr = request.getParameter("fuelRate");
                String maxPassengersStr = request.getParameter("maxPassengers");
                String tripIDStr = request.getParameter("tripID");
                
                // Validate inputs
                if (vehicleTypeStr == null || vehicleTypeStr.trim().isEmpty() ||
                    vehicleNumber == null || vehicleNumber.trim().isEmpty() ||
                    fuelType == null || fuelType.trim().isEmpty() ||
                    fuelRateStr == null || fuelRateStr.trim().isEmpty() ||
                    maxPassengersStr == null || maxPassengersStr.trim().isEmpty()) {
                    
                    errorMessage = "All fields except Trip ID are required.";
                } else {
                    // Convert and validate numeric fields
                    try {
                        float fuelRate = Float.parseFloat(fuelRateStr);
                        int maxPassengers = Integer.parseInt(maxPassengersStr);
                        Integer tripID = null;
                        
                        if (tripIDStr != null && !tripIDStr.trim().isEmpty()) {
                            tripID = Integer.parseInt(tripIDStr);
                        }
                        
                        // Parse vehicle type
                        VehicleDTO.VehicleType vehicleType = VehicleDTO.VehicleType.valueOf(vehicleTypeStr);
                        
                        // Create vehicle DTO
                        VehicleDTO vehicle = VehicleDTO.setupVehicle()
                            .setVehicleType(vehicleType)
                            .setVehicleNum(vehicleNumber)
                            .setFuelType(fuelType)
                            .setConsumptionRate(fuelRate)
                            .setMaxPassenger(maxPassengers)
                            .setTripID(tripID)
                            .buildVehicle();
                        
                        // Check for business logic from request attribute first (passed from VehicleFrontController)
                        VehiclesBusinessLogic vehiclesLogic = (VehiclesBusinessLogic) request.getAttribute("vehiclesLogic");
                        
                        // If not available, instantiate a new one
                        if (vehiclesLogic == null) {
                            vehiclesLogic = new VehiclesBusinessLogic(credentials);
                        }
                        
                        // Register the vehicle
                        vehiclesLogic.registerVehicle(vehicle);
                        successMessage = "Vehicle registered successfully.";
                    } catch (NumberFormatException e) {
                        errorMessage = "Invalid number format: " + e.getMessage();
                    } catch (IllegalArgumentException e) {
                        errorMessage = "Invalid vehicle type: " + e.getMessage();
                    }
                }
            } catch (SQLException e) {
                errorMessage = "Database error: " + e.getMessage();
            } catch (Exception e) {
                errorMessage = "An error occurred while registering vehicle: " + e.getMessage();
            }
        }
        
        try {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Register New Vehicle</title>");
            out.println("</head>");
            out.println("<body>");
            
            out.println("<h1>Register New Vehicle</h1>");
            
            // Navigation menu - updated with module parameter
            out.println("<div style='margin-bottom:20px;'>");
            out.println("<a href='FrontController-URL?module=vehicle&action=view_all'>View All Vehicles</a> | ");
            out.println("<a href='FrontController-URL?module=vehicle&action=view'>Search Vehicle</a> | ");
            out.println("<a href='FrontController-URL?module=vehicle&action=update'>Update Vehicle</a> | ");
            out.println("<a href='FrontController-URL?module=vehicle&action=register'>Register Vehicle</a> | ");
            out.println("<a href='FrontController-URL?module=vehicle&action=remove'>Remove Vehicle</a> | ");
            out.println("<a href='FrontController-URL?module=vehicle&action=return_to_menu'>Return to Main Menu</a>");
            out.println("</div>");
            
            // Display success or error message if applicable
            if (!successMessage.isEmpty()) {
                out.println("<p style='color: green;'>" + successMessage + "</p>");
            }
            if (!errorMessage.isEmpty()) {
                out.println("<p style='color: red;'>" + errorMessage + "</p>");
            }
            
            // Display registration form
            out.println("<form action='RegisterVehicle-URL' method='post'>");
            out.println("<table>");
            
            out.println("<tr>");
            out.println("<td><label for='vehicleType'>Vehicle Type:</label></td>");
            out.println("<td><select name='vehicleType' id='vehicleType' required>");
            out.println("<option value='BUS'>Bus</option>");
            out.println("<option value='ELECTRIC_TRAIN'>Electric Train</option>");
            out.println("<option value='DIESEL_TRAIN'>Diesel Train</option>");
            out.println("</select></td>");
            out.println("</tr>");
            
            out.println("<tr>");
            out.println("<td><label for='vehicleNumber'>Vehicle Number:</label></td>");
            out.println("<td><input type='text' name='vehicleNumber' id='vehicleNumber' required></td>");
            out.println("</tr>");
            
            out.println("<tr>");
            out.println("<td><label for='fuelType'>Fuel Type:</label></td>");
            out.println("<td><input type='text' name='fuelType' id='fuelType' required></td>");
            out.println("</tr>");
            
            out.println("<tr>");
            out.println("<td><label for='fuelRate'>Fuel Consumption Rate:</label></td>");
            out.println("<td><input type='number' name='fuelRate' id='fuelRate' step='0.01' min='0' required></td>");
            out.println("</tr>");
            
            out.println("<tr>");
            out.println("<td><label for='maxPassengers'>Maximum Passengers:</label></td>");
            out.println("<td><input type='number' name='maxPassengers' id='maxPassengers' min='1' required></td>");
            out.println("</tr>");
            
            out.println("<tr>");
            out.println("<td><label for='tripID'>Assigned Trip ID (optional):</label></td>");
            out.println("<td><input type='number' name='tripID' id='tripID'></td>");
            out.println("</tr>");
            
            out.println("<tr>");
            out.println("<td colspan='2'><input type='submit' value='Register Vehicle'></td>");
            out.println("</tr>");
            
            out.println("</table>");
            out.println("</form>");
            
            // Add link back to view all vehicles
            out.println("<p><a href=\"FrontController-URL?module=vehicle&action=view_all\">Back to All Vehicles</a></p>");
            
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
        return "Register New Vehicle Servlet";
    }
}