/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package ViewLayer;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author kipro
 */
@WebServlet(name = "MaintenanceRequest", urlPatterns =
{
    "/MaintenanceRequest"
})
public class MaintenanceRequest extends HttpServlet
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
            out.println("<title>Servlet MaintenanceRequest</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<center>");
            out.println("<form action=\"/Group1_Final_Project_v1/MaintenanceRequest\" method=\"post\">");
            out.println("<h1>Please fill out the form to submit a MaintenanceRequest</h1>");
            out.println("<br>");
            out.println("<br>");
            out.println("<label for=\"vtypes\">Vehicle type<label>");
            out.println("<select name=\"vtypes\">");
                out.println("<option name=\"vtype\">Bus<option>");
                out.println("<option name=\"vtype\">Electric Train<option>");
                out.println("<option name=\"vtype\">Diesel Train<option>");
            out.println("</select>");
            out.println("<br>");
            out.println("<input type= submit>");
            out.println("</form>");
            out.println("</center>");
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
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet MaintenanceRequest</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<center>");
            out.println("<form action=\"/Group1_Final_Project_v1/MaintenanceRequest\" method=\"post\">");
            out.println("<h1>Please fill out the form to submit a MaintenanceRequest</h1>");
            out.println("<br>");
            out.println("<br>");
            out.println("<label for=\"vTypes\">Vehicle type<label>");
            out.println("<select name=\"vTypes\">");
                out.println("<option value=\"bus\">Bus<option>");
                out.println("<option value=\"eTrain\">Electric Train<option>");
                out.println("<option value=\"dTrain\">Diesel Train<option>");
            out.println("</select>");
            out.println("<br>");
            out.println("<button type=\"submit\" name=\"rType\" value=\"new\">Submit New Request</button>");
            out.println("<button type=\"submit\" name=\"rType\" value=\"update\">Update Previous Request</button>");
            out.println("</form>");
            if (request.getParameter("valid").equals("false") && request.getParameter("valid") != null){
              out.println("<h2>Please select a vehicle type</h2>");  
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
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter())
        {
            
            try{
                
                String requestType = request.getParameter("rType");
                String vehicleType = request.getParameter("vTypes");
                
                if (requestType.equals("new")){
                    if (vehicleType != null){
                        switch (vehicleType){
                            case "bus":
                                response.sendRedirect("AddMaintenanceRequest?type=bus");
                                break;
                            case "eTrain":
                                response.sendRedirect("AddMaintenanceRequest?type=eTrain");
                                break;
                            case "dTrain":
                                response.sendRedirect("AddMaintenanceRequest?type=dTrain");
                                break;
                            default:
                                response.sendRedirect("MaintenanceRequest?valid=false");
                                break;
                        }
                    }
                }
                else{
                    if (vehicleType != null){
                        switch (vehicleType){
                            case "bus":
                                response.sendRedirect("UpdateMaintenanceRequest?type=bus");;
                                break;
                            case "eTrain":
                                response.sendRedirect("UpdateMaintenanceRequest?type=eTrain");
                                break;
                            case "dTrain":
                                response.sendRedirect("UpdateMaintenanceRequest?type=dTrain");
                                break;
                            default:
                                response.sendRedirect("MaintenanceRequest?valid=false");
                                break;
                       }
                    }
                }
            } catch(NullPointerException e){
                response.sendRedirect("MaintenanceRequest?valid=false");
            }
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
