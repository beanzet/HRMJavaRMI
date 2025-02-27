package hrm;
import java.io.Serializable;

public class LeaveRecord implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private int leaveId;
    private int employeeId;
    private String startDate;
    private String endDate;
    private String status;
    
    // Default constructor
    public LeaveRecord() {
    }
    
    // Constructor with all fields
    public LeaveRecord(int leaveId, int employeeId, String startDate, String endDate, String status) {
        this.leaveId = leaveId;
        this.employeeId = employeeId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
    }
    
    // Getters and setters
    public int getLeaveId() {
        return leaveId;
    }
    
    public void setLeaveId(int leaveId) {
        this.leaveId = leaveId;
    }
    
    public int getEmployeeId() {
        return employeeId;
    }
    
    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }
    
    public String getStartDate() {
        return startDate;
    }
    
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }
    
    public String getEndDate() {
        return endDate;
    }
    
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    @Override
    public String toString() {
        return "LeaveRecord{" +
                "leaveId=" + leaveId +
                ", employeeId=" + employeeId +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}