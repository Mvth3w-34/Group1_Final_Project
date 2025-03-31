/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package ViewLayer;

import BusinessLayer.TransitBusinessLayer;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import TransferObjects.*;
import java.sql.SQLException;

/**
 *
 * @author johnt
 */
public class RegisterVehicle extends HttpServlet {

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
        OperatorDTO op = (OperatorDTO) request.getSession().getAttribute("operator");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Transit Vehicle Registration</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Vehicle Registration</h1>");
            out.println("<p>Enter the vehicle details</p>");
            out.append("<form method='POST'>"
                    + "<label for='vin'>Vehicle Number</label>"
                    + "<input type='text' id='vin' name='vin'><br>"
                    
                    + "<label for='type'>Vehicle Type</label>"
                    + "<select id='type' name='type'>"
                    + " <option value=''>Select a Vehicle Type</option>"
                    + " <option value='DIESEL_BUS'>Diesel Bus</option>"
                    + " <option value='ELECTRIC_LIGHT_RAIL'>Electric Light Rail</option>"
                    + " <option value='DIESEL_ELECTRIC_TRAIN'>Hybrid Train</option>"
                    + "</select><br>"
                    
                    + "<label for='fueltype'>Fuel Type</label>"
                    + "<input type='text' id='fueltype' name='fueltype'><br>"
                    
                    + "<label for='fuelrate'>Fuel Consumption Rate</label>"
                    + "<input type='number' id='fuelrate' name='fuelrate' min='0' step='0.01'><br>"
                    
                    + "<label for='passengers'>Maximum Passengers</label>"
                    + "<input type=number= id='passengers' name='passengers' min='0'><br>"
                    + "<input type='submit'>"
                    + "</form>");
            out.println("   </p><a href='/Group1_Final_Project_v1/TransitMenuView'>Return to Menu</a>");
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
//        request.getParameter(string);
//        processRequest(request, response);
        TransitBusinessLayer transitLayer;
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Transit Vehicle Registration</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Vehicle Registration Submitted</h1>");
            try {
                if(request.getSession().getAttribute("businessLayer") == null) {
                    transitLayer = new TransitBusinessLayer();
                } else {
                    transitLayer = (TransitBusinessLayer) request.getSession().getAttribute("businessLayer");
                }
                transitLayer.registerVehicle(request.getParameter("type"), request.getParameter("vin"),
                        request.getParameter("fueltype"), Float.parseFloat(request.getParameter("fuelrate")), 
                        Integer.parseInt(request.getParameter("passengers")));
                out.println("<p>Vehicle Registration Submitted</p>");
            } catch (SQLException e) {
                out.println("<h1>Database Error</h1>"
                    +   "<p>Unable to connect to the database</p>");
            }
            out.println("   </p><a href='/Group1_Final_Project_v1/TransitMenuView'>Return to Menu</a>");
            out.println("</body>");
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
