package com.gwi.mail.entity;

/**
 * Created by Administrator on 2016-10-25.
 */
public class AttendanceEntity {
    // 考勤号码
    private String jobNumber;
    // 日期
    private String date;
    // 签到时间
    private String signInTime;
    // 签退时间
    private String returnTime;
    // 打卡时间
    private String clock;

    public String getClock() {
        return clock;
    }

    public void setClock(String clock) {
        this.clock = clock;
    }

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
}
