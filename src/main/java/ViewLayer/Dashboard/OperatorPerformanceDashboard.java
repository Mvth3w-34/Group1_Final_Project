/* filename: OperatorPerformanceDashboard.java
 * date: Apr. 6th, 2025
 * authors: Stephanie Prystupa-Maule
 * course: CST8288 O.O.P. with Design Patterns - Lab Section 023 
 * professor: Samira Ouaaz
 * coursework: Final Project - Public Transit Management System
 */

package ViewLayer.Dashboard;
        
import BusinessLayer.OperatorPerformanceBusinessLogic;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import TransferObjects.CredentialsDTO;
import TransferObjects.OperatorPerformanceDTO;
import ViewLayer.Dashboard.OperatorPerformanceView;
import ViewLayer.Dashboard.OperatorPerformancePresenter;

/**
 * Servlet for displaying operator performance metrics in a dashboard format.
 * Implements the OperatorPerformanceView interface for the MVP pattern.
 * 
 * @author Stephanie Prystupa-Maule
 * @version 2.0
 * @since 04/06/2025
 */
public class OperatorPerformanceDashboard extends HttpServlet implements OperatorPerformanceView {
    
    private OperatorPerformancePresenter presenter;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private List<OperatorPerformanceDTO> operatorsList;
    private String errorMessage = "";
    private String successMessage = "";
    private double averageOntimePerformance = 0.0;
    private boolean isLoading = false;
    
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
        
        this.request = request;
        this.response = response;
        
