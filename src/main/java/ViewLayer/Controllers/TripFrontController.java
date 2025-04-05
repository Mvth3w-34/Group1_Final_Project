/* filename: TripScheduleFrontController.java
 * date: Apr. 5th, 2025
 * authors: Stephanie Prystupa-Maule
 * course: CST8288 O.O.P. with Design Patterns - Lab Section 023 
 * professor: Samira Ouaaz
 * coursework: Final Project - Public Transit Management System
 */

package ViewLayer.Controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import BusinessLayer.*;
import TransferObjects.CredentialsDTO;

/**
 * Module-specific Front Controller servlet for the Trip Schedule Management module.
 * Processes all incoming trip-related requests and delegates to appropriate handlers.
 *
 * @author Stephanie Prystupa-Maule
 * @version 1.0
 * @since 04/05/2025
 */
public class TripFrontController extends HttpServlet {

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
        TripScheduleBusinessLogic tripScheduleLogic = new TripScheduleBusinessLogic(credentials);
        
        // Get parameters from the request
        String action = request.getParameter("action");
        String tripID = request.getParameter("tripID");
        String routeID = request.getParameter("routeID");
        
        // Determine default the action to take
        if (action == null) {
            action = "view_all"; // Default action
        }
        
        // Set attributes for the dispatcher
        request.setAttribute("action", action);
        request.setAttribute("tripScheduleLogic", tripScheduleLogic);
        
        // Dispatch to appropriate handler based on action
        switch (action) {
            case "view_all":
                request.getRequestDispatcher("/ViewAllTripSchedules-URL").forward(request, response);
                break;
            case "view_by_route":
                request.getRequestDispatcher("/ViewTripSchedulesByRoute-URL").forward(request, response);
                break;
            case "view_unassigned":
                request.getRequestDispatcher("/ViewUnassignedTrips-URL").forward(request, response);
                break;
            case "assign_vehicle":
                request.getRequestDispatcher("/AssignVehicleToTrip-URL").forward(request, response);
                break;
            case "return_to_menu":
                response.sendRedirect("LandingServlet-URL");
                break;
            default:
                request.getRequestDispatcher("/ViewAllTripSchedules-URL").forward(request, response);
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
        return "Trips Module Front Controller";
    }
}
