package com.gwi.mail.parse;

import com.gwi.mail.constant.GwiConfigs;
import com.gwi.mail.entity.ExcelEntity;
import com.gwi.mail.utils.CommonUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by Administrator on 2016-10-27.
 */
public class Parse {
    /**
     * 合成的发送邮件的对象<邮箱账号，邮件内容>
     */
    protected HashMap<String, String> mHashMap;

    public Parse() {
        mHashMap = new HashMap<>();
    }

    public void creatEntity(ExcelEntity entity, String columnLabel, String outPut) {
        switch (columnLabel) {
            case GwiConfigs.ColumnLabel.考勤号码:
                entity.setJobNumber(outPut);
                break;
            case GwiConfigs.ColumnLabel.日期:
                entity.setDate(outPut);
                break;
            case GwiConfigs.ColumnLabel.签到时间:
                entity.setSignInTime(outPut);
                break;
            case GwiConfigs.ColumnLabel.签退时间:
                entity.setReturnTime(outPut);
                break;
            default:
                break;
        }
    }

    public boolean filterColumns(String outPut) {
        return GwiConfigs.ColumnLabel.考勤号码.equals(outPut)
                || GwiConfigs.ColumnLabel.日期.equals(outPut)
                || GwiConfigs.ColumnLabel.签到时间.equals(outPut)
                || GwiConfigs.ColumnLabel.签退时间.equals(outPut);
    }

    /**
     * 获取打卡异常员工工号
     *
     * @return
     */
    public void getAbnormalJobNomber(ArrayList<ExcelEntity> list) {
        mHashMap.clear();

        StringBuffer sb = new StringBuffer();
        for (ExcelEntity entity : list) {
            if (CommonUtils.isEmpty(entity.getSignInTime()) && CommonUtils.isEmpty(entity.getReturnTime())) {
                // 旷工
                sb = getContent(GwiConfigs.LABEL_ABSENTEEISM, entity.getJobNumber(), entity.getDate(), "");
                mHashMap.put(entity.getJobNumber(), sb.toString());
                sb.setLength(0);
            } else if (CommonUtils.isEmpty(entity.getSignInTime())) {
                // 上班未打卡
                sb = getContent(GwiConfigs.LABEL_ABSENTEEISM_MORNING, entity.getJobNumber(), entity.getDate(), entity.getReturnTime());
                mHashMap.put(entity.getJobNumber(), sb.toString());
                sb.setLength(0);
            } else if (CommonUtils.isEmpty(entity.getReturnTime())) {
                // 下班未打卡
                sb = getContent(GwiConfigs.LABEL_ABSENTEEISM_AFTERNOON, entity.getJobNumber(), entity.getDate(), entity.getSignInTime());
                mHashMap.put(entity.getJobNumber(), sb.toString());
                sb.setLength(0);
            } else {
                // 上午
                if (checkDate(entity.getSignInTime())) {
                    sb = getContent(GwiConfigs.LABEL_CLOCK_TIME, entity.getJobNumber(), entity.getDate(), entity.getSignInTime());
                    mHashMap.put(entity.getJobNumber(), sb.toString());
                    sb.setLength(0);
                }
                // 下午
                if (checkDate(entity.getReturnTime())) {
                    sb = getContent(GwiConfigs.LABEL_CLOCK_TIME, entity.getJobNumber(), entity.getDate(), entity.getReturnTime());
                    mHashMap.put(entity.getJobNumber(), sb.toString());
                    sb.setLength(0);
                }
            }
        }
    }

    public boolean checkDate(String time) {
        final Date MORNING = CommonUtils.getParseTime(GwiConfigs.WorkTime.MORNING);
        final Date AFTERNOON = CommonUtils.getParseTime(GwiConfigs.WorkTime.AFTERNOON);
        Date date = CommonUtils.getParseHHMM(time);
        return date.after(MORNING) && date.before(AFTERNOON);
    }

    public StringBuffer getContent(String tips, String jobNomber, String date, String time) {
        StringBuffer sb = new StringBuffer();
        String formatTime = CommonUtils.isEmpty(time) ? "" : CommonUtils.getFormatHHMM(time);
        if (mHashMap.containsKey(jobNomber)) {
            String out = mHashMap.get(jobNomber);
            sb.append(out).append(tips).append(date).append(" ").append(formatTime).append("<br>");
            mHashMap.remove(jobNomber);
        } else {
            sb.append(tips).append(date).append(" ").append(formatTime).append("<br>");
        }
        return sb;
    }
}
