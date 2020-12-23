package com.example.attendance.data;

public class SignUpRequestsData {
    private String name, email, department;
    private boolean isExpended;

    public SignUpRequestsData() {
    }

    public SignUpRequestsData(String name, String email, String department) {
        this.name = name;
        this.email = email;
        this.department = department;
        this.isExpended =false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public boolean isExpended() {
        return isExpended;
    }

    public void setExpended(boolean expended) {
        isExpended = expended;
    }
}
