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
            out.append("<link rel='stylesheet' href='style.css'>");
            out.println("</head>");
            out.println("<body><center>");
            out.println("<h1>Vehicle Registration</h1>");
            out.println("<p>Enter the vehicle details</p>");
            out.append("<form method='POST'>"
                    + "<label for='vin'>Vehicle Number</label><br>"
                    + "<input type='text' id='vin' name='vin'><br>"
                    
                    + "<label for='type'>Vehicle Type</label><br>"
                    + "<select id='type' name='type'>"
                    + " <option value=''>Select a Vehicle Type</option>"
                    + " <option value='BUS'>Bus</option>"
                    + " <option value='ELECTRIC_TRAIN'>Electric Train</option>"
                    + " <option value='DIESEL_TRAIN'>Diesel Train</option>"
                    + "</select><br>"
                    
                    + "<label for='fueltype'>Fuel Type</label><br>"
                    + "<input type='text' id='fueltype' name='fueltype'><br>"
                    
                    + "<label for='fuelrate'>Fuel Consumption Rate</label><br>"
                    + "<input type='number' id='fuelrate' name='fuelrate' min='0' step='0.01'><br>"
                    
                    + "<label for='passengers'>Maximum Passengers</label><br>"
                    + "<input type=number id='passengers' name='passengers' min='0'><br>"
                    + "<input type='submit'>"
                    + "</form>");
            out.println("   </p><a href='/Group1_Final_Project_v1/TransitMenuView'><button>Return to Menu</button></a>");
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
//        request.getParameter(string);
//        processRequest(request, response);
        TransitBusinessLayer transitLayer;
        OperatorDTO operator = (OperatorDTO) request.getSession().getAttribute("operator");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Transit Vehicle Registration</title>");
            out.println("</head>");
            out.println("<body>");
            try {
                if(request.getSession().getAttribute("businessLayer") == null) {
                    transitLayer = new TransitBusinessLayer();
                } else {
                    transitLayer = (TransitBusinessLayer) request.getSession().getAttribute("businessLayer");
                }
                transitLayer.registerVehicle(request.getParameter("type"), request.getParameter("vin"),
                        request.getParameter("fueltype"), Float.parseFloat(request.getParameter("fuelrate")), 
                        Integer.parseInt(request.getParameter("passengers")));
                out.println("<h1>Vehicle Registration Submitted</h1>");
                out.println("<p>Vehicle successly registered</p>");
            } catch (SQLException e) {
                out.append("<h1>Database Error</h1>"
                    +   "<p>Unable to connect to the database</p>");
            } catch (IllegalArgumentException e) {
                out.append("<h1>Vehicle Registration Error</h1>"
                    +   "<p>Invalid vehicle details</p>");
            }
            out.println("<a href='/Group1_Final_Project_v1/TransitMenuView'><button>Return to Menu</button></a>");
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
