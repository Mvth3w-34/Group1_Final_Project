/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package ViewLayer;

import BusinessLayer.TransitBusinessLayer;
import TransferObjects.VehicleStationTimetable;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author johnt
 */
public class ViewVehicleTimestamp extends HttpServlet {

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
        TransitBusinessLayer logicLayer;
        List<VehicleStationTimetable> vehicleTimetables;
        int vehicleID = Integer.parseInt(request.getParameter("vehicleID") == null ? "1": request.getParameter("vehicleID")) > 0 
                ? Integer.parseInt(request.getParameter("vehicleID")) : 1;
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ViewVehicleTimestamp</title>");
            out.append("<link rel='stylesheet' href='vehicleStyle.css'>");
            out.println("</head>");
            out.println("<body><center>");
            out
                .append("<h1>Viewing Details of Vehicle " + request.getParameter("vehicleID") + "</h1>");
            try {
                if(request.getSession().getAttribute("businessLayer") == null) {
                    logicLayer = new TransitBusinessLayer();
                    request.getSession().setAttribute("businessLayer", logicLayer);
                } else {
                    logicLayer = (TransitBusinessLayer) request.getSession().getAttribute("businessLayer");
                }
                vehicleTimetables = logicLayer.getRoutes(vehicleID);
                if (vehicleTimetables.isEmpty()) {
                    out.append("<p>No timetable available for the vehicle</p>");
                } else {
                    out.append("<table><tr>");
                    for (int i = 0; i < logicLayer.getHeaders("vehicleroutes").size(); i++) {
                        out.append("<th");
                        if (i > 0 && i < logicLayer.getHeaders("vehicleroutes").size()) {
                            out.append(" class='middle'");
                        }
                        out.append(">" + logicLayer.getHeaders("vehicleroutes").get(i) + "</th>");
                    }
                    out.append("</tr>");
                    for (int i = 0; i < vehicleTimetables.size(); i++) {
                    String departureTime = vehicleTimetables.get(i).getDepartureTime() == null ? "N/A" : vehicleTimetables.get(i).getDepartureTime().toString();
                    out
                        .append("<tr><td>" + vehicleTimetables.get(i).getVehicleID() + "</td>") // Remove column
                        .append("<td class='middle'>" + vehicleTimetables.get(i).getStationName() + "</td>")
                        .append("<td class='middle'>" + vehicleTimetables.get(i).checkStation() + "</td>") // Remove column
                        .append("<td class='middle'>" + vehicleTimetables.get(i).getArrivalTime() + "</td>")
                        .append("<td class='middle'>" + departureTime + "</td>")
                        .append("</tr>");
                    }
                    out.append("</table>");
                }
            } catch (SQLException e) {
                e.printStackTrace();
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
