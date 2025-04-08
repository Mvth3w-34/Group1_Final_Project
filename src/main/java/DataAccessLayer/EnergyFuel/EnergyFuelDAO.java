/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DataAccessLayer.EnergyFuel;

/**
 *
 * @author mario
 */
import TransferObjects.EnergyFuelDTO;
import java.sql.SQLException;
import java.util.List;

public interface EnergyFuelDAO {
    void logConsumption(EnergyFuelDTO record) throws SQLException;

    List<EnergyFuelDTO> getAllLogs() throws SQLException;

    List<EnergyFuelDTO> getLogsByVehicle(String vehicleId) throws SQLException;

    List<EnergyFuelDTO> getOverThresholdLogs() throws SQLException;
}
