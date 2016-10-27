package com.gwi.mail.parse;

import java.io.File;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

/**
 * Created by Administrator on 2016-10-26.
 */
public class ParseXlsManager {
    private static ParseXlsManager ourInstance = new ParseXlsManager();

    public static ParseXlsManager getInstance() {
        return ourInstance;
    }

    private ParseXlsManager() {
    }

    public static void main(String args[]) {
        try {
            Workbook book = Workbook.getWorkbook(new File("C:/Users/Administrator/Desktop/kaoqin/002.xls"));
            //  获得第一个工作表对象
            Sheet sheet = book.getSheet(0);
            //  得到第一列第一行的单元格
            Cell cell1 = sheet.getCell(0, 0);
            String result = cell1.getContents();
            System.out.println(result);
            book.close();
        } catch (Exception e) {
            //System.out.println(e);
            e.printStackTrace();
        }
    }
}
