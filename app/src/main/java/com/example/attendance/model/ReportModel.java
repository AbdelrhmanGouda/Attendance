package com.example.attendance.model;

public class ReportModel {
    String name,department,type,month;
    int totalHours,totalMintues;

    public ReportModel() {
    }

    public ReportModel(String name, String department, String type, String month, int totalHours, int totalMintues) {
        this.name = name;
        this.department = department;
        this.type = type;
        this.month = month;
        this.totalHours = totalHours;
        this.totalMintues = totalMintues;
    }

    public int getTotalMintues() {
        return totalMintues;
    }

    public void setTotalMintues(int totalMintues) {
        this.totalMintues = totalMintues;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public int getTotalHours() {
        return totalHours;
    }

    public void setTotalHours(int totalHours) {
        this.totalHours = totalHours;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTime() {
        return totalHours;
    }

    public void setTime(int time) {
        this.totalHours = totalHours;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
