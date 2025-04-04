/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package DataAccessLayer.TimestampData;

import DataAccessLayer.TransitDaoInterface;
import TransferObjects.TimeStamp;
import java.sql.SQLException;

/**
 *
 * @author johnt
 */
public interface TimestampDAO extends TransitDaoInterface {
    public void addTimestamp(TimeStamp timestamp) throws SQLException;
}
