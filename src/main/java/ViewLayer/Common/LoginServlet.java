/* filename: LoginServlet.java
 * date: Apr. 5th, 2025
 * authors: Stephanie Prystupa-Maule, John Tieu
 * course: CST8288 O.O.P. with Design Patterns - Lab Section 023 
 * professor: Samira Ouaaz
 * coursework: Final Project - Public Transit Management System
 */

package ViewLayer.Common;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import BusinessLayer.*;
import TransferObjects.CredentialsDTO;

/**
 * LoginServlet servlet that both displays the login form and validates credentials
 * 
 * @author Stephanie Prystupa-Maule
 */
public class LoginServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        // Check if this is a form submission or just a request to display the login form
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        // If no username/password provided, display the login form
        if (username == null || password == null) {
            displayLoginForm(request, response);
            return;
        }
        
        // Validate credentials
        if (username.trim().isEmpty() || password.trim().isEmpty()) {
            // Display login form again with error
            displayLoginForm(request, response, "missing");
            return;
        }
        
        // Create CredentialsDTO
        CredentialsDTO credentials = new CredentialsDTO();
        credentials.setUsername(username);
        credentials.setPassword(password);
        
        // Try to initialize business logic with credentials
        try {
            VehiclesBusinessLogic vehiclesLogic = new VehiclesBusinessLogic(credentials);
            
            // If we get here, credentials worked - create session
            HttpSession session = request.getSession(true);
            session.setAttribute("credentials", credentials);
            
            // Always redirect to LandingServlet after successful login
            response.sendRedirect("LandingServlet-URL");
            
        } catch (Exception e) {
            // Failed to connect to database or other error
            displayLoginForm(request, response, "invalid");
        }
    }
    
    /**
     * Displays the login form
     * @param request The HttpServletRequest
     * @param response The HttpServletResponse
     * @param errorType The type of error to display (optional)
     * @throws IOException If an I/O error occurs
     */
    private void displayLoginForm(HttpServletRequest request, HttpServletResponse response, String errorType) 
            throws IOException {
        PrintWriter out = response.getWriter();
        
        out.println("<!DOCTYPE html>");
        out.println("<HTML>");
        out.println("<HEAD>");
        out.println("  <TITLE>Public Transit Fleet Management System Login</TITLE>");
        out.println("</HEAD>");
        out.println("<BODY BGCOLOR=\"#FDF5E6\">");
        out.println("<CENTER>");
        out.println("<H2>Enter DBMS Credentials</H2>");
        
        // Display error message if applicable
        if (errorType != null) {
            out.println("<div id=\"error-message\" class=\"error\">");
            if (errorType.equals("missing")) {
                out.println("Username and password are required.");
            } else if (errorType.equals("invalid")) {
                out.println("Invalid credentials. Please try again.");
            }
            out.println("</div>");
        } else {
            out.println("<div id=\"error-message\" class=\"error\"></div>");
        }
        
        // Login Form
        out.println("<FORM ACTION=\"LoginServlet-URL\" METHOD=\"POST\">");
        out.println("  username:");
        out.println("  <INPUT TYPE=\"TEXT\" NAME=\"username\"><BR>");
        out.println("  password:");
        out.println("  <INPUT TYPE=\"PASSWORD\" NAME=\"password\"><P>");
        out.println("  <INPUT TYPE=\"SUBMIT\" VALUE=\"Login\">");
        out.println("</FORM>");
        out.println("</CENTER>");
        out.println("</BODY>");
        out.println("</HTML>");
    }
    
    /**
     * Overloaded displayLoginForm method without error message
     */
    private void displayLoginForm(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        displayLoginForm(request, response, null);
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
        return "Login Servlet with embedded login form";
    }
}