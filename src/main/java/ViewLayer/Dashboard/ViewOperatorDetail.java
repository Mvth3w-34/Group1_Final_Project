///* filename: ViewOperatorDetail.java
// * date: Apr. 6th, 2025
// * authors: Stephanie Prystupa-Maule
// * course: CST8288 O.O.P. with Design Patterns - Lab Section 023 
// * professor: Samira Ouaaz
// * coursework: Final Project - Public Transit Management System
// */
//
//package ViewLayer.Dashboard;
//        
//import BusinessLayer.OperatorPerformanceBusinessLogic;
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.sql.SQLException;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//import TransferObjects.CredentialsDTO;
//import TransferObjects.OperatorPerformanceDTO;
//
///**
// * Servlet for displaying detailed performance metrics for a specific operator
// * 
// * @author Stephanie Prystupa-Maule
// * @version 1.0
// * @since 04/06/2025
// */
//public class ViewOperatorDetail extends HttpServlet {
//    /**
//     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
//     * methods.
//     *
//     * @param request servlet request
//     * @param response servlet response
//     * @throws ServletException if a servlet-specific error occurs
//     * @throws IOException if an I/O error occurs
//     */
//    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        response.setContentType("text/html;charset=UTF-8");
//        PrintWriter out = response.getWriter();
//    
//        // Get session and check for credentials
//        HttpSession session = request.getSession(false);
//        if (session == null || session.getAttribute("credentials") == null) {
//            // If no valid session - redirect to login
//            response.sendRedirect("index.html?error=timeout");
//            return;
//        }
//        
//        // Get credentials from session
//        CredentialsDTO credentials = (CredentialsDTO) session.getAttribute("credentials");
//        
//        // Get operator ID from request
//        String operatorIdStr = request.getParameter("operatorId");
//        if (operatorIdStr == null || operatorIdStr.isEmpty()) {
//            // Try to get it from request attributes (set by front controller)
//            Object operatorIdAttr = request.getAttribute("operatorId");
//            if (operatorIdAttr != null) {
//                operatorIdStr = operatorIdAttr.toString();
//            }
//        }
//        
//        // Initialize variables
//        OperatorPerformanceDTO operator = null;
//        String errorMessage = "";
//        
//        try {
//            // Parse operator ID
//            Integer operatorId = Integer.parseInt(operatorIdStr);
//            
//            // Initialize business logic with credentials from session
//            OperatorPerformanceBusinessLogic operatorLogic = new OperatorPerformanceBusinessLogic(credentials);
//            
//            // Get operator performance data
//            operator = operatorLogic.getOperatorPerformanceById(operatorId);
//            
//            if (operator == null) {
//                errorMessage = "No data found for operator ID: " + operatorId;
//            }
//        } catch (NumberFormatException e) {
//            errorMessage = "Invalid operator ID format: " + operatorIdStr;
//        } catch (SQLException e) {
//            errorMessage = "Database error: " + e.getMessage();
//        } catch (Exception e) {
//            errorMessage = "An error occurred while retrieving operator performance data: " + e.getMessage();
//        }
//        
//        try {
//            out.println("<!DOCTYPE html>");
//            out.println("<html>");
//            out.println("<head>");
//            out.println("<title>Operator Performance Details</title>");
//            out.println("<style>");
//            out.println("body { font-family: Arial, sans-serif; margin: 20px; }");
//            out.println("table { border-collapse: collapse; width: 100%; margin-bottom: 20px; }");
//            out.println("th, td { padding: 8px; text-align: left; border: 1px solid #ddd; }");
//            out.println("th { background-color: #f2f2f2; }");
//            out.println("h1, h2, h3 { color: #333; }");
//            out.println(".metric-group { margin-bottom: 30px; }");
//            out.println(".good { color: green; }");
//            out.println(".warning { color: orange; }");
//            out.println(".poor { color: red; }");
//            out.println("</style>");
//            out.println("</head>");
//            out.println("<body>");
//            
//            // Navigation menu
//            out.println("<div>");
//            out.println("<a href='FrontController-URL?module=dashboard&action=operator_performance'>Back to Operator Performance</a> | ");
//            out.println("<a href='FrontController-URL?module=dashboard&action=return_to_menu'>Return to Main Menu</a>");
//            out.println("</div>");
//            
//            if (operator != null) {
//                out.println("<h1>Performance Details: " + operator.getOperatorName() + "</h1>");
//                out.println("<p>Operator ID: " + operator.getOperatorId() + "</p>");
//                
//                // General metrics
//                out.println("<div class='metric-group'>");
//                out.println("<h2>General Performance</h2>");
//                out.println("<table>");
//                out.println("<tr><th>Metric</th><th>Value</th></tr>");
//                
//                // Determine performance class
//                double ontimePerf = operator.getOverallOntimePerformance();
//                String ontimeClass = ontimePerf >= 90 ? "good" : (ontimePerf >= 80 ? "warning" : "poor");
//                
//                out.println("<tr><td>Total Trips</td><td>" + operator.getTotalTrips() + "</td></tr>");
//                out.println("<tr><td>Overall On-time Performance</td><td class='" + ontimeClass + "'>" + 
//                           String.format("%.1f%%", ontimePerf) + "</td></tr>");
//                
//                // Punctuality score
//                double punctScore = operator.getPunctualityScore();
//                String punctClass = punctScore >= 85 ? "good" : (punctScore >= 75 ? "warning" : "poor");
//                out.println("<tr><td>Punctuality Score</td><td class='" + punctClass + "'>" + 
//                           String.format("%.1f", punctScore) + "</td></tr>");
//                
//                out.println("<tr><td>Average Combined Variance</td><td>" + 
//                           String.format("%.0f seconds", operator.getAvgCombinedVariance()) + "</td></tr>");
//                out.println("</table>");
//                out.println("</div>");
//                
//                // On-time metrics
//                out.println("<div class='metric-group'>");
//                out.println("<h2>On-time Performance Breakdown</h2>");
//                out.println("<table>");
//                out.println("<tr><th>Metric</th><th>Value</th></tr>");
//                out.println("<tr><td>On-time Arrivals</td><td>" + 
//                           String.format("%.1f%%", operator.getPctOntimeArrivals()) + "</td></tr>");
//                out.println("<tr><td>On-time Departures</td><td>" + 
//                           String.format("%.1f%%", operator.getPctOntimeDepartures()) + "</td></tr>");
//                out.println("</table>");
//                out.println("</div>");
//                
//                // Early metrics
//                out.println("<div class='metric-group'>");
//                out.println("<h2>Early Performance Metrics</h2>");
//                out.println("<table>");
//                out.println("<tr><th>Metric</th><th>Value</th></tr>");
//                out.println("<tr><td>Early Arrivals</td><td>" + 
//                           String.format("%.1f%%", operator.getPctEarlyArrivals()) + "</td></tr>");
//                out.println("<tr><td>Average Early Arrival Time</td><td>" + 
//                           String.format("%.0f seconds", operator.getAvgEarlyArrivalTime()) + "</td></tr>");
//                out.println("<tr><td>Early Departures</td><td>" + 
//                           String.format("%.1f%%", operator.getPctEarlyDepartures()) + "</td></tr>");
//                out.println("<tr><td>Average Early Departure Time</td><td>" + 
//                           String.format("%.0f seconds", operator.getAvgEarlyDepartureTime()) + "</td></tr>");
//                out.println("</table>");
//                out.println("</div>");
//                
//                // Late metrics
//                out.println("<div class='metric-group'>");
//                out.println("<h2>Late Performance Metrics</h2>");
//                out.println("<table>");
//                out.println("<tr><th>Metric</th><th>Value</th></tr>");
//                out.println("<tr><td>Late Arrivals</td><td>" + 
//                           String.format("%.1f%%", operator.getPctLateArrivals()) + "</td></tr>");
//                out.println("<tr><td>Average Late Arrival Time</td><td>" + 
//                           String.format("%.0f seconds", operator.getAvgLateArrivalTime()) + "</td></tr>");
//                out.println("<tr><td>Late Departures</td><td>" + 
//                           String.format("%.1f%%", operator.getPctLateDepartures()) + "</td></tr>");
//                out.println("<tr><td>Average Late Departure Time</td><td>" + 
//                           String.format("%.0f seconds", operator.getAvgLateDepartureTime()) + "</td></tr>");
//                out.println("</table>");
//                out.println("</div>");
//                
//                // Performance evaluation and recommendations
//                out.println("<div class='metric-group'>");
//                out.println("<h2>Performance Evaluation</h2>");
//                
//                // Generate evaluation based on metrics
//                if (ontimePerf >= 90) {
//                    out.println("<p class='good'>Excellent performance. This operator consistently maintains high on-time rates.</p>");
//                } else if (ontimePerf >= 80) {
//                    out.println("<p class='warning'>Good performance with room for improvement. Consider reviewing specific routes or time periods.</p>");
//                } else {
//                    out.println("<p class='poor'>Performance needs significant improvement. Recommend additional training and supervision.</p>");
//                }
//                
//                // Add specific recommendations based on metrics
//                out.println("<h3>Recommendations:</h3>");
//                out.println("<ul>");
//                
//                // Early/Late pattern analysis
//                if (operator.getPctEarlyArrivals() > 15 || operator.getPctEarlyDepartures() > 15) {
//                    out.println("<li>Address tendency to arrive/depart early which can cause riders to miss connections.</li>");
//                }
//                
//                if (operator.getPctLateArrivals() > 15 || operator.getPctLateDepartures() > 15) {
//                    out.println("<li>Review route timing and address consistent late arrivals/departures.</li>");
//                }
//                
//                // Schedule adherence
//                if (operator.getAvgCombinedVariance() > 180) { // More than 3 minutes average variance
//                    out.println("<li>Schedule adherence training recommended to improve timing consistency.</li>");
//                }
//                
//                // General recommendation
//                out.println("<li>Regular performance reviews should be conducted to monitor progress.</li>");
//                
//                out.println("</ul>");
//                out.println("</div>");
//                
//            } else if (!errorMessage.isEmpty()) {
//                out.println("<h1>Error</h1>");
//                out.println("<p style=\"color: red;\">" + errorMessage + "</p>");
//                out.println("<p><a href='FrontController-URL?module=dashboard&action=operator_performance'>Return to Operator Performance Dashboard</a></p>");
//            }
//            
//            out.println("</body>");
//            out.println("</html>");
//        } finally {
//            out.close();
//        }
//    }
//        
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        processRequest(request, response);
//    }
//
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        processRequest(request, response);
//    }
//
//    @Override
//    public String getServletInfo() {
//        return "Operator Performance Detail Servlet";
//    }
//}
