package com.gwi.mail.parse;

import com.gwi.mail.constant.GwiConfigs;
import com.gwi.mail.entity.ExcelEntity;
import com.gwi.mail.utils.CommonUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

/**
 * Created by Administrator on 2016-10-26.
 */
public class ParseExcelJxl extends Parse implements IParse {

    public ParseExcelJxl() {
        super();
    }

    private void readExcelFile(String filePath) {
        try {
            ArrayList<ExcelEntity> list = new ArrayList<>();

            Workbook book = Workbook.getWorkbook(new File(filePath));
            //  获得第一个工作表对象
            Sheet sheet = book.getSheet(0);

            // 解析
            for (int row = 1; row < sheet.getRows(); row++) {
                ExcelEntity entity = new ExcelEntity();
                for (int column = 0; column < sheet.getColumns(); column++) {
                    Cell cell = sheet.getCell(column, row);
                    String outPut = cell.getContents().trim();
                    String columnLabel = sheet.getCell(column, 0).getContents().trim();
                    if (filterColumns(columnLabel) && 0 != row) {
                        creatEntity(entity, columnLabel, outPut);
                    }
                }
                System.out.println(entity.toString());
                list.add(entity);
            }

            // 获取打卡异常员工工号
            getAbnormalJobNomber(list);

            book.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void creatEntity(ExcelEntity entity, String columnLabel, String outPut) {
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

    private static boolean filterColumns(String outPut) {
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
    private void getAbnormalJobNomber(ArrayList<ExcelEntity> list) {
        mHashMap.clear();

        StringBuffer sb = new StringBuffer();
        for (ExcelEntity entity : list) {

            if (CommonUtils.isEmpty(entity.getSignInTime()) && CommonUtils.isEmpty(entity.getReturnTime())) {
                // 旷工
                sb = getContent(GwiConfigs.LABEL_ABSENTEEISM, entity.getJobNumber(), entity.getDate(), "");
                mHashMap.put(entity.getJobNumber(), sb.toString());
            } else if (CommonUtils.isEmpty(entity.getSignInTime())) {
                // 上班未打卡
                sb = getContent(GwiConfigs.LABEL_CLOCK_TIME, entity.getJobNumber(), entity.getDate(), entity.getReturnTime());
                mHashMap.put(entity.getJobNumber(), sb.toString());
            } else if (CommonUtils.isEmpty(entity.getReturnTime())) {
                // 下班未打卡
                sb = getContent(GwiConfigs.LABEL_CLOCK_TIME, entity.getJobNumber(), entity.getDate(), entity.getSignInTime());
                mHashMap.put(entity.getJobNumber(), sb.toString());
            } else {
                // 上午
                StringBuffer morning = checkDate(CommonUtils.getParseTime(entity.getSignInTime(), GwiConfigs.DATE_FORMAT_HHMM), entity);
                if (null != morning) {
                    sb.append(morning);
                }
                // 下午
                StringBuffer afternoon = checkDate(CommonUtils.getParseTime(entity.getReturnTime(), GwiConfigs.DATE_FORMAT_HHMM), entity);
                if (null != afternoon) {
                    sb.append(afternoon);
                }
                mHashMap.put(entity.getJobNumber(), sb.toString());
            }
        }
    }

    private StringBuffer checkDate(Date date, ExcelEntity entity) {
        StringBuffer sb = new StringBuffer();
        final Date MORNING = CommonUtils.getParseTime(GwiConfigs.WorkTime.MORNING);
        final Date AFTERNOON = CommonUtils.getParseTime(GwiConfigs.WorkTime.AFTERNOON);
        if (date.after(MORNING) && date.before(AFTERNOON)) {
            sb = getContent(GwiConfigs.LABEL_CLOCK_TIME, entity.getJobNumber(), entity.getDate(), entity.getSignInTime());
        }
        return sb;
    }

    private StringBuffer getContent(String tips, String jobNomber, String date, String time) {
        StringBuffer sb = new StringBuffer();
        sb.append(tips).append("<br>")
                .append(date).append(" ").append(time).append("<br>");
        if (mHashMap.containsKey(jobNomber)) {
            String out = mHashMap.get(jobNomber);
            sb.append(out).append("<br>")
                    .append(date).append(" ").append(time).append("<br>");
        }
        return sb;
    }

    @Override
    public HashMap<String, String> parse(String filePath) {
        readExcelFile(filePath);
        return mHashMap;
    }
}
