package com.cwb.platform.util.commonUtil;

import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
public class ExcelUtil {


    public static void createSheet(OutputStream outputStream, String sheetName, String[] heads, List<List<String>> data) {
        createSheet(outputStream, sheetName, 0, heads, data);
    }

    /**
     * 根据表名，表头，表数据 生成excel文件，输出到outputStream
     *
     * @param outputStream 输出流
     * @param sheetName    表名
     * @param sheetIndex   表索引，默认为0
     * @param heads        表头
     * @param data         表数据
     */
    public static void createSheet(OutputStream outputStream, String sheetName, int sheetIndex, String[] heads, List<List<String>> data) {
        try {
            WritableWorkbook workbook = jxl.Workbook.createWorkbook(outputStream);
            WritableSheet sheet = workbook.createSheet(sheetName, sheetIndex);
            for (int c = 0; c < heads.length; c++) {
                sheet.addCell(new Label(c, 0, heads[c]));
            }
            for (int r = 0; r < data.size(); r++) {
                List<String> row = data.get(r);
                for (int c = 0; c < row.size(); c++) {
                    sheet.addCell(new Label(c, r + 1, row.get(c)));
                }
            }
            workbook.write();
            workbook.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void createSheet(OutputStream outputStream,String sheetName,  List<Map<Integer,String>> data){
        try {
            WritableCellFormat cellFormat = new WritableCellFormat();
            cellFormat.setBorder(Border.ALL, BorderLineStyle.THIN);//单元格设置边框
            cellFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
            cellFormat.setAlignment(Alignment.CENTRE);
            WritableWorkbook workbook = jxl.Workbook.createWorkbook(outputStream);
            WritableSheet sheet = workbook.createSheet(sheetName, 0);
            Map<Integer,String> titleMap=data.get(0);
            for	(int c = 0;c < titleMap.size();c++){
                sheet.addCell(new Label(c, 0, titleMap.get(c)==null?titleMap.get(c+""):titleMap.get(c),cellFormat));
            }
            for (int r = 1;r < data.size();r++) {
                Map<Integer,String> row = data.get(r);
                for(int c = 0;c < row.size();c++){
                    sheet.addCell(new Label(c, r, row.get(c+"")==null?row.get(c):row.get(c+""),cellFormat));
                }
            }
            workbook.write();
            workbook.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static List<List<String>> getData(String filePath) {
        List<List<String>> data = new ArrayList<>();
        Workbook workbook;
        try {
            if (filePath.contains(".xlsx")) {
                workbook = new XSSFWorkbook(new FileInputStream(filePath));
            } else if (filePath.contains(".xls")) {
                workbook = new HSSFWorkbook(new FileInputStream(filePath));
            } else {
                return null;
            }
            Sheet sheet = workbook.getSheetAt(0); // 创建对工作表的引用
            int rows = sheet.getPhysicalNumberOfRows();// 获取表格的
            int columns = 0;
            for (int r = 0; r < rows; r++) { // 循环遍历表格的行
                if (r == 0) columns = sheet.getRow(r).getLastCellNum();
                List<String> line = new ArrayList<>(columns);
                Row row = sheet.getRow(r); // 获取单元格中指定的行对象
                if (row == null) continue;
                for (short c = 0; c < columns; c++) {
                    Cell cell = row.getCell(c);
                    String v = cell == null ? "" : cell.toString();
                    line.add(v);
                }
                data.add(line);
            }
            return data;
        } catch (Exception e) {
            log.error("读取excel文件异常",e);
        }
        return data;
    }

}
