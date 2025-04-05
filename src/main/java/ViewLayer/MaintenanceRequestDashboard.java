/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package ViewLayer;

import BusinessLayer.TransitBusinessLayer;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author kipro
 */
@WebServlet(name = "MaintenanceRequestDashboard", urlPatterns =
{
    "/MaintenanceRequestDashboard"
})
public class MaintenanceRequestDashboard extends HttpServlet
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
            try{
                TransitBusinessLayer logic = new  TransitBusinessLayer();
                List<Object[]> resultSet = logic.getAllMaintenanceRequests();
                
                
                if(resultSet != null){ 
                    /* TODO output your page here. You may use following sample code. */
                    out.println("<!DOCTYPE html>");
                    out.println("<html>");
                    out.println("<head>");
                    out.println("<link rel='stylesheet' href='style.css'>");
                    out.println("<title>Servlet MaintenanceRequestReports</title>");
                    out.println("</head>");
                    out.println("<body>");
                    out.println("<center>");
                    out.println("<h1>Please choose one of the options to view a specific report.</h1>");
                    out.println("<a href=\"/Group1_Final_Project_v1/MaintenanceRequestCostReport\"><button>View By Cost</button></a>");
                    out.println("<a href=\"/Group1_Final_Project_v1/MaintenanceRequestCompletionReport\"><button>View By Completion</button></a>");
                    out.println("<a href=\"/Group1_Final_Project_v1/TransitMenuView\"><button>Return to Main Menu</button></a>");
                    out.println("</body>");
                    out.println("</center>");
                    out.println("</html>");
                }
            } catch (SQLException | NullPointerException e){
                e.printStackTrace();
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
            throws ServletException, IOException
    {
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
            throws ServletException, IOException
    {
        processRequest(request, response);
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
