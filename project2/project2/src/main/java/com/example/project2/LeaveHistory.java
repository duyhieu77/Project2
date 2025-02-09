package com.example.project2;

public class LeaveHistory {
    private int id;
    private int employeeId;
    private String leaveStart;
    private String leaveEnd;
    private String reason;

    public LeaveHistory(int id, int employeeId, String leaveStart, String leaveEnd, String reason) {
        this.id = id;
        this.employeeId = employeeId;
        this.leaveStart = leaveStart;
        this.leaveEnd = leaveEnd;
        this.reason = reason;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getLeaveStart() {
        return leaveStart;
    }

    public void setLeaveStart(String leaveStart) {
        this.leaveStart = leaveStart;
    }

    public String getLeaveEnd() {
        return leaveEnd;
    }

    public void setLeaveEnd(String leaveEnd) {
        this.leaveEnd = leaveEnd;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}