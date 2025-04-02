/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TransferObjects;

import java.sql.Timestamp;

/**
 *
 * @author johnt
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
