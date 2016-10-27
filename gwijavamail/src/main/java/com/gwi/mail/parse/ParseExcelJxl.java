package com.gwi.mail.parse;

import com.gwi.mail.constant.GwiConfigs;
import com.gwi.mail.entity.ExcelEntity;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

/**
 * Created by Administrator on 2016-10-26.
 */
public class ParseExcelJxl extends Parse<ExcelEntity> implements IParse {

    public ParseExcelJxl() {
        super();
    }

    private void readExcelFile(String filePath) {
        try {
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
                mParseList.add(entity);
            }

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
        for (ExcelEntity entity : list) {
            if (entity.getSignInTime() != null && entity.getReturnTime() != null) {

            } else if (entity.getSignInTime() == null) {
                // 上班未打卡
                StringBuffer sb = new StringBuffer();
                sb.append(GwiConfigs.LABEL_CLOCK_TIME).append("<br>")
                        .append(entity.getDate()).append(" ").append(entity.getReturnTime()).append("<br>");
                if (mHashMap.containsKey(entity.getJobNumber())) {
                    String out = mHashMap.get(entity.getJobNumber());
                    sb.append(out).append("<br>")
                            .append(entity.getDate()).append(" ").append(entity.getReturnTime()).append("<br>");
                }
                mHashMap.put(entity.getJobNumber(), sb.toString());
            } else if (entity.getReturnTime() == null) {
                // 下班未打卡
                StringBuffer sb = new StringBuffer();
                sb.append(GwiConfigs.LABEL_CLOCK_TIME).append("<br>")
                        .append(entity.getDate()).append(" ").append(entity.getSignInTime()).append("<br>");
                if (mHashMap.containsKey(entity.getJobNumber())) {
                    String out = mHashMap.get(entity.getJobNumber());
                    sb.append(out).append("<br>")
                            .append(entity.getDate()).append(" ").append(entity.getSignInTime()).append("<br>");
                }
                mHashMap.put(entity.getJobNumber(), sb.toString());
            } else {
                // 旷工
                StringBuffer sb = new StringBuffer();
                sb.append(GwiConfigs.LABEL_ABSENTEEISM).append("<br>").append(entity.getDate()).append("<br>");
                if (mHashMap.containsKey(entity.getJobNumber())) {
                    String out = mHashMap.get(entity.getJobNumber());
                    sb.append(out).append("<br>").append(entity.getDate()).append("<br>");
                }
                mHashMap.put(entity.getJobNumber(), sb.toString());
            }
        }
    }

    @Override
    public HashMap<String, String> parse(String filePath) {
        readExcelFile(filePath);
        return mHashMap;
    }
}
