package com.gwi.mail.entity;

/**
 * Created by Administrator on 2016-10-25.
 */
public class ExcelEntity {
    // 考勤号码
    private String jobNumber;
    // 日期
    private String date;
    // 签到时间
    private String signInTime;
    // 签退时间
    private String returnTime;

    public String getJobNumber() {
        return jobNumber;
    }

    public void setJobNumber(String jobNumber) {
        this.jobNumber = jobNumber;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getReturnTime() {
        return returnTime;
    }

    public void setReturnTime(String returnTime) {
        this.returnTime = returnTime;
    }

    public String getSignInTime() {
        return signInTime;
    }

    public void setSignInTime(String signInTime) {
        this.signInTime = signInTime;
    }

    @Override
    public String toString() {
        return "ExcelEntity{" +
                "date='" + date + '\'' +
                ", jobNumber='" + jobNumber + '\'' +
                ", signInTime='" + signInTime + '\'' +
                ", returnTime='" + returnTime + '\'' +
                '}';
    }
}
