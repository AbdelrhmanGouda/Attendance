package com.example.attendance.data;

public class LeaveRequestsData {
    private String leaveEmpName,leaveEmpDept,leaveEmpTime,leaveEmpReason;
    private boolean expended;
    public LeaveRequestsData(String leaveEmpName, String leaveEmpDept, String leaveEmpTime, String leaveEmpReason) {
        this.leaveEmpName = leaveEmpName;
        this.leaveEmpDept = leaveEmpDept;
        this.leaveEmpTime = leaveEmpTime;
        this.leaveEmpReason = leaveEmpReason;
        this.expended =false;
    }

    public boolean isExpended() {
        return expended;
    }

    public void setExpended(boolean expended) {
        this.expended = expended;
    }

    public String getLeaveEmpName() {
        return leaveEmpName;
    }

    public void setLeaveEmpName(String leaveEmpName) {
        this.leaveEmpName = leaveEmpName;
    }

    public String getLeaveEmpDept() {
        return leaveEmpDept;
    }

    public void setLeaveEmpDept(String leaveEmpDept) {
        this.leaveEmpDept = leaveEmpDept;
    }

    public String getLeaveEmpTime() {
        return leaveEmpTime;
    }

    public void setLeaveEmpTime(String leaveEmpTime) {
        this.leaveEmpTime = leaveEmpTime;
    }

    public String getLeaveEmpReason() {
        return leaveEmpReason;
    }

    public void setLeaveEmpReason(String leaveEmpReason) {
        this.leaveEmpReason = leaveEmpReason;
    }
}
