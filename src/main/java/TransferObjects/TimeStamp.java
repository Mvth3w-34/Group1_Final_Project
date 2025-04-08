/* filename: TimeStamp.java
 * date: Apr. 6th, 2025
 * authors: John Tieu
 * course: CST8288 O.O.P. with Design Patterns - Lab Section 023 
 * professor: Samira Ouaaz
 * coursework: Final Project - Public Transit Management System
 */
package TransferObjects;

import java.sql.Timestamp;

/**
 *
 * @author John Tieu
 * @version 1.0
 * @since 21
 */
public class TimeStamp {
    private int timestampId;
    private int operatorId;
    private Timestamp startTime;
    private Timestamp endTime;
    private String timestampType;
    
    public void setTimestampId(int id) {
        this.timestampId = id;
    }
    public void setOperatorId(int opId) {
        this.operatorId = opId;
    }
    public void setStartTime(Timestamp start) {
        this.startTime = start;
    }
    public void setEndTime(Timestamp end) {
        this.endTime = end;
    }
    public void setTimestampType(String type) {
        this.timestampType = type;
    }
    
    public int getTimestampId() {
        return this.timestampId;
    }
    public int getOperatorId() {
        return this.operatorId;
    }
    public Timestamp getStartTime() {
        return this.startTime;
    }
    public Timestamp getEndTime() {
        return this.endTime;
    }
    public String getTimestampType() {
        return this.timestampType;
    }
}
