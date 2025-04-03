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
public class AssignRoutes extends HttpServlet {

    
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
        TransitBusinessLayer transitLayer;
        List<VehicleDTO> vehicles;
        List<Integer> routesSchedules;
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet AssignRoutes</title>");
            out.append("<link rel='stylesheet' href='style.css'>");
            out.println("</head>");
            out.println("<body><center>");
            out.println("<h1>Assign a Route</h1>");
            
            try {
                transitLayer = new TransitBusinessLayer();
                vehicles = transitLayer.getVehicles();
                out.append("<form method='POST'>"
                        + "<label for='vehicleid'>Vehicle ID</label><br>"
                        + "<select id='vehicleid' name='vehicleid'>");
                out.append("<option value=''>Select a vehicle ID</option>");
                for (int i = 0; i < vehicles.size(); i++) {
                    out.println("<option value='" + vehicles.get(i).getVehicleID() + "'>" + vehicles.get(i).getVehicleID()+"</option>");
                }
                out
                    .append("</select><br>")
                    .append("<label for='route'>Route</label><br>");
                out.append("<select id='route' name='route'>")
                    .append("<option value=''>Select a Route</option>")
                ;
                routesSchedules = transitLayer.getRoutes();
                for(int i = 0; i < routesSchedules.size(); i++) {
                    out.println("<option value='" + routesSchedules.get(i) + "'>" + routesSchedules.get(i) + "</option>");
                }
                out.append("</select><br>");
                out
                    .append("<label for='fuel'>Fuel Type</label><br>")
                    .append("<input type='text' id='fuel' name='fuel'><br>")
                    .append("<input type='submit'></form>"
                );
            } catch (SQLException e) {
                out.append("<p>Database Error: Unable to update a vehicle</p>");
            }
            out.println("<a href='/Group1_Final_Project_v1/TransitMenuView'><button>Return to Menu</button></a>");

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
        TransitBusinessLayer transitLayer;
        String vehIdInput;
        
        if (request.getParameter("vehicleid").equals("")) {
            vehIdInput = "0";
        } else {
            vehIdInput = request.getParameter("vehicleid");
        }
        int vehId = Integer.parseInt(vehIdInput);
        String route = request.getParameter("route").equals("") ? null : request.getParameter("route");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet AssignRoutes</title>");
            out.append("<link rel='stylesheet' href='style.css'>");
//            out.append("<meta http-equiv='refresh' content='0; url = /Group1_Final_Project_v1/TransitMenuView''>");
            out.println("</head>");
            out.append("<body><center>");
        
            try {
                if (vehId == 0) {
                    throw new SQLException();
                }
                if(request.getSession().getAttribute("businessLayer") == null) {
                    transitLayer = new TransitBusinessLayer();
                } else {
                    transitLayer = (TransitBusinessLayer) request.getSession().getAttribute("businessLayer");
                }
                for (int i = 0; i < transitLayer.getVehicles().size(); i++) {
                    if (transitLayer.getVehicles().get(i).getVehicleID() == vehId) {
                        transitLayer.updateVehicle(request.getParameter("fuel"), route, transitLayer.getVehicles().get(i));
                    }
                }
                out.append("<p>Vehicle successfully updated</p>");
            } catch (SQLException e) {
                out.append("<p>Database Error: Unable to update a vehicle</p>");
            }
            out.println("<a href='/Group1_Final_Project_v1/TransitMenuView'><button>Return to Menu</button></a>");
            out.append("</center></body></html>");
        }
//        request.getRequestDispatcher("/TransitMenuView");
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
