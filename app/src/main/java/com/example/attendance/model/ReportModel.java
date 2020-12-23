package com.example.attendance.model;

public class ReportModel {
    String name,department,type;
    int totalTime;

    public ReportModel() {
    }

    public ReportModel(String name, int totalTime, String department, String type) {
        this.name = name;
        this.totalTime = totalTime;
        this.department = department;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTime() {
        return totalTime;
    }

    public void setTime(int time) {
        this.totalTime = totalTime;
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
