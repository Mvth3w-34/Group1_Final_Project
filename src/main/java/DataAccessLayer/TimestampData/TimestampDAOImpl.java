/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DataAccessLayer.TimestampData;

import DataAccessLayer.TransitDataSource;
import TransferObjects.TimeStamp;
import java.sql.*;

/**
 * The implementation of the Timestamp DAO methods
 * @author johnt
 */
public class TimestampDAOImpl implements TimestampDAO {
    
    private final TransitDataSource instance;
    /**
     * Gets the data source instance
     * @throws SQLException 
     */
    public TimestampDAOImpl() throws SQLException {
        instance = TransitDataSource.getDataInstance();
        // Test to see if login is successful upon login
        instance.getConnection();
    }
    /**
     * Add a new operator timestamp to the DB
     * @param timestamp The timestamp to log
     * @throws SQLException 
     */
    @Override
    public void addTimestamp(TimeStamp timestamp) throws SQLException {
        String addTimestampQuery = "INSERT INTO OPERATOR_TIMESTAMP (PUNCH_TIME_START, PUNCH_TIME_STOP, PUNCH_TYPE, OPERATOR_ID) "
                + "VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = instance.getConnection().prepareStatement(addTimestampQuery, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
            statement.setTimestamp(1, timestamp.getStartTime());
            statement.setTimestamp(2, timestamp.getEndTime());
            statement.setString(3, timestamp.getTimestampType());
            statement.setInt(4, timestamp.getOperatorId());
            statement.executeUpdate();
        }
    }
    
}
