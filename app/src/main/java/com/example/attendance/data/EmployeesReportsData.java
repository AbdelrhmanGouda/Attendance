package com.example.attendance.data;

public class EmployeesReportsData {
    private String name;
    private String department;
    private String month;
    private String totalHours;
    private String totalMintues;
    private boolean expended;

    public EmployeesReportsData(String name, String department, String month, String totalHours ,String totalMintues ) {
        this.name = name;
        this.department = department;
        this.month = month;
        this.totalHours = totalHours;
        this.totalMintues=totalMintues;
        this.expended = false;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public EmployeesReportsData() {
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

    public String getTotalHours() {
        return totalHours;
    }

    public void setTotalHours(String totalHours) {
        this.totalHours = totalHours;
    }

    public boolean isExpended() {
        return expended;
    }

    public void setExpended(boolean expended) {
        this.expended = expended;
    }
    public String getTotalMintues() {
        return totalMintues;
    }

    public void setTotalMintues(String totalMintues) {
        this.totalMintues = totalMintues;
    }
}
