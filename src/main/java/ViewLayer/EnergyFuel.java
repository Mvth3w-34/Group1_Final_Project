/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package ViewLayer;

import BusinessLayer.TransitBusinessLayer;
import TransferObjects.EnergyFuelDTO;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/*
@author: mario
*/

@WebServlet("/EnergyFuel")
public class EnergyFuel extends HttpServlet {
    private TransitBusinessLayer logicLayer;

    @Override
    public void init() throws ServletException {
        try {
            logicLayer = new TransitBusinessLayer();
        } catch (SQLException e) {
            throw new ServletException("Failed to initialize business layer", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<EnergyFuelDTO> alerts = logicLayer.getEnergyFuelAlerts();
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();

            out.println("<html><head><title>Fuel Alerts</title></head><body>");
            out.println("<h1>Vehicles with Fuel/Energy Consumption Above Threshold</h1>");
            if (alerts.isEmpty()) {
                out.println("<p>No alerts at the moment.</p>");
            } else {
                out.println("<table border='1'><tr><th>Vehicle ID</th><th>Date</th><th>Fuel (L)</th><th>Energy (kWh)</th></tr>");
                for (EnergyFuelDTO dto : alerts) {
                    out.printf("<tr><td>%s</td><td>%s</td><td>%.2f</td><td>%.2f</td></tr>",
                            dto.getVehicleId(),
                            dto.getLogDate(),
                            dto.getFuelConsumed(),
                            dto.getEnergyConsumed()
                    );
                }
                out.println("</table>");
            }
            out.println("<br><a href='index.html'>Back to Home</a>");
            out.println("</body></html>");
        } catch (SQLException e) {
            throw new ServletException("Error retrieving alerts", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String vehicleId = request.getParameter("vehicleId");
            float fuel = Float.parseFloat(request.getParameter("fuel"));
            float energy = Float.parseFloat(request.getParameter("energy"));
            float fuelThreshold = Float.parseFloat(request.getParameter("fuelThreshold"));
            float energyThreshold = Float.parseFloat(request.getParameter("energyThreshold"));
            String dateStr = request.getParameter("logDate");

            Date logDate = new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);

            EnergyFuelDTO record = new EnergyFuelDTO();
            record.setVehicleId(vehicleId);
            record.setLogDate(logDate);
            record.setFuelConsumed(fuel);
            record.setEnergyConsumed(energy);
            record.setFuelThreshold(fuelThreshold);
            record.setEnergyThreshold(energyThreshold);

            logicLayer.logEnergyFuelConsumption(record);

            response.sendRedirect("EnergyFuel"); // Redirect to GET (alert view)
        } catch (Exception e) {
            throw new ServletException("Error saving consumption", e);
        }
    }
}

