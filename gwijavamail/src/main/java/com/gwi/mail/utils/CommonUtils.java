package com.gwi.mail.utils;

import com.gwi.mail.constant.GwiConfigs;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Administrator on 2016-10-27.
 */
public class CommonUtils {
    public static boolean isEmpty(String str) {
        return null == str || "".equals(str);
    }

    /**
     * 解析 yyyy-MM-dd HH:mm:ss
     *
     * @param clock
     * @return
     */
    public static Date getParseDate(String clock) {
        Date time = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat(GwiConfigs.DEFAULT_DATE_FORMAT, Locale.CHINA);
        SimpleDateFormat timeFormat = new SimpleDateFormat(GwiConfigs.DATE_FORMAT_TIME, Locale.CHINA);
        try {
            Date date = dateFormat.parse(clock);
            time = timeFormat.parse(timeFormat.format(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }

    /**
     * 解析 HH:mm:ss
     *
     * @param clock
     * @return
     */
    public static Date getParseTime(String clock) {
        Date time = null;
        SimpleDateFormat timeFormat = new SimpleDateFormat(GwiConfigs.DATE_FORMAT_TIME, Locale.CHINA);
        try {
            time = timeFormat.parse(clock);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }

    /**
     * 解析 HH:mm
     *
     * @param clock
     * @return
     */
    public static Date getParseHHMM(String clock) {
        Date time = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat(GwiConfigs.DATE_FORMAT_HHMM, Locale.CHINA);
        SimpleDateFormat timeFormat = new SimpleDateFormat(GwiConfigs.DATE_FORMAT_TIME, Locale.CHINA);
        try {
            Date date = dateFormat.parse(clock);
            time = timeFormat.parse(timeFormat.format(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }

    /**
     * 解析 HH:mm
     *
     * @param clock
     * @return
     */
    public static String getFormatHHMM(String clock) {
        String time = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat(GwiConfigs.DATE_FORMAT_HHMM, Locale.CHINA);
        SimpleDateFormat timeFormat = new SimpleDateFormat(GwiConfigs.DATE_FORMAT_TIME, Locale.CHINA);
        try {
            Date date = dateFormat.parse(clock);
            time = timeFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }

    /**
     * 解析时间
     *
     * @param clock
     * @param format
     * @return
     */
    public static Date getParseTime(String clock, String format) {
        Date time = null;
        SimpleDateFormat timeFormat = new SimpleDateFormat(format, Locale.CHINA);
        try {
            time = timeFormat.parse(clock);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }
}
