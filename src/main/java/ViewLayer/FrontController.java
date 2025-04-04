/* filename: FrontController.java
 * date: Apr. 3rd, 2025
 * authors: Stephanie Prystupa-Maule, John Tieu
 * course: CST8288 O.O.P. with Design Patterns - Lab Section 023 
 * professor: Samira Ouaaz
 * coursework: Final Project - Public Transit Management System
 */

package ViewLayer;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import BusinessLayer.*;
import TransferObjects.CredentialsDTO;

/**
 * Front Controller servlet for the Transit Management application.
 * Processes all incoming requests and delegates to appropriate handlers.
 *
 * @author Stephanie Prystupa-Maule
 */
public class FrontController extends HttpServlet {

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
        
        // Check for valid session and credentials
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("credentials") == null) {
            // If there's no valid session - redirect to login
            response.sendRedirect("index.html?error=timeout");
            return;
        }
        
        // Get credentials from session
        CredentialsDTO credentials = (CredentialsDTO) session.getAttribute("credentials");
        
        // Initialize the business logic with credentials from the session
        VehiclesBusinessLogic vehiclesLogic = new VehiclesBusinessLogic(credentials);
        
        // Get parameters from the request
        String action = request.getParameter("action");
        String vehicleID = request.getParameter("vehicleID");
        
        // Determine default the action to take
        if (action == null) {
            action = "view_all"; // Default action
        }
        
        // Set attributes for the dispatcher
        request.setAttribute("action", action);
        request.setAttribute("vehiclesLogic", vehiclesLogic);
        
        // Dispatch to appropriate handler based on action
        switch (action) {
            case "view":
                request.getRequestDispatcher("ViewVehicle-URL").forward(request, response);
                break;
            case "update":
                request.getRequestDispatcher("UpdateVehicle-URL").forward(request, response);
                break;
            case "register":
                request.getRequestDispatcher("RegisterVehicle-URL").forward(request, response);
                break;
            case "remove":
                request.getRequestDispatcher("RemoveVehicle-URL").forward(request, response);
                break;
            case "view_all":
                request.getRequestDispatcher("ViewVehicles-URL").forward(request, response);
                break;
            default:
                request.getRequestDispatcher("ViewVehicles-URL").forward(request, response);
                break;
        }
    }

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Fleet Management Front Controller";
    }
}