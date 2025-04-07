/* filename: LandingServlet.java
 * date: Apr. 6th, 2025
 * authors: Stephanie Prystupa-Maule
 * course: CST8288 O.O.P. with Design Patterns - Lab Section 023 
 * professor: Samira Ouaaz
 * coursework: Final Project - Public Transit Management System
 */
package ViewLayer.Common;

import BusinessLayer.TransitBusinessLayer;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
//import TransferObjects.CredentialsDTO;
import TransferObjects.OperatorDTO;

/**
 * Central dashboard servlet that serves as the landing page after login.
 * Provides navigation to different modules of the Transit Management System.
 * 
 * @author Stephanie Prystupa-Maule
 * @version 1.1
 * @since 04/06/2025
 */
public class LandingServlet extends HttpServlet {

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
          
        // new session handling with raw credentials and authentication in session
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("operator") == null) {
            // If there's no valid session - redirect to login
            response.sendRedirect("/Group1_Final_Project_v1/TransitFrontController");
            return;
        }
        
        TransitBusinessLayer logicLayer = (TransitBusinessLayer) session.getAttribute("businessLayer"); 

        // Get operator from session
        OperatorDTO operator = (OperatorDTO) session.getAttribute("operator");
        
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
        
        try (PrintWriter out = response.getWriter()) {
            
            // Output HTML for the dashboard
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Transit Management System - Dashboard</title>");
            out.println("<style>");
            out.println(".modules { margin-top: 20px; }");
            out.println(".module-card { border: 1px solid #ccc; padding: 10px; margin: 10px 0; }");
            out.println(".module-title { font-weight: bold; margin-bottom: 10px; }");
            out.println("</style>");
            out.println("</head>");
            out.println("<body>");
            // Header section with title and logout
            out.println("<h1>Public Transit Management System</h1>");
            out.println("<div><a href=\"LogoutServlet-URL\">Logout</a></div>");
            
            // Check for error messages
            if (request.getAttribute("errorMessage") != null) {
                out.println("<div style=\"color: red; margin: 10px 0;\">");
                out.println(request.getAttribute("errorMessage"));
                out.println("</div>");
            }
            
            // Modules section
            out.println("<div class=\"modules\">");
            
            // Vehicle Management Module
            out.println("<div class=\"module-card\">");
            out.println("<div class=\"module-title\">Vehicle Management</div>");
//            out.println("<p>Total Vehicles: " + vehicleCount + "</p>");
            out.println("<ul>");
            out.println("<li><a href=\"FrontController-URL?module=vehicle&action=view_all\">View All Vehicles</a></li>");
            out.println("<li><a href=\"FrontController-URL?module=vehicle&action=view\">Search Vehicle</a></li>");
            out.println("<li><a href=\"FrontController-URL?module=vehicle&action=register\">Register New Vehicle</a></li>");
            out.println("<li><a href=\"FrontController-URL?module=vehicle&action=update\">Update Vehicle</a></li>");
            out.println("<li><a href=\"FrontController-URL?module=vehicle&action=remove\">Remove Vehicle</a></li>");
            out.println("</ul>");
            out.println("</div>");
            
            // Trip Schedule Management Module
            out.println("<div class=\"module-card\">");
            out.println("<div class=\"module-title\">Trip Schedule Management</div>");
  //          out.println("<p>Total Trip Schedules: " + scheduleCount + "</p>");
  //          out.println("<p>Unassigned Trips: " + unassignedTripsCount + "</p>");
            out.println("<ul>");
            out.println("<li><a href=\"FrontController-URL?module=trip&action=view_all\">View All Trip Schedules</a></li>");
            out.println("<li><a href=\"FrontController-URL?module=trip&action=view_by_route\">View Schedules by Route</a></li>");
            out.println("<li><a href=\"FrontController-URL?module=trip&action=view_unassigned\">View Unassigned Trips</a></li>");
            out.println("<li><a href=\"FrontController-URL?module=trip&action=assign_vehicle\">Assign Vehicle to Trip</a></li>");
            out.println("<li><a href=\"FrontController-URL?module=trip&action=simulate_trip\">Simulate Trip</a></li>");
            out.println("</ul>");
            out.println("</div>");
            
            // Performance Dashboard Module
            out.println("<div class=\"module-card\">");
            out.println("<div class=\"module-title\">Performance Dashboards</div>");
            out.println("<ul>");
            out.println("<li><a href=\"/Group1_Final_Project_v1/FrontController-URL?module=dashboard&action=operator_performance\">Operator Performance Dashboard</a></li>");
            out.println("</ul>");
            out.println("</div>");
            
            out.println("</div>"); // End modules section
            out.println("</body>");
            out.println("</html>");
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
        return "Transit Management System Dashboard";
    }
}
