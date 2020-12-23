package com.example.attendance.model;

public class LeaveRequestModel {
    String name,timeHours,timeMinutes,months,department, reason;


    public LeaveRequestModel() {
    }

    public LeaveRequestModel(String name, String timeHours, String timeMinutes, String months,String reason) {
        this.name = name;
        this.timeHours = timeHours;
        this.timeMinutes = timeMinutes;
        this.months = months;
        this.reason=reason;
    }

    public LeaveRequestModel(String name, String timeHours, String timeMinutes, String months, String department,String reason) {
        this.name = name;
        this.timeHours = timeHours;
        this.timeMinutes = timeMinutes;
        this.months = months;
        this.department = department;
        this.reason=reason;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTimeHours() {
        return timeHours;
    }

    public void setTimeHours(String timeHours) {
        this.timeHours = timeHours;
    }

    public String getTimeMinutes() {
        return timeMinutes;
    }

    public void setTimeMinutes(String timeMinutes) {
        this.timeMinutes = timeMinutes;
    }

    public String getMonths() {
        return months;
    }

    public void setMonths(String months) {
        this.months = months;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}
