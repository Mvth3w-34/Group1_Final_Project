/* filename: UpdateMaintenanceRequest.java 
 * date: April 6th, 2025
 * authors: Mathew Chebet
 * course: CST8288 O.O.P. with Design Patterns - Lab Section 023 
 * professor: Samira Ouaaz
 * coursework: Final Project - Public Transit Management System
 */
package ViewLayer;

import BusinessLayer.TransitBusinessLayer;
import TransferObjects.MaintenanceRequestTicketDTO;
import TransferObjects.OperatorDTO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Random;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This class models a servlet to update maintenance requests.
 * 
 * @author Mathew Chebet
 * @version 1.0
 * @since 21
 */
@WebServlet(name = "UpdateMaintenanceRequest", urlPatterns =
{
    "/UpdateMaintenanceRequest"
})
public class UpdateMaintenanceRequest extends HttpServlet
{

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
            throws ServletException, IOException
    {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter())
        {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet UpdateMaintenanceRequest</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UpdateMaintenanceRequest at " + request.getContextPath() + "</h1>");
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
            throws ServletException, IOException
    {
         response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter())
        {   
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<link rel='stylesheet' href='style.css'>");
            out.println("<title>Servlet MaintenanceRequest</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<center>");
            out.println("<form action=\"/Group1_Final_Project_v1/UpdateMaintenanceRequest\" method=\"post\">");
            out.println("<h1>Please fill out the form to update a Maintenance Request and mark it as complete.</h1>");
            out.println("<br>");
            out.println("<br>");
            
            out.println("<label for=\"vTypes\">Request ID</label>");
            out.println("<input type=\"text\" name=\"rID\"/>");
            
            out.println("<br>");
            
            out.println("<button type=\"submit\" name=\"rType\" value=\"new\">Mark request as complete</button>");
            out.println("</form>");
            out.println("<br>");
            out.println("<a href=\"/Group1_Final_Project_v1/TransitMenuView\"><button>Return to Main Menu</button></a>");
            if (request.getParameter("valid").equals("false") && request.getParameter("valid") != null){
              out.println("<h2>No request with that ID exists please pick a valid request ID.</h2>");  
            }
            
            out.println("</center>");
            out.println("</body>");
            out.println("</html>");
            
            
            
        }
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
            throws ServletException, IOException
    {
        try{
            int requestID = Integer.parseInt(request.getParameter("rID"));
            TransitBusinessLayer logic = new TransitBusinessLayer();
            MaintenanceRequestTicketDTO ticket = logic.getMaintenanceRequestById(requestID);
            
            if(ticket != null){
                logic.updateMaintenanceRequest(ticket);
                request.getRequestDispatcher("succesfulAction.html").forward(request, response);
            }          
            else {
                response.sendRedirect("UpdateMaintenanceRequest?valid=false");
            }
        } catch (NullPointerException | NumberFormatException | SQLException e) {
            response.sendRedirect("UpdateMaintenanceRequest?valid=false");
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo()
    {
        return "Short description";
    }// </editor-fold>

}
