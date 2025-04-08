/* filename: FrontController.java
 * date: Apr. 6th, 2025
 * authors: Stephanie Prystupa-Maule
 * course: CST8288 O.O.P. with Design Patterns - Lab Section 023 
 * professor: Samira Ouaaz
 * coursework: Final Project - Public Transit Management System
 */

package ViewLayer.Controllers;

import BusinessLayer.OperatorPerformanceBusinessLogic;
import BusinessLayer.TransitBusinessLayer;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import TransferObjects.CredentialsDTO;
import TransferObjects.OperatorDTO;

/**
 * Main Front Controller servlet for the Transit Management application.
 * Routes incoming requests to the appropriate module controller.
 *
 * @author Stephanie Prystupa-Maule
 * @version 2.1
 * @since 04/06/2025
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
        
//        // Check for valid session and credentials
//        HttpSession session = request.getSession(false);
//        if (session == null || session.getAttribute("credentials") == null) {
//            // If there's no valid session - redirect to login
//            response.sendRedirect("index.html?error=timeout");
//            return;
//        }
        
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
                if (action != null && !action.isEmpty()) {
                    // Base URL
                    StringBuilder redirectURL = new StringBuilder("VehicleFrontController-URL?");

                    // Get all parameter names from the request
                    java.util.Enumeration<String> paramNames = request.getParameterNames();
                    boolean firstParam = true;

                    // Add each parameter to the redirect URL
                    while (paramNames.hasMoreElements()) {
                        String paramName = paramNames.nextElement();
                        String paramValue = request.getParameter(paramName);

                        if (paramValue != null && !paramValue.isEmpty()) {
                            if (!firstParam) {
                                redirectURL.append("&");
                            }
                            redirectURL.append(paramName).append("=")
                                      .append(java.net.URLEncoder.encode(paramValue, "UTF-8"));
                            firstParam = false;
                        }
                    }

                    response.sendRedirect(redirectURL.toString());
                } else {
                    response.sendRedirect("VehicleFrontController-URL");
                }
                break;

            case "trip":
                if (action != null && !action.isEmpty()) {
                    // Base URL
                    StringBuilder redirectURL = new StringBuilder("TripFrontController-URL?");

                    // Get all parameter names from the request
                    java.util.Enumeration<String> paramNames = request.getParameterNames();
                    boolean firstParam = true;

                    // Add each parameter to the redirect URL
                    while (paramNames.hasMoreElements()) {
                        String paramName = paramNames.nextElement();
                        String paramValue = request.getParameter(paramName);

                        if (paramValue != null && !paramValue.isEmpty()) {
                            if (!firstParam) {
                                redirectURL.append("&");
                            }
                            redirectURL.append(paramName).append("=")
                                      .append(java.net.URLEncoder.encode(paramValue, "UTF-8"));
                            firstParam = false;
                        }
                    }

                    response.sendRedirect(redirectURL.toString());
                } else {
                    response.sendRedirect("TripFrontController-URL");
                }
                break;
                
            case "dashboard":
                if (action != null && !action.isEmpty()) {
                    // Base URL
                    StringBuilder redirectURL = new StringBuilder("DashboardFrontController-URL?");

                    // Get all parameter names from the request
                    java.util.Enumeration<String> paramNames = request.getParameterNames();
                    boolean firstParam = true;

                    // Add each parameter to the redirect URL
                    while (paramNames.hasMoreElements()) {
                        String paramName = paramNames.nextElement();
                        String paramValue = request.getParameter(paramName);

                        if (paramValue != null && !paramValue.isEmpty()) {
                            if (!firstParam) {
                                redirectURL.append("&");
                            }
                            redirectURL.append(paramName).append("=")
                                      .append(java.net.URLEncoder.encode(paramValue, "UTF-8"));
                            firstParam = false;
                        }
                    }

                    response.sendRedirect(redirectURL.toString());
                } else {
                    response.sendRedirect("DashboardFrontController-URL");
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