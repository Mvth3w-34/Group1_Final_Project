/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package ViewLayer;

import BusinessLayer.TransitBusinessLayer;
import TransferObjects.VehicleDTO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author johnt
 */
public class ViewVehicles extends HttpServlet {

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
        List<VehicleDTO> vehiclesList;
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Transit Fleet</title>");
            out.append("<link rel='stylesheet' href='vehicleStyle.css'>");
            out.println("</head>");
            out.println("<body><center>");
            out.println("<h1>Viewing Transit Fleet</h1>");
            try {
                if(request.getSession().getAttribute("businessLayer") == null) {
                    logicLayer = new TransitBusinessLayer();
                    request.getSession().setAttribute("businessLayer", logicLayer);
                } else {
                    logicLayer = (TransitBusinessLayer) request.getSession().getAttribute("businessLayer");
                }
                vehiclesList = logicLayer.getVehicles();
                if (vehiclesList.isEmpty()) {
                    out.append("<p>Please register a vehicle</p>");
                } else {
                    out.append("<table><tr>");
                    // Print table headers
                    // TODO: Need to include arrival/departure times
                    for (int i = 0; i < logicLayer.getVehicleHeaders().size(); i++) {
                        out.append("<th>" + logicLayer.getVehicleHeaders().get(i) + "</th>");
                    }
                    out.append("</tr>");
                    for (int i = 0; i < vehiclesList.size(); i++) {
                        out.append("<tr><td>" + vehiclesList.get(i).getVehicleID() + "</td>")
                        .append("<td>" + vehiclesList.get(i).getVehicleType().name() + "</td>")
                        .append("<td>" + vehiclesList.get(i).getVIN() + "</td>")
                        .append("<td>" + vehiclesList.get(i).getFuelType() + "</td>")
                        .append("<td>" + vehiclesList.get(i).getFuelRate() + "</td>")
                        .append("<td>" + vehiclesList.get(i).getMaxPassengers() + "</td>")
                        .append("<td>" + vehiclesList.get(i).getRoute() + "</td>")
                        .append("</tr>");
                    }
                    out.append("</table>");
                }
                out.println("<a href='/Group1_Final_Project_v1/TransitMenuView'><button>Return to Menu</button></a>");
            } catch (SQLException e) {
                out.append("<h1>Database Error</h1>")
                    .append("<p>Unable to connect to the database</p>")
                    .append("   <a href='/Group1_Final_Project_v1/TransitFrontController'><button>Return to Login</button></a>");
            }
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
