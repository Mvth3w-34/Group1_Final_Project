/* filename: TransitMenuView.java 
 * date: April 6th, 2025
 * authors: John Tieu
 * course: CST8288 O.O.P. with Design Patterns - Lab Section 023 
 * professor: Samira Ouaaz
 * coursework: Final Project - Public Transit Management System
 */
package ViewLayer;

import BusinessLayer.TransitBusinessLayer;
import TransferObjects.OperatorDTO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * The webpage for the main menu
 * @author John Tieu
 * @version 1.0
 * @since 21
 */ 
public class TransitMenuView extends HttpServlet {

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
        TransitBusinessLayer logicLayer;
        OperatorDTO op;
        // Retrieve entered login information and save as a session
        HttpSession session = request.getSession();
        if(session.getAttribute("user") == null && session.getAttribute("pass") == null) {
            session.setAttribute("user", request.getParameter("username"));
            session.setAttribute("pass", request.getParameter("password"));
        }
        // If account matches login credentials in DB, access main menu
        try (PrintWriter out = response.getWriter()) {
            // TODO: Have a user registration
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Transit Application</title>");
            out.append("<link rel='stylesheet' href='style.css'>");
            out.println("</head>");
            out.println("<body><center>");
            try {
                if(session.getAttribute("businessLayer") == null) {
                    logicLayer = new TransitBusinessLayer();
                    session.setAttribute("businessLayer", logicLayer);
                } else {
                    logicLayer = (TransitBusinessLayer) session.getAttribute("businessLayer");
                }
                op = logicLayer.validateCredentials((String) session.getAttribute("user"), (String) session.getAttribute("pass"));
                if (op != null) {
                    // Retrieve the operator account whose 
                    // credentials matches the entered login credentials
                    session.setAttribute("operator", op);
                    out.append( "<h1>Transit Application Menu</h1>"
                            +   "<p>Welcome " + op.getOperatorName()+ "</p>");
                    // Buttons that only managers can see
                    if (op.getUserType().name().equals(OperatorDTO.UserType.MANAGER.name())) {
                        out.append("<a href='/Group1_Final_Project_v1/MaintenanceRequestDashboard'><button>View Maintenance Request Dashboard</button></a><br>")
                            .append("<a href='/Group1_Final_Project_v1/MaintenanceRequest'><button>Perform Maintenance Request</button></a><br>")
                            .append("<a href='/Group1_Final_Project_v1/RegisterVehicle'><button>Register Vehicle</button></a><br>")
                            .append("<a href='/Group1_Final_Project_v1/AssignRoutes'><button>Assign Routes</button></a><br>")
                            .append("<a href='/Group1_Final_Project_v1/ViewVehicles'><button>View Fleet</button></a><br>");
                        // Links to register fuel/energy consumption and view alerts (Monitoring module by Mario)
                        out.append("<a href='energyFuelForm.html'><button>Log Energy/Fuel Consumption</button></a><br>")
                            .append("<a href='/Group1_Final_Project_v1/EnergyFuel'><button>View Fuel/Energy Alerts</button></a><br>");
                        
                        // Link to Steph's Operator Dashboard
                        out.append("<a href='/Group1_Final_Project_v1/OperatorPerformanceDashboard-URL'><button>Operator Performance Dashboard</button></a><br>");
                            ;
                    }
                    // All operators can see
                    out.append("<a href='/Group1_Final_Project_v1/LogTime'><button>Log Time</button></a><br>");
                    
                    
                    out.append("<a href='/Group1_Final_Project_v1/TransitFrontController'><button class='logout'>Logout</button></a>");
                   
                    
                } else {
                    out.append("   <h1>Transit Application Login Failed</h1>")
                       .append("   <p>Invalid credentials</p>")
                       .append("   <a href='/Group1_Final_Project_v1/TransitFrontController'><button>Return to Login</button></a>");
                }
            } catch (SQLException e) {
                out.append("<h1>Database Error</h1>")
                    .append("<p>Unable to connect to the database</p>")
                    .append("   <a href='/Group1_Final_Project_v1/TransitFrontController'><button>Return to Login</button></a>");
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
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
        return "Short description";
    }// </editor-fold>

}
