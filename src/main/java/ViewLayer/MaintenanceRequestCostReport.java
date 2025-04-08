/* filename: MaintenanceRequestCostReport.java 
 * date: April 6th, 2025
 * authors: Mathew Chebet
 * course: CST8288 O.O.P. with Design Patterns - Lab Section 023 
 * professor: Samira Ouaaz
 * coursework: Final Project - Public Transit Management System
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
 * This class models a servlet to display maintenance requests by their cost from low to high.
 * 
 * @author Mathew Chebet
 * @version 1.0
 * @since 21
 */
@WebServlet(name = "MaintenanceRequestCostReport", urlPatterns =
{
    "/MaintenanceRequestCostReport"
})
public class MaintenanceRequestCostReport extends HttpServlet
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
            out.println("<title>Servlet MaintenanceRequestCostReport</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet MaintenanceRequestCostReport at " + request.getContextPath() + "</h1>");
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
            try{
                TransitBusinessLayer logic = new  TransitBusinessLayer();
                List<Object[]> resultSet = logic.getAllMaintenanceRequests();
                
                
                if(resultSet != null){ 
                    /* TODO output your page here. You may use following sample code. */
                    out.println("<!DOCTYPE html>");
                    out.println("<html>");
                    out.println("<head>");
                    out.println("<link rel='stylesheet' href='vehicleStyle.css'>");
                    out.println("<title>Servlet MaintenanceRequestReport</title>");
                    out.println("</head>");
                    out.println("<body>");
                    out.println("<center>");
                    out.println("<h1>Maintencance Request Report by Price</h1>");
                    out.println("<table>");
                    out.println("<tr>");
                    out.println("<th>RequestID</th>");
                    out.println("<th>Request Date(yyyy-mm-dd)</th>");
                    out.println("<th>Submitted by (ManagerID)</th>");
                    out.println("<th>Vehicle Number</th>");
                    out.println("<th>Vehicle Type</th>");
                    out.println("<th>Service Description </th>");
                    out.println("<th>Quoted Cost (CAD)</th>");
                    out.println("<th>Completed</th>");
                    out.println("</tr>");
                    for(Object[] row: resultSet){
                        out.println("<tr>");
                        for(int i = 0; i <= row.length - 1 ; i++){
                            if (i <= row.length - 2){
                                out.println("<td>"+ row[i].toString() +"</td>");
                            }
                            else{
                                String value = (row[i].toString().equals("true") ) ? "YES":"NOT YET";
                                out.println("<td>"+ value +"</td>");
                            }
                        }
                        out.println("<tr>");
                    }
                    out.println("</table>");
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
