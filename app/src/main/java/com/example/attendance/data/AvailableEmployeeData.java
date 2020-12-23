package com.example.attendance.data;

public class AvailableEmployeeData {
    public AvailableEmployeeData() {
    }

    private String Name;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public AvailableEmployeeData(String availableEmployeeName) {
        this.Name = availableEmployeeName;
    }
}
