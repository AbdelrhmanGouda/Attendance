package com.example.attendance.data;

public class EmployeesListData {
    private String name,email;

    public EmployeesListData(String employeeName) {
        name = employeeName;
    }

    public EmployeesListData() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public EmployeesListData(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}
