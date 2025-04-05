/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package ViewLayer;

import BusinessLayer.TransitBusinessLayer;
import TransferObjects.MaintenanceRequestTicketDTO;
import TransferObjects.OperatorDTO;
import TransferObjects.VehicleComponentDTO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Random;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author kipro
 */
@WebServlet(name = "AddMaintenanceRequest", urlPatterns =
{
    "/AddMaintenanceRequest"
})
public class AddMaintenanceRequest extends HttpServlet
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
            out.println("<title>Servlet AddMaintenanceRequest</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AddMaintenanceRequest at " + request.getContextPath() + "</h1>");
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
            
//If the vehicle type is a bus
            if (request.getParameter("type")!= null && request.getParameter("type").equals("bus")){
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Servlet MaintenanceRequest</title>");
                out.println("</head>");
                out.println("<body>");
                out.println("<center>");
                out.println("<form action=\"/Group1_Final_Project_v1/AddMaintenanceRequest\" method=\"post\">");
                out.println("<h1>Please fill out the form to submit a MaintenanceRequest</h1>");
                out.println("<br>");
                out.println("<br>");
                out.println("<label for=\"vID\">Vehicle ID<label>");
                out.println("<input type=\"text\" name=\"vID\"");
                out.println("<br>");
                out.println("<br>");
                out.println("<label for=\"comps\">Vehicle Component<label>");
                out.println("<select name=\"comps\">");
                    out.println("<option value=\"1\">Rear Brakes<option>");
                    out.println("<option value=\"2\">Front Brakes<option>");
                    out.println("<option value=\"3\">Left Rear Tire<option>");
                    out.println("<option value=\"4\">Left Front Tire<option>");
                    out.println("<option value=\"5\">Right Front Tire<option>");
                    out.println("<option value=\"6\">Right Rear Tire<option>");
                    out.println("<option value=\"7\">Rear Axel Bearings<option>");
                    out.println("<option value=\"8\">Rear Axel Bearings<option>");
                    out.println("<option value=\"12\">Diesel Engine<option>");
                out.println("</select>");
                out.println("<br>");
                out.println("<br>");
                out.println("<label for=\"sDesc\">Service Description<label>");
                out.println("<input type=\"text\" name=\"sDesc\">");
                out.println("<br>");
                out.println("<br>");
                out.println("<button type=\"submit\" name=\"rType\" value=\"new\">Submit New Request</button>");
                out.println("<input type=\"hidden\" name=\"type\" value=\"bus\">");
                out.println("</form>");
            }
            else if (request.getParameter("type")!= null && request.getParameter("type").equals("eTrain")){
                //If the vehicle type is an electric train
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Servlet MaintenanceRequest</title>");
                out.println("</head>");
                out.println("<body>");
                out.println("<center>");
                out.println("<form action=\"/Group1_Final_Project_v1/AddMaintenanceRequest\" method=\"post\">");
                out.println("<h1>Please fill out the form to submit a MaintenanceRequest</h1>");
                out.println("<br>");
                out.println("<br>");
                out.println("<label for=\"vID\">Vehicle ID<label>");
                out.println("<input type=\"text\" name=\"vID\"");
                out.println("<br>");
                out.println("<br>");
                out.println("<label for=\"comps\">Vehicle Component<label>");
                out.println("<select name=\"comps\">");
                    out.println("<option value=\"1\">Rear Brakes<option>");
                    out.println("<option value=\"2\">Front Brakes<option>");
                    out.println("<option value=\"9\">Canternary<option>");
                    out.println("<option value=\"10\">Circuit Breaker<option>");
                    out.println("<option value=\"11\">Pantograph<option>");
                    out.println("<option value=\"13\">Electric Engine<option>");
                out.println("</select>");
                out.println("<br>");
                out.println("<br>");
                out.println("<label for=\"sDesc\">Service Description<label>");
                out.println("<input type=\"text\" name=\"sDesc\">");
                out.println("<br>");
                out.println("<br>");
                out.println("<button type=\"submit\" name=\"rType\" value=\"new\">Submit New Request</button>");
                out.println("<input type=\"hidden\" name=\"type\" value=\"eTrain\">");
                out.println("</form>");
            }
            else if (request.getParameter("type")!= null && request.getParameter("type").equals("dTrain")){
                //If the vehicle type is a diesel train
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Servlet MaintenanceRequest</title>");
                out.println("</head>");
                out.println("<body>");
                out.println("<center>");
                out.println("<form action=\"/Group1_Final_Project_v1/AddMaintenanceRequest\" method=\"post\">");
                out.println("<h1>Please fill out the form to submit a MaintenanceRequest</h1>");
                out.println("<br>");
                out.println("<br>");
                out.println("<label for=\"vID\">Vehicle ID<label>");
                out.println("<input type=\"text\" name=\"vID\"");
                out.println("<br>");
                out.println("<br>");
                out.println("<label for=\"comps\">Vehicle Component<label>");
                out.println("<select name=\"comps\">");
                    out.println("<option value=\"1\">Rear Brakes<option>");
                    out.println("<option value=\"2\">Front Brakes<option>");
                    out.println("<option value=\"9\">Canternary<option>");
                    out.println("<option value=\"10\">Circuit Breaker<option>");
                    out.println("<option value=\"11\">Pantograph<option>");
                    out.println("<option value=\"12\">Diesel Engine<option>");
                out.println("</select>");
                out.println("<br>");
                out.println("<br>");
                out.println("<label for=\"sDesc\">Service Description<label>");
                out.println("<input type=\"text\" name=\"sDesc\">");
                out.println("<br>");
                out.println("<br>");
                out.println("<button type=\"submit\" name=\"rType\" value=\"new\">Submit New Request</button>");
                out.println("<input type=\"hidden\" name=\"type\" value=\"dTrain\">");
                out.println("</form>");
            }
            else{
                response.sendRedirect("MaintenanceRequest");
            }
            
            out.println("<a href=\"/Group1_Final_Project_v1/TransitMenuView\"><button>Return to Main Menu</button></a>");
            if (request.getParameter("exists").equals("false") && request.getParameter("exists") != null){
              out.println("<h2>Error! Either the vehicle or the component for that vehicle does not exist!</h2>");  
              out.println("<h2>Please you fill all of the required fields with valid entries</h2>");  
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
        try{
            String description = request.getParameter("sDesc");
            int vehicleID = Integer.parseInt(request.getParameter("vID"));
            int componentID = Integer.parseInt(request.getParameter("comps"));
            TransitBusinessLayer logic = new TransitBusinessLayer();
            VehicleComponentDTO vehicleComponent = logic.getComponentByIDs(vehicleID, componentID);
            
            if(!description.isEmpty() && vehicleComponent != null){
                Random r = new Random();
                double cost = 300 + (1000 - 300) * r.nextDouble(); //Calculates a random cost for the service
                

                OperatorDTO operator = (OperatorDTO) request.getSession().getAttribute("operator");

                MaintenanceRequestTicketDTO ticket = new MaintenanceRequestTicketDTO();

                ticket.setRequestDate(LocalDateTime.now());
                ticket.setQuotedCost(cost);
                ticket.setOperatorID(operator.getOperatorID());
                ticket.setVehicleComponentID(vehicleComponent.getVehicleComponentID());
                ticket.setServiceDescription(description.toUpperCase());
                ticket.setIsComplete(false);

                logic.addMaintenanceRequest(ticket);
                request.getRequestDispatcher("succesfulAction.html").forward(request, response);
            }          
            else {
                response.sendRedirect("AddMaintenanceRequest?exists=false&type="+request.getParameter("type"));
            }
        } catch (NullPointerException | NumberFormatException | SQLException e) {
            response.sendRedirect("AddMaintenanceRequest?exists=false&type="+request.getParameter("type"));
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
