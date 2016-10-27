package com.gwi.mail.parse;

import com.gwi.mail.constant.GwiConfigs;
import com.gwi.mail.entity.ExcelEntity;

import java.io.File;
import java.util.HashMap;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

/**
 * Created by Administrator on 2016-10-26.
 */
public class ParseJxlExcel extends Parse<ExcelEntity> implements IParse {

    public ParseJxlExcel() {
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
                    String outPut = cell.getContents();
                    String columnLabel = sheet.getCell(column, 0).getContents();
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

    @Override
    public HashMap<String, String> parse(String filePath) {
        readExcelFile(filePath);
        return mHashMap;
    }
}
