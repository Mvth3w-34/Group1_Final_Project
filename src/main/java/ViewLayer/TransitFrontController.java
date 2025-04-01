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
import javax.servlet.http.HttpSession;

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
        request.getSession().invalidate(); // Clears out any existing sessions prior to login by default
        try (PrintWriter out = response.getWriter()) {
            // TODO: Have a user registration
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Transit Application</title>");
            out.append("<link rel='stylesheet' href='style.css'>");
            out.println("</head>");
            out.println("<body><center>");
            out.println("<h1>Enter Login Credentials for Transit DB</h1>"
                    + "<div class='form-container'><form method='POST'>"
                    + "<label for='username'>Username</label><br>"
                    + "<input type='text' id='username' name='username'><br>"
                    + "<label for='password'>Password</label><br>"
                    + "<input type='text' id='password' name='password'><br>"
                    + "<input type='submit' value='Login'></form></div>");
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
        // Clears out session prior to logging in
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
        if(request.getSession().getAttribute("user") == null && request.getSession().getAttribute("pass") == null) {
            request.getSession().setAttribute("user", request.getParameter("username"));
            request.getSession().setAttribute("pass", request.getParameter("password"));
        }
        request.getRequestDispatcher("/TransitMenuView").forward(request, response);
//        processRequest(request, response);
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
