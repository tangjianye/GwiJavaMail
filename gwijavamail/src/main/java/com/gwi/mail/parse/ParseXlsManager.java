package com.gwi.mail.parse;

import java.io.File;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;


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
            //  打开文件
            WritableWorkbook book = Workbook.createWorkbook(new File("C:/Users/Administrator/Desktop/kaoqin/test.xls"));
            //  生成名为“第一页”的工作表，参数0表示这是第一页
            WritableSheet sheet = book.createSheet(" 第一页 ", 0);
            //  在Label对象的构造子中指名单元格位置是第一列第一行(0,0)
            //  以及单元格内容为test
            Label label = new Label(0, 0, "test");
            //  将定义好的单元格添加到工作表中
            sheet.addCell(label);
            //  写入数据并关闭文件
            book.write();
            book.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
