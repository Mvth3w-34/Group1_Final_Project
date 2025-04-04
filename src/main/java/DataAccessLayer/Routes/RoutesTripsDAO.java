/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package DataAccessLayer.Routes;

import DataAccessLayer.TransitDaoInterface;
import TransferObjects.*;
import java.sql.SQLException;
import java.util.*;

/**
 *
 * @author johnt
 */
public interface RoutesTripsDAO extends TransitDaoInterface {
    public List<VehicleStationTimetable> getAllVehicleStationTimes(int vehicleID) throws SQLException;
    public List<String> getHeaders() throws SQLException;
    public List<Integer> getRoutes() throws SQLException;
//    public List<RouteTripDTO> getRouteTrips() throws SQLException;
}
