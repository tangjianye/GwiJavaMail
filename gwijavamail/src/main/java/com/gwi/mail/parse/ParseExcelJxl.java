package com.gwi.mail.parse;

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

    @Override
    public HashMap<String, String> parse(String filePath) {
        readExcelFile(filePath);
        return mHashMap;
    }
}
