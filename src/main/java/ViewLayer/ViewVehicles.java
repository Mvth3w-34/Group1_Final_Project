package ViewLayer;
        
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
import TransferObjects.VehicleDTO;


/**
 * Servlet for listing all vehicles using session-based authentication
 * 
 * @author johnt
 * @author Stephanie Prystupa-Maule
 */
public class ViewVehicles extends HttpServlet {
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
        List<VehicleDTO> vehicles = null;
        String errorMessage = "";
        
        try {
            // Initialize business logic with credentials from session
            VehiclesBusinessLogic vehiclesLogic = new VehiclesBusinessLogic(credentials);
            
            // Get all vehicles
            vehicles = vehiclesLogic.getAllVehicles();
        } catch (SQLException e) {
            errorMessage = "Database error: " + e.getMessage();
        } catch (Exception e) {
            errorMessage = "An error occurred while retrieving vehicles: " + e.getMessage();
        }
        
            try {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>View All Vehicles</title>");
            out.println("</head>");
            out.println("<body>");
            
            out.println("<h1>Vehicle List</h1>");
            
            // Display all vehicles if found
            if (vehicles!= null && !vehicles.isEmpty()) {
                out.println("<h3>All Vehicles:</h3>");
                out.println("<table border=\"1\">");
                out.println("<tr>");
                out.println("<th>Vehicles ID</th>");
                out.println("<th>Vehicle Type</th>");
                out.println("<th>Vehicle Number</th>");
                out.println("<th>Fuel Type</th>");
                out.println("<th>Fuel Consumption Rate</th>");
                out.println("<th>Maximum Passengers</th>"); 
                out.println("<th>Current Assigned Trip</th>");                
                out.println("</tr>");
                
                for (VehicleDTO vehicle : vehicles) {
                    // format output into table
                    out.printf("<tr>"
                            + "<td>%d</td>" // vehicle ID (int)
                            + "<td>%s</td>" // vehicle type (as a String)
                            + "<td>%s</td>" // vehicle number (String)
                            + "<td>%s</td>" // fuel type (String)
                            + "<td>%.2f</td>" // fuel rate (2 decimal point float)
                            + "<td>%d</td>" // max passengers (int)
                            + "<td>%d</td>" // trip ID (int)
                            + "</tr>",
                        vehicle.getVehicleID(), vehicle.getVehicleType().name(),
                        vehicle.getVIN(), vehicle.getFuelType(), vehicle.getFuelRate(),
                        vehicle.getMaxPassengers(), vehicle.getTripID());
                }
                out.println("</table>");
                
            } else if (vehicles != null && vehicles.isEmpty()) {
                out.println("<p>No vehicles found in the database.</p>");
            } else if (!errorMessage.isEmpty()) {
                out.println("<p style=\"color: red;\">Error: " + errorMessage + "</p>");
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
        return "View All Vehicles Servlet";
    }
}