        // Get session and check for credentials
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("credentials") == null) {
            // If no valid session - redirect to login
            response.sendRedirect("index.html?error=timeout");
            return;
        }
        
        // Get credentials from session
        CredentialsDTO credentials = (CredentialsDTO) session.getAttribute("credentials");
        
        // Initialize presenter if not already done
        if (presenter == null) {
            presenter = new OperatorPerformancePresenter(this, credentials);
        }
        
        // Let the presenter handle the business logic
        presenter.initializeView();
        
        // Render the view
        renderHtml();
    }
    
    /**
     * Renders the HTML output to the response
     * 
     * @throws IOException if an I/O error occurs
     */
    private void renderHtml() throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        try {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Operator Performance Dashboard</title>");
            out.println("<style>");
            out.println("table { border-collapse: collapse; width: 100%; }");
            out.println("th, td { padding: 8px; text-align: left; border: 1px solid #ddd; }");
            out.println("th { background-color: #f2f2f2; }");
            out.println("tr:nth-child(even) { background-color: #f9f9f9; }");
            out.println("tr:hover { background-color: #f2f2f2; }");
            out.println(".good { color: green; }");
            out.println(".warning { color: orange; }");
            out.println(".poor { color: red; }");
            out.println(".dashboard-controls { margin-bottom: 20px; padding: 10px; background-color: #f9f9f9; border-radius: 5px; }");
            out.println("</style>");
            out.println("</head>");
            out.println("<body>");
            
            out.println("<h1>Operator Performance Dashboard</h1>");
            
            // Navigation menu
            out.println("<div>");
            out.println("<a href='FrontController-URL?module=dashboard&action=operator_performance'>Operator Performance</a> | ");
            out.println("<a href='FrontController-URL?module=dashboard&action=route_performance'>Route Performance</a> | ");
            out.println("<a href='FrontController-URL?module=dashboard&action=system_overview'>System Overview</a> | ");
            out.println("<a href='FrontController-URL?module=dashboard&action=return_to_menu'>Return to Main Menu</a>");
            out.println("</div>");
            
            // Display loading message if applicable
            if (isLoading) {
                out.println("<div style='padding: 20px; background-color: #f5f5f5; text-align: center;'>");
                out.println("<p>Loading data, please wait...</p>");
                out.println("</div>");
            }
            
            // Display error message if applicable
            if (errorMessage != null && !errorMessage.isEmpty()) {
                out.println("<div style='padding: 10px; margin: 10px 0; background-color: #ffeeee; border-left: 5px solid #ff0000;'>");
                out.println("<p style='color: #700;'><strong>Error:</strong> " + errorMessage + "</p>");
                out.println("</div>");
            }
            
            // Display success message if applicable
            if (successMessage != null && !successMessage.isEmpty()) {
                out.println("<div style='padding: 10px; margin: 10px 0; background-color: #eeffee; border-left: 5px solid #00cc00;'>");
                out.println("<p style='color: #070;'><strong>Success:</strong> " + successMessage + "</p>");
                out.println("</div>");
            }
            
            // Filter and sort controls
            out.println("<div class='dashboard-controls'>");
            out.println("<form action='FrontController-URL' method='GET'>");
            out.println("<input type='hidden' name='module' value='dashboard'>");
            out.println("<input type='hidden' name='action' value='operator_performance'>");
            
            // Sorting options
            out.println("<label for='sort'>Sort by:</label>");
            out.println("<select name='sort' id='sort'>");
            out.println("<option value='name'" + ("name".equals(getSortMetric()) ? " selected" : "") + ">Operator Name</option>");
            out.println("<option value='ontime'" + ("ontime".equals(getSortMetric()) ? " selected" : "") + ">On-time Performance</option>");
            out.println("<option value='trips'" + ("trips".equals(getSortMetric()) ? " selected" : "") + ">Total Trips</option>");
            out.println("<option value='early'" + ("early".equals(getSortMetric()) ? " selected" : "") + ">Early Arrivals/Departures</option>");
            out.println("<option value='late'" + ("late".equals(getSortMetric()) ? " selected" : "") + ">Late Arrivals/Departures</option>");
            out.println("<option value='variance'" + ("variance".equals(getSortMetric()) ? " selected" : "") + ">Average Variance</option>");
            out.println("</select>");
            
            // Ascending/Descending
            out.println("<select name='order' id='order'>");
            out.println("<option value='asc'" + (getSortOrder() ? " selected" : "") + ">Ascending</option>");
            out.println("<option value='desc'" + (!getSortOrder() ? " selected" : "") + ">Descending</option>");
            out.println("</select>");
            
            // Filtering options
            out.println("<label for='filterType'>Filter:</label>");
            out.println("<select name='filterType' id='filterType'>");
            out.println("<option value='all'" + ("all".equals(getFilterType()) ? " selected" : "") + ">All Operators</option>");
            out.println("<option value='above'" + ("above".equals(getFilterType()) ? " selected" : "") + ">Above Threshold</option>");
            out.println("<option value='below'" + ("below".equals(getFilterType()) ? " selected" : "") + ">Below Threshold</option>");
            out.println("</select>");
            
            // Threshold input
            Double threshold = getThresholdValue();
            out.println("<label for='threshold'>Threshold (%):</label>");
            out.println("<input type='number' name='threshold' id='threshold' min='0' max='100' step='0.1' value='" + 
                       (threshold != null ? threshold : "85") + "'>");
            
            out.println("<button type='submit'>Apply</button>");
            out.println("</form>");
            out.println("</div>");
            
            // Display operator performance if found
            if (operatorsList != null && !operatorsList.isEmpty()) {
                out.println("<h3>Operator Performance Metrics:</h3>");
                out.println("<table border=\"1\">");
                out.println("<tr>");
                out.println("<th>Operator ID</th>");
                out.println("<th>Operator Name</th>");
                out.println("<th>Total Trips</th>");
                out.println("<th>On-time Performance</th>");
                out.println("<th>Early Arrivals</th>");
                out.println("<th>Early Departures</th>");
                out.println("<th>Late Arrivals</th>");
                out.println("<th>Late Departures</th>");
                out.println("<th>Avg. Combined Variance</th>");
                out.println("<th>Punctuality Score</th>");
                out.println("</tr>");
                
                for (OperatorPerformanceDTO operator : operatorsList) {
                    // format output into table
                    out.println("<tr>");
                    out.println("<td>" + operator.getOperatorId() + "</td>");
                    out.println("<td>" + operator.getOperatorName() + "</td>");
                    out.println("<td>" + operator.getTotalTrips() + "</td>");
                    
                    // On-time performance with color coding
                    double ontimePerf = operator.getOverallOntimePerformance();
                    String ontimeClass = ontimePerf >= 90 ? "good" : (ontimePerf >= 80 ? "warning" : "poor");
                    out.println("<td class='" + ontimeClass + "'>" + String.format("%.1f%%", ontimePerf) + "</td>");
                    
                    // Early metrics
                    out.println("<td>" + String.format("%.1f%%", operator.getPctEarlyArrivals()) + 
                              " (avg: " + String.format("%.0f", operator.getAvgEarlyArrivalTime()) + "s)</td>");
                    out.println("<td>" + String.format("%.1f%%", operator.getPctEarlyDepartures()) + 
                              " (avg: " + String.format("%.0f", operator.getAvgEarlyDepartureTime()) + "s)</td>");
                    
                    // Late metrics
                    out.println("<td>" + String.format("%.1f%%", operator.getPctLateArrivals()) + 
                              " (avg: " + String.format("%.0f", operator.getAvgLateArrivalTime()) + "s)</td>");
                    out.println("<td>" + String.format("%.1f%%", operator.getPctLateDepartures()) + 
                              " (avg: " + String.format("%.0f", operator.getAvgLateDepartureTime()) + "s)</td>");
                    
                    // Variance
                    out.println("<td>" + String.format("%.0f", operator.getAvgCombinedVariance()) + "s</td>");
                    
                    // Punctuality score with color coding
                    double punctScore = operator.getPunctualityScore();
                    String punctClass = punctScore >= 85 ? "good" : (punctScore >= 75 ? "warning" : "poor");
                    out.println("<td class='" + punctClass + "'>" + String.format("%.1f", punctScore) + "</td>");
                    
                    out.println("</tr>");
                }
                out.println("</table>");
                
                // Add summary statistics
                out.println("<div style='margin-top: 20px;'>");
                out.println("<h3>Summary Statistics:</h3>");
                out.println("<p>Average On-time Performance Across All Operators: " + 
                           String.format("%.1f%%", averageOntimePerformance) + "</p>");
                out.println("</div>");
            } else if (operatorsList != null && operatorsList.isEmpty()) {
                out.println("<p>No operator performance data found matching the criteria.</p>");
            }
            
            out.println("</body>");
            out.println("</html>");
        } finally {
            out.close();
        }
    }
        
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Operator Performance Dashboard Servlet";
    }
    
    // Implementation of OperatorPerformanceView interface methods
    
    @Override
    public void displayOperatorPerformance(List<OperatorPerformanceDTO> operators) {
        this.operatorsList = operators;
    }
    
    @Override
    public void displayErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
    
    @Override
    public void displaySuccessMessage(String message) {
        this.successMessage = message;
    }
    
    @Override
    public void displaySummaryStatistics(double avgOntimePerformance) {
        this.averageOntimePerformance = avgOntimePerformance;
    }
    
    @Override
    public String getSortMetric() {
        return request.getParameter("sort");
    }
    
    @Override
    public boolean getSortOrder() {
        return request.getParameter("order") == null || "asc".equals(request.getParameter("order"));
    }
    
    @Override
    public String getFilterType() {
        String filterType = request.getParameter("filterType");
        return filterType == null ? "all" : filterType;
    }
    
    @Override
    public Double getThresholdValue() {
        String thresholdParam = request.getParameter("threshold");
        if (thresholdParam != null && !thresholdParam.isEmpty()) {
            try {
                return Double.parseDouble(thresholdParam);
            } catch (NumberFormatException e) {
                this.errorMessage = "Invalid threshold value. Please enter a valid number.";
                return null;
            }
        }
        return null;
    }
    
    @Override
    public void initializeView() {
        // Clear previous state
        this.errorMessage = "";
        this.successMessage = "";
    }
    
    @Override
    public void showLoading() {
        this.isLoading = true;
    }
    
    @Override
    public void hideLoading() {
        this.isLoading = false;
    }
    
    @Override
    public void setPresenter(OperatorPerformancePresenter presenter) {
        this.presenter = presenter;
    }
}
