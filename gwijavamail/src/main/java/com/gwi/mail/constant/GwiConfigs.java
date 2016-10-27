package com.gwi.mail.constant;

/**
 * Created by Administrator on 2016-10-25.
 */
public class GwiConfigs {
    public static final String LABEL_DOCUMENT = "选择考勤文档（.txt）";
    public static final String LABEL_CHOICE = "选择";
    public static final String LABEL_TITLE = "完成";
    public static final String LABEL_MSG = "发出%s封邮件";

    public static final String MAIL_FROM = "GWI Abnormal attendance";
    public static final String MAIL_SUBJECT = "考勤异常";
    public static final String MAIL_CONTENT = "考勤异常时间列表: ";

    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_FORMAT_DAY = "yyyy-MM-dd";
    public static final String DATE_FORMAT_TIME = "HH:mm:ss";

    public static class Mail {
        // 发件人的 邮箱 和 密码（替换为自己的邮箱和密码）
        // public static final String SEND_EMAIL_ACCOUNT = "ccylyfb@gwi.com.cn";
        // public static final String SEND_EMAIL_PASSWORD = "ccylyfb0048";
        public static final String SEND_EMAIL_ACCOUNT = "1110825@gwi.com.cn";
        public static final String SEND_EMAIL_PASSWORD = "password";

        // 发件人邮箱的 SMTP 服务器地址, 必须准确, 不同邮件服务器地址不同, 一般格式为: smtp.xxx.com
        // 网易163邮箱的 SMTP 服务器地址为: smtp.163.com
        public static final String SEND_EMAIL_SMTPHOST = "mail.gwi.com.cn";

        // 收件人邮箱后缀
        public static final String RECEIVE_EMAIL_SUFFIX = "@gwi.com.cn";
    }

    public static class WorkTime {
        public static final String MORNING = "08:35:59";
        public static final String AFTERNOON = "16:55:00";
    }

    /**
     * 1）旷工：签到时间 && 签退时间 都为空
     * 2）迟到|早退：
     * a.签到时间或签退时间有一个为空
     * b.签到时间和签退时间不为空，但是在8:30到17:00之间
     */
    public static class CellLabel {
        public static final String 考勤号码 = "考勤号码";
        public static final String 日期 = "日期";
        public static final String 签到时间 = "签到时间";
        public static final String 签退时间 = "签退时间";
    }
}
