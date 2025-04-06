/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DataAccessLayer.EnergyFuel;

import DataAccessLayer.TransitDataSource;
import TransferObjects.EnergyFuelDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EnergyFuelDAOImpl implements EnergyFuelDAO {
    private final Connection conn;

    public EnergyFuelDAOImpl() throws SQLException {
        conn = TransitDataSource.getDataInstance().getConnection();  

    }

    @Override
    public void logConsumption(EnergyFuelDTO record) throws SQLException {
        String query = "INSERT INTO EnergyFuelLog (vehicle_id, log_date, fuel_consumed, energy_consumed, fuel_threshold, energy_threshold) " +
                       "VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, record.getVehicleId());
        stmt.setDate(2, new java.sql.Date(record.getLogDate().getTime()));
        stmt.setFloat(3, record.getFuelConsumed());
        stmt.setFloat(4, record.getEnergyConsumed());
        stmt.setFloat(5, record.getFuelThreshold());
        stmt.setFloat(6, record.getEnergyThreshold());
        stmt.executeUpdate();
        stmt.close();
    }

    @Override
    public List<EnergyFuelDTO> getAllLogs() throws SQLException {
        List<EnergyFuelDTO> logs = new ArrayList<>();
        String query = "SELECT * FROM EnergyFuelLog";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);

        while (rs.next()) {
            EnergyFuelDTO log = mapResultSetToDTO(rs);
            logs.add(log);
        }
        rs.close();
        stmt.close();
        return logs;
    }

    @Override
    public List<EnergyFuelDTO> getLogsByVehicle(String vehicleId) throws SQLException {
        List<EnergyFuelDTO> logs = new ArrayList<>();
        String query = "SELECT * FROM EnergyFuelLog WHERE vehicle_id = ?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, vehicleId);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            EnergyFuelDTO log = mapResultSetToDTO(rs);
            logs.add(log);
        }
        rs.close();
        stmt.close();
        return logs;
    }

    @Override
    public List<EnergyFuelDTO> getOverThresholdLogs() throws SQLException {
        List<EnergyFuelDTO> logs = new ArrayList<>();
        String query = "SELECT * FROM EnergyFuelLog WHERE fuel_consumed > fuel_threshold OR energy_consumed > energy_threshold";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);

        while (rs.next()) {
            EnergyFuelDTO log = mapResultSetToDTO(rs);
            logs.add(log);
        }
        rs.close();
        stmt.close();
        return logs;
    }

    private EnergyFuelDTO mapResultSetToDTO(ResultSet rs) throws SQLException {
        EnergyFuelDTO dto = new EnergyFuelDTO();
        dto.setLogId(rs.getInt("log_id"));
        dto.setVehicleId(rs.getString("vehicle_id"));
        dto.setLogDate(rs.getDate("log_date"));
        dto.setFuelConsumed(rs.getFloat("fuel_consumed"));
        dto.setEnergyConsumed(rs.getFloat("energy_consumed"));
        dto.setFuelThreshold(rs.getFloat("fuel_threshold"));
        dto.setEnergyThreshold(rs.getFloat("energy_threshold"));
        return dto;
    }
}
