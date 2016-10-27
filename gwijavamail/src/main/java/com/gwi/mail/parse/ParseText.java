package com.gwi.mail.parse;

import com.gwi.mail.constant.GwiConfigs;
import com.gwi.mail.entity.AttendanceEntity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

/**
 * Created by Administrator on 2016-10-25.
 */
public class ParseText extends Parse implements IParse {
    private static final String ENCODING = "GBK";

    public ParseText() {
        super();
    }

    private Date getParseDate(String clock) {
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

    private Date getParseTime(String clock) {
        Date time = null;
        SimpleDateFormat timeFormat = new SimpleDateFormat(GwiConfigs.DATE_FORMAT_TIME, Locale.CHINA);
        try {
            time = timeFormat.parse(clock);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }

    private void readTxtFile(String filePath) {
        mParseList.clear();
        try {
            File file = new File(filePath);
            if (file.isFile() && file.exists()) {
                InputStreamReader read = new InputStreamReader(new FileInputStream(file), ENCODING);
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                while ((lineTxt = bufferedReader.readLine()) != null) {
                    System.out.println(lineTxt);
                    AttendanceEntity bean = parseStr(lineTxt);
                    if (null != bean) {
                        mParseList.add(bean);
                    }
                }
                read.close();
            } else {
                System.out.println("找不到指定的文件");
            }
        } catch (Exception e) {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
        }
    }

    private AttendanceEntity parseStr(String str) {
        if (null == str) {
            return null;
        }
        AttendanceEntity bean = new AttendanceEntity();
        String strr = str.trim();
        String[] abc = strr.split("\\t");
        bean.setJobNumber(abc[0]);
        bean.setClock(abc[1]);
        System.out.println(bean.getJobNumber());
        System.out.println(bean.getClock());
        return bean;
    }

    /**
     * 获取打卡异常员工工号
     *
     * @return
     */
    public HashMap<String, String> getAbnormalJobNomber() {
        mHashMap.clear();
        final Date MORNING = getParseTime(GwiConfigs.WorkTime.MORNING);
        final Date AFTERNOON = getParseTime(GwiConfigs.WorkTime.AFTERNOON);

        for (AttendanceEntity entity : mParseList) {
            Date date = getParseDate(entity.getClock());

            // 打卡异常的员工
            if (date.after(MORNING) && date.before(AFTERNOON)) {
                // 有过打卡异常
                StringBuffer sb = new StringBuffer();
                sb.append(entity.getClock());
                if (mHashMap.containsKey(entity.getJobNumber())) {
                    String clock = mHashMap.get(entity.getJobNumber());
                    sb.append("<br>").append(clock).append("<br>").append(entity.getClock());
                }
                mHashMap.put(entity.getJobNumber(), sb.toString());
            }
        }
        return mHashMap;
    }

    @Override
    public HashMap<String, String> parse(String filePath) {
        // 解析原始的打卡数据
        readTxtFile(filePath);
        // 解析打卡异常的工号
        return getAbnormalJobNomber();
    }
}
