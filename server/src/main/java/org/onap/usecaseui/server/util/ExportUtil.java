package org.onap.usecaseui.server.util;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.io.IOException;
import java.util.List;


@SuppressWarnings("unused")
@Slf4j
public class ExportUtil {

    public static void exportExcel(HttpServletResponse response, String fileName, List<?> dataList) {
        SXSSFWorkbook book = new SXSSFWorkbook();
        Sheet sheet = book.createSheet();
        CellStyle rowStyle = book.createCellStyle();
        rowStyle.setAlignment(HorizontalAlignment.CENTER);
        rowStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        sheet.setDefaultColumnWidth(15);
        for (int i = 0; i < dataList.size(); i++) {
            Row row = sheet.createRow(i);
            Cell cell = row.createCell(0);
            cell.setCellStyle(rowStyle);
            cell.setCellType(CellType.STRING);
            cell.setCellValue((String) dataList.get(i));
        }
        try {
            write(response, book, fileName);
        } catch (IOException e) {
            log.error("An exception occurred with exportExcel, message: "+e.getMessage());
        }
    }
    private static void write(HttpServletResponse response, SXSSFWorkbook book, String fileName) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        String name = new String(fileName.getBytes("GBK"), "ISO8859_1") + ".xlsx";
        response.addHeader("Content-Disposition", "attachment;filename=" + name);
        ServletOutputStream out = response.getOutputStream();
        book.write(out);
        out.flush();
        out.close();
    }
}
