/* filename: FrontController.java
 * date: Apr. 5th, 2025
 * authors: Stephanie Prystupa-Maule, John Tieu
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
import TransferObjects.CredentialsDTO;

/**
 * Main Front Controller servlet for the Transit Management application.
 * Routes incoming requests to the appropriate module controller.
 *
 * @author Stephanie Prystupa-Maule
 * @version 2.0
 * @since 04/05/2025
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
        
        // Get module and action parameters
        String module = request.getParameter("module");
        String action = request.getParameter("action");
        
        // If module is not specified, default to the landing page
        if (module == null || module.isEmpty()) {
            response.sendRedirect("LandingServlet");
            return;
        }
        
        // Route to the appropriate module controller based on the module parameter
        switch (module.toLowerCase()) {
            case "vehicle":
                // Forward to VehicleFrontController with the original action
                if (action != null && !action.isEmpty()) {
                    response.sendRedirect("VehicleFrontController-URL?action=" + action);
                } else {
                    response.sendRedirect("VehicleFrontController-URL");
                }
                break;
                
            case "trip":
                // Forward to TripScheduleFrontController with the original action
                if (action != null && !action.isEmpty()) {
                    response.sendRedirect("TripFrontController-URL?action=" + action);
                } else {
                    response.sendRedirect("TripFrontController-URL");
                }
                break;
                
            default:
                // If an unknown module is requested, redirect to the landing page
                response.sendRedirect("LandingServlet");
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
        return "Main Front Controller - Routes to Module Controllers";
    }
}