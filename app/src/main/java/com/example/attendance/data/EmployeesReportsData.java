package com.example.attendance.data;

public class EmployeesReportsData {
    private String reportEmpName,reportEmpDept,reportEmpMonth,reportEmpAbsence;
    private boolean expended;

    public EmployeesReportsData(String reportEmpName, String reportEmpDept, String reportEmpMonth, String reportEmpAbsence) {
        this.reportEmpName = reportEmpName;
        this.reportEmpDept = reportEmpDept;
        this.reportEmpMonth = reportEmpMonth;
        this.reportEmpAbsence = reportEmpAbsence;
        this.expended = false;
    }

    public String getReportEmpMonth() {
        return reportEmpMonth;
    }

    public void setReportEmpMonth(String reportEmpMonth) {
        this.reportEmpMonth = reportEmpMonth;
    }

    public String getReportEmpName() {
        return reportEmpName;
    }

    public void setReportEmpName(String reportEmpName) {
        this.reportEmpName = reportEmpName;
    }

    public String getReportEmpDept() {
        return reportEmpDept;
    }

    public void setReportEmpDept(String reportEmpDept) {
        this.reportEmpDept = reportEmpDept;
    }

    public String getReportEmpAbsence() {
        return reportEmpAbsence;
    }

    public void setReportEmpAbsence(String reportEmpAbsence) {
        this.reportEmpAbsence = reportEmpAbsence;
    }

    public boolean isExpended() {
        return expended;
    }

    public void setExpended(boolean expended) {
        this.expended = expended;
    }
}
