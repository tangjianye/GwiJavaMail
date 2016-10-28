package com.gwi.mail.parse;

import com.gwi.mail.entity.ExcelEntity;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

public class ParseExcelPoi  extends Parse implements IParse {

    public ParseExcelPoi() {
        super();
    }

    private void readExcelFile(String filePath) {
        try {
            ArrayList<ExcelEntity> list = new ArrayList<>();

            Workbook book = createWorkBook(filePath);
            if (book == null) {
                System.out.println("Excel Error!");
                return;
            }
            // 只读取第一页
            Sheet sheet = book.getSheetAt(0);
            int rowCount = sheet.getPhysicalNumberOfRows(); // 获取总行数

            // 解析
            for (int r = 1; r < rowCount; r++) {
                ExcelEntity entity = new ExcelEntity();
                Row row = sheet.getRow(r);
                int columnCount = row.getPhysicalNumberOfCells(); // 获取总列数
                for (int column = 0; column < columnCount; column++) {
                    String cellContent = readExcelCell(row.getCell(column)).trim();
                    String columnLabel = readExcelCell(sheet.getRow(0).getCell(column)).trim();
                    if (filterColumns(columnLabel)) {
                        creatEntity(entity, columnLabel, cellContent);
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

    public static Workbook createWorkBook(String filePath) {
        File excelFile = new File(filePath); // 创建文件对象
        FileInputStream is = null;
        Workbook workbook = null;
        try {
            is = new FileInputStream(excelFile);
            workbook = WorkbookFactory.create(is);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (EncryptedDocumentException e) {
            e.printStackTrace();
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return workbook;
    }

    public static String readExcelCell(Cell cell) {
        int cellType = cell.getCellType();
        String cellValue = null;
        switch (cellType) {
            case Cell.CELL_TYPE_STRING: // 文本
                cellValue = cell.getStringCellValue();
                break;
            case Cell.CELL_TYPE_NUMERIC: // 数字、日期
                if (DateUtil.isCellDateFormatted(cell)) {
                    SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
                    cellValue = fmt.format(cell.getDateCellValue()); // 日期型
                } else {
                    cellValue = String.valueOf(cell.getNumericCellValue()); // 数字
                }
                break;
            case Cell.CELL_TYPE_BOOLEAN: // 布尔型
                cellValue = String.valueOf(cell.getBooleanCellValue());
                break;
            case Cell.CELL_TYPE_BLANK: // 空白
                cellValue = cell.getStringCellValue();
                break;
            case Cell.CELL_TYPE_ERROR: // 错误
                cellValue = "";
                break;
            case Cell.CELL_TYPE_FORMULA: // 公式
                cellValue = "";
                break;
            default:
                cellValue = "";
        }
        return cellValue;
    }

    @Override
    public HashMap<String, String> parse(String filePath) {
        readExcelFile(filePath);
        return mHashMap;
    }
}
