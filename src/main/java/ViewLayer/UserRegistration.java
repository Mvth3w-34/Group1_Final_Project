/* filename: UserRegistration.java
 * date: April 6th, 2025
 * authors: John Tieu
 * course: CST8288 O.O.P. with Design Patterns - Lab Section 023 
 * professor: Samira Ouaaz
 * coursework: Final Project - Public Transit Management System
 */
package ViewLayer;

import BusinessLayer.TransitBusinessLayer;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The webpage for registering a new operator
 * @author John Tieu
 * @version 1.0
 * @since 21
 */
public class UserRegistration extends HttpServlet {

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
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet UserRegistration</title>");
            out.append("<link rel='stylesheet' href='style.css'>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UserRegistration at " + request.getContextPath() + "</h1>");
            out
                .append("<form method='POST'><label for='name'>Operator Name</label><br>")
                .append("<input type='text' id='name' name='name'><br>")
                .append("<label for='email'>Email</label><br>")
                .append("<input type='text' id='email' name='email'><br>")
                .append("<label for='username'>Username</label><br>")
                .append("<input type='text' id='username' name='username'><br>")
                .append("<label for='password'>Password</label><br>")
                .append("<input type='text' id='password' name='password'><br>")
                .append("<p>Select user type: </p>")
                .append("<input type='radio' value='OPERATOR' id='operator' name='usertype'><label class='punchlabelseperator' for='operator'>Operator</label>")
                .append("<input type='radio' value='MANAGER' id='manager' name='usertype'><label for='manager'>Manager</label><br>")
                .append("<input type='submit'></form>")
            ;
            out.println("</body>");
            out.println("</html>");
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
//        processRequest(request, response);
        response.setContentType("text/html;charset=UTF-8");
        TransitBusinessLayer logicLayer;
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet UserRegistration</title>");
            out.append("<link rel='stylesheet' href='style.css'>");
            out.println("</head>");
            out.println("<body>");
            try {
                if(request.getSession().getAttribute("businessLayer") == null) {
                    logicLayer = new TransitBusinessLayer();
                    request.getSession().setAttribute("businessLayer", logicLayer);
                } else {
                    logicLayer = (TransitBusinessLayer) request.getSession().getAttribute("businessLayer");
                }
                logicLayer.registerAccount(
                        request.getParameter("name"),
                        request.getParameter("email"), 
                        request.getParameter("username"), 
                        request.getParameter("password"), 
                        request.getParameter("usertype")
                );
                out
                    .append("<h1>User created</h1>")
                    .append("<p>User account has been created</p>");
            } catch (SQLException | IllegalArgumentException e) {
                out
                    .append("<h1>User Creation Failed</h1>")
                    .append("<p>Unable to create user account</p>");
            }
            out.append("<a href='/Group1_Final_Project_v1/TransitFrontController'><button>Return to Login</button></a>");
            out.println("</body>");
            out.println("</html>");
        }
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
