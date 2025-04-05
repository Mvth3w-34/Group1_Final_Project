/* filename: UpdateVehicle.java
 * date: Apr. 4th, 2025
 * authors: Stephanie Prystupa-Maule
 * course: CST8288 O.O.P. with Design Patterns - Lab Section 023 
 * professor: Samira Ouaaz
 * coursework: Final Project - Public Transit Management System
 */
package ViewLayer;
        
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
 * Servlet for updating a vehicle using session-based authentication
 * 
 * @author Stephanie Prystupa-Maule
 */
public class UpdateVehicle extends HttpServlet {
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
        String successMessage = "";
        String errorMessage = "";
        
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
            
            // Process form submission for update
            if ("POST".equalsIgnoreCase(request.getMethod()) && vehicleID != null) {
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
                        float fuelRate = Float.parseFloat(fuelRateStr);
                        int maxPassengers = Integer.parseInt(maxPassengersStr);
                        Integer tripID = null;
                        
                        if (tripIDStr != null && !tripIDStr.trim().isEmpty()) {
                            tripID = Integer.parseInt(tripIDStr);
                        }
                        
                        // Parse vehicle type
                        VehicleDTO.VehicleType vehicleType = VehicleDTO.VehicleType.valueOf(vehicleTypeStr);
                        
                        // Create updated vehicle DTO
                        vehicle = VehicleDTO.setupVehicle()
                            .setID(vehicleID)
                            .setVehicleType(vehicleType)
                            .setVehicleNum(vehicleNumber)
                            .setFuelType(fuelType)
                            .setConsumptionRate(fuelRate)
                            .setMaxPassenger(maxPassengers)
                            .setTripID(tripID)
                            .buildVehicle();
                        
                        // Update the vehicle
                        vehiclesLogic.updateVehicle(vehicle);
                        successMessage = "Vehicle updated successfully.";
                    }
                } catch (NumberFormatException e) {
                    errorMessage = "Invalid number format: " + e.getMessage();
                } catch (IllegalArgumentException e) {
                    errorMessage = "Invalid vehicle type: " + e.getMessage();
                }
            } 
            
            // If this is a GET request or we need to display the form again
            // Get the vehicle data to pre-fill the form
            if (vehicleID != null && (errorMessage.isEmpty() || "GET".equalsIgnoreCase(request.getMethod()))) {
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
            out.println("<title>Update Vehicle</title>");
            out.println("</head>");
            out.println("<body>");
            
            out.println("<h1>Update Vehicle</h1>");
            
            // Navigation menu
            out.println("<div style='margin-bottom:20px;'>");
            out.println("<a href='FrontController-URL?action=view_all'>View All Vehicles</a> | ");
            out.println("<a href='FrontController-URL?action=view'>Search Vehicle</a> | ");
            out.println("<a href='FrontController-URL?action=update'>Update Vehicle</a> | ");
            out.println("<a href='FrontController-URL?action=register'>Register Vehicle</a> | ");
            out.println("<a href='FrontController-URL?action=remove'>Remove Vehicle</a>");
            out.println("</div>");   
            
            // Display success or error messages
            if (!successMessage.isEmpty()) {
                out.println("<p style='color: green;'>" + successMessage + "</p>");
            }
            if (!errorMessage.isEmpty()) {
                out.println("<p style='color: red;'>" + errorMessage + "</p>");
            }
            
            // If no vehicle is selected or not found, show search form
            if (vehicleID == null || vehicle == null) {
                out.println("<h3>Enter Vehicle ID to Update:</h3>");
                out.println("<form action='UpdateVehicle-URL' method='get'>");
                out.println("<label for='vehicleID'>Vehicle ID: </label>");
                out.println("<input type='text' name='vehicleID' id='vehicleID' required>");
                out.println("<input type='submit' value='Search'>");
                out.println("</form>");
            } 
            // If vehicle is found, display update form with pre-filled values
            else {
                out.println("<h3>Update Vehicle Details:</h3>");
                out.println("<form action='UpdateVehicle-URL' method='post'>");
                out.println("<input type='hidden' name='vehicleID' value='" + vehicle.getVehicleID() + "'>");
                
                out.println("<table>");
                
                // Vehicle Type (dropdown)
                out.println("<tr>");
                out.println("<td><label for='vehicleType'>Vehicle Type:</label></td>");
                out.println("<td><select name='vehicleType' id='vehicleType' required>");
                for (VehicleDTO.VehicleType type : VehicleDTO.VehicleType.values()) {
                    out.println("<option value='" + type.name() + "'" + 
                        (type == vehicle.getVehicleType() ? " selected" : "") + ">" + 
                        type.name() + "</option>");
                }
                out.println("</select></td>");
                out.println("</tr>");
                
                // Vehicle Number (VIN)
                out.println("<tr>");
                out.println("<td><label for='vehicleNumber'>Vehicle Number:</label></td>");
                out.println("<td><input type='text' name='vehicleNumber' id='vehicleNumber' value='" + 
                    vehicle.getVIN() + "' required></td>");
                out.println("</tr>");
                
                // Fuel Type
                out.println("<tr>");
                out.println("<td><label for='fuelType'>Fuel Type:</label></td>");
                out.println("<td><input type='text' name='fuelType' id='fuelType' value='" + 
                    vehicle.getFuelType() + "' required></td>");
                out.println("</tr>");
                
                // Fuel Consumption Rate
                out.println("<tr>");
                out.println("<td><label for='fuelRate'>Fuel Consumption Rate:</label></td>");
                out.println("<td><input type='number' name='fuelRate' id='fuelRate' step='0.01' min='0' value='" + 
                    String.format("%.2f", vehicle.getFuelRate()) + "' required></td>");
                out.println("</tr>");
                
                // Maximum Passengers
                out.println("<tr>");
                out.println("<td><label for='maxPassengers'>Maximum Passengers:</label></td>");
                out.println("<td><input type='number' name='maxPassengers' id='maxPassengers' min='1' value='" + 
                    vehicle.getMaxPassengers() + "' required></td>");
                out.println("</tr>");
                
                // Assigned Trip ID (optional)
                out.println("<tr>");
                out.println("<td><label for='tripID'>Assigned Trip ID (optional):</label></td>");
                out.println("<td><input type='number' name='tripID' id='tripID' value='" + 
                    (vehicle.getTripID() == null ? "" : vehicle.getTripID()) + "'></td>");
                out.println("</tr>");
                
                // Submit button
                out.println("<tr>");
                out.println("<td colspan='2'><input type='submit' value='Update Vehicle'></td>");
                out.println("</tr>");
                
                out.println("</table>");
                out.println("</form>");
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
        return "Update Vehicle Servlet";
    }
}
