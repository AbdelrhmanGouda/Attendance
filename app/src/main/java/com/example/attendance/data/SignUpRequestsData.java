package com.example.attendance.data;

public class SignUpRequestsData {
    private String  empName,empEmail,empDept;
    private boolean isExpended;

    public SignUpRequestsData(String empName, String empEmail, String empDept) {
        this.empName = empName;
        this.empEmail = empEmail;
        this.empDept = empDept;
        this.isExpended =false;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getEmpEmail() {
        return empEmail;
    }

    public void setEmpEmail(String empEmail) {
        this.empEmail = empEmail;
    }

    public String getEmpDept() {
        return empDept;
    }

    public void setEmpDept(String empDept) {
        this.empDept = empDept;
    }

    public boolean isExpended() {
        return isExpended;
    }

    public void setExpended(boolean expended) {
        isExpended = expended;
    }
}
