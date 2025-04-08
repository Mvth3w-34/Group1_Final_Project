/* filename: LogTime.java
 * date: Apr. 6th, 2025
 * authors: John Tieu
 * course: CST8288 O.O.P. with Design Patterns - Lab Section 023 
 * professor: Samira Ouaaz
 * coursework: Final Project - Public Transit Management System
 */
package ViewLayer;

import BusinessLayer.TransitBusinessLayer;
import TransferObjects.OperatorDTO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The webpage for operators to log their times
 * @author John Tieu
 * @version 1.0
 * @since 21
 */
public class LogTime extends HttpServlet {

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
        OperatorDTO operator = (OperatorDTO) request.getSession().getAttribute("operator");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet LogTime</title>");
            out.append("<link rel='stylesheet' href='style.css'>");
            out.println("</head>");
            out.println("<body><center>");
            out.println("<h1>Servlet LogTime at " + request.getContextPath() + "</h1>");
            out.append("<form method='POST'>")
                .append("<label for='operatorid'>Operator ID</label><br>")
                .append("<input type='number' readonly id='operatorid' style='background-color: lightgray;' name='operatorid' value='" + operator.getOperatorId()+ "'><br>")
                .append("<label for='starttime'>Start Time</label><br>")
                .append("<input type='time' id='starttime' name='starttime'><br>")
                .append("<label for='endtime'>End Time</label><br>")
                .append("<input type='time' id='endtime' name='endtime'><br>")
                .append("<p>Select the punch-out reason: </p>")
                .append("<input type='radio' value='break' id='break' name='punchtype'><label class='punchlabelseperator' id='punchlabel' for='break'>Break</label>")
                .append("<input type='radio' value='finish' id='finish' name='punchtype'><label for='finish'>End Shift</label><br>")
                .append("<input type='submit'>")
                .append("</form>")
            ;
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
        int id = Integer.parseInt(request.getParameter("operatorid"));
        String start = request.getParameter("starttime"); // Start time
        String end = request.getParameter("endtime"); // Endtime
        String punch = request.getParameter("punchtype"); // Punchout type
        TransitBusinessLayer logicLayer = (TransitBusinessLayer)request.getSession().getAttribute("businessLayer");
//        processRequest(request, response);
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet LogTime</title>");
            out.append("<link rel='stylesheet' href='style.css'>");
            out.println("</head>");
            out.println("<body><center>");
            out.println("<h1>Servlet LogTime at " + request.getContextPath() + "</h1>");
            if (start.equals("") && end.equals("") && punch == null) {
                out.append("<p>Invalid log times entered</p>");
            } else {
                try {
                    logicLayer.logTime(id, start, end, punch);
                    out.append("<p>Timestamp successfully added</p>");
                } catch (SQLException e) {
                    out.append("<p>Unable to log timestamp</p>");
                } catch (IllegalArgumentException e) {
                    out.append("<p>Bad data inputs</p>");
                }
            }
            out.println("<a href='/Group1_Final_Project_v1/TransitMenuView'><button>Return to Menu</button></a>");
            out.println("</center></body>");
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
