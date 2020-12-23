package com.example.attendance.data;

public class LeaveRequestsData {
    private String name, department, timeHours, reason ,timeMinutes;
    private boolean expended;
    public LeaveRequestsData(String name, String department, String timeHours, String reason ,String timeMinutes) {
        this.name = name;
        this.department = department;
        this.timeHours = timeHours;
        this.reason = reason;
        this.timeMinutes=timeMinutes;
        this.expended =false;
    }

    public LeaveRequestsData() {
    }

    public boolean isExpended() {
        return expended;
    }

    public void setExpended(boolean expended) {
        this.expended = expended;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getTimeHours() {
        return timeHours;
    }

    public void setTimeHours(String timeHours) {
        this.timeHours = timeHours;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getTimeMinutes() {
        return timeMinutes;
    }

    public void setTimeMinutes(String timeMinutes) {
        this.timeMinutes = timeMinutes;
    }
}
