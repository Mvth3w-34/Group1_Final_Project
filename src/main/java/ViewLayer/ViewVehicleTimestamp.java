/* filename: ViewVehicleTimestamp.java
 * date: April 6th, 2025
 * authors: John Tieu
 * course: CST8288 O.O.P. with Design Patterns - Lab Section 023 
 * professor: Samira Ouaaz
 * coursework: Final Project - Public Transit Management System
 */

package ViewLayer;

import BusinessLayer.TransitBusinessLayer;
import TransferObjects.VehicleStationTimetable;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * The webpage for viewing the vehicle's timestamps of each station
 * @author John Tieu
 * @version 1.0
 * @since 21
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
                    for (int i = 1; i < logicLayer.getHeaders("vehicleroutes").size(); i++) {
                        if (i == 0 || i == 2) {
                            continue;
                        } else {
                        out.append("<th");
                            if (i > 1 && i < logicLayer.getHeaders("vehicleroutes").size()) {
                                out.append(" class='middle'");
                            }
                            out.append(">" + logicLayer.getHeaders("vehicleroutes").get(i) + "</th>");
                        }
                    }
                    out.append("</tr>");
                    for (int i = 0; i < vehicleTimetables.size(); i++) {
                        String arrivalDT = vehicleTimetables.get(i)
                                .getArrivalTime()
                                .toLocalDateTime().format(DateTimeFormatter.ISO_LOCAL_TIME);
                        out.append("<tr><td>" + vehicleTimetables.get(i).getStationName() + "</td>")
                            .append("<td class='middle'>" + arrivalDT + "</td>");
                        if (vehicleTimetables.get(i).getDepartureTime() != null) {
                            String departDT = vehicleTimetables.get(i)
                                .getDepartureTime()
                                .toLocalDateTime().format(DateTimeFormatter.ISO_LOCAL_TIME);
                            out.append("<td class='middle'>" + departDT + "</td>");
                        } else {
                            out.append("<td class='middle'>N/A</td>");
                        }
                        out
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
