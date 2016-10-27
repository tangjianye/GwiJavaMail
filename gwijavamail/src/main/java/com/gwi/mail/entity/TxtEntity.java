package com.gwi.mail.entity;

/**
 * Created by Administrator on 2016-10-25.
 */
public class TxtEntity {
    // 考勤号码
    private String jobNumber;
    // 打卡时间
    private String date;

    public String getDate() {
        return date;
    }

    public void setDate(String clock) {
        this.date = clock;
    }

    public String getJobNumber() {
        return jobNumber;
    }

    public void setJobNumber(String jobNumber) {
        this.jobNumber = jobNumber;
    }
}
