/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package ViewLayer;

import TransferObjects.*;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import BusinessLayer.*;
import java.sql.SQLException;

/**
 *
 * @author johnt
 */
public class TransitFrontController extends HttpServlet {

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
            // TODO: Have a user registration
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Transit Application</title>");
            out.println("</head>");
            out.println("<body><center>");
            out.println("<h1>Enter Login Credentials for Transit DB</h1>"
                    + "<form action='/CST8288-JavaProject/TransitFrontController' method='POST'>"
                    + "<label for='username'>Username</label>"
                    + "<input type='text' id='username' name='username'><br>"
                    + "<label for='password'>Password</label>"
                    + "<input type='text' id='password' name='password'><br>"
                    + "<input type='submit' value='Login'></form>");
            out.println("</center></body>");
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
        String user = request.getParameter("username");
        String pass = request.getParameter("password");
        TransitBusinessLayer logicLayer;
        // If account matches login credentials in DB, access main menu
        try (PrintWriter out = response.getWriter()) {
            // TODO: Have a user registration
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Transit Application</title>");
            out.println("</head>");
            out.println("<body><center>");
            
            try {
                logicLayer = new TransitBusinessLayer();
                if (logicLayer.validateCredentials(user, pass)) {
                    out.append( "<h1>Transit Application Menu</h1>"
                            +   "<p>Welcome " + user + "</p>"
                            +   "<form action='/CST8288-JavaProject/TransitFrontController'>"
                            +       "<input type='submit' value='Logout'>"
                    );
                } else {
                    out.append("   <h1>Transit Application Login Failed</h1>")
                       .append("   <p>Invalid credentials</p>")
                       .append("   <form action='/CST8288-JavaProject/TransitFrontController'>")
                       .append("        <input type='submit' value='Return to Login'></form>");
                }
            } catch (SQLException e) {
                out.println("<h1>Database Error</h1>"
                        +   "<p>Unable to connect to the database</p>");
            }
            
            out.println("</center></body>");
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
