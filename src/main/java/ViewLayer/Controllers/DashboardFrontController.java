/* filename: DashboardFrontController.java
 * date: Apr. 6th, 2025
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
//import TransferObjects.CredentialsDTO;
import TransferObjects.OperatorDTO;

/**
 * Module-specific Front Controller servlet for the Dashboard module.
 * Processes all incoming dashboard-related requests and delegates to appropriate handlers.
 *
 * @author Stephanie Prystupa-Maule
 * @version 1.0
 * @since 04/06/2025
 */
public class DashboardFrontController extends HttpServlet {

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
            
        
            // new session handling with raw credentials and authentication in session
            HttpSession session = request.getSession(false);
            if (session == null || session.getAttribute("operator") == null) {
                // If there's no valid session - redirect to login
                response.sendRedirect("/Group1_Final_Project_v1/TransitFrontController");
                return;
            }

            // Get operator from session
            OperatorDTO operator = (OperatorDTO) session.getAttribute("operator");
            TransitBusinessLayer logicLayer = (TransitBusinessLayer) session.getAttribute("businessLayer");

            // Initialize the business logic with the operator object
            OperatorPerformanceBusinessLogic operatorPerformanceLogic = new OperatorPerformanceBusinessLogic(operator, logicLayer);
        
          // old session handling, with encapsulation and resource management        
//        // Check for valid session and credentials
//        HttpSession session = request.getSession(false);
//        if (session == null || session.getAttribute("credentials") == null) {
//            // If there's no valid session - redirect to login
//            response.sendRedirect("index.html?error=timeout");
//            return;
//        }
//        
//        // Get credentials from session
//        CredentialsDTO credentials = (CredentialsDTO) session.getAttribute("credentials");
//        
//        // Initialize the business logic with credentials from the session
//        OperatorPerformanceBusinessLogic operatorPerformanceLogic = new OperatorPerformanceBusinessLogic(credentials);
        
        // Get parameters from the request
        String action = request.getParameter("action");
        String operatorId = request.getParameter("operatorId");
        String routeId = request.getParameter("routeId");
        
        // Determine default action to take
        if (action == null) {
            action = "operator_performance"; // Default action
        }
        
        // Set attributes for the dispatcher
        request.setAttribute("action", action);
        request.setAttribute("operatorPerformanceLogic", operatorPerformanceLogic);
        
        // Dispatch to appropriate handler based on action
        switch (action) {
            case "operator_performance":
                request.getRequestDispatcher("/OperatorPerformanceDashboard-URL").forward(request, response);
                break;
                
            case "operator_detail":
                if (operatorId == null || operatorId.isEmpty()) {
                    response.sendRedirect("DashboardFrontController-URL?action=operator_performance&error=missing_id");
                    return;
                }
                request.setAttribute("operatorId", operatorId);
                request.getRequestDispatcher("/ViewOperatorDetail-URL").forward(request, response);
                break;             

            case "return_to_menu":
                response.sendRedirect("/Group1_Final_Project_v1/TransitMenuView");
                break;
              
              // old version
//            case "return_to_menu":
//                response.sendRedirect("LandingServlet-URL");
//                break;
                
            default:
                request.getRequestDispatcher("/OperatorPerformanceDashboard-URL").forward(request, response);
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
        return "Dashboard Module Front Controller";
    }
}
