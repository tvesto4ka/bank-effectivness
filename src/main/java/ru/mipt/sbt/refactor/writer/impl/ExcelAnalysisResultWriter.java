package ru.mipt.sbt.refactor.writer.impl;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import ru.mipt.sbt.refactor.builder.Norms;
import ru.mipt.sbt.refactor.builder.Value;
import ru.mipt.sbt.refactor.writer.AnalysisResultWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by Toma on 31.05.2017.
 */
public class ExcelAnalysisResultWriter implements AnalysisResultWriter {
    @Override
    public void write(Map<Norms, List<Value>> analysisResult, File file) {
        Workbook book = new XSSFWorkbook();
        Sheet sheet = book.createSheet("AnalysisResult");

        Row row = sheet.createRow(0);
        Cell name = row.createCell(0);
        name.setCellValue("Показатель");

        Cell minValue = row.createCell(1);
        minValue.setCellValue("MIN");

        Cell maxValue = row.createCell(2);
        maxValue.setCellValue("MAX");

        Cell value;
        for (int k = 0; k < analysisResult.get(Norms.INSTANT_LIQUIDITY).size(); k++) {
            value = row.createCell(k + 3);
            value.setCellValue(analysisResult.get(Norms.INSTANT_LIQUIDITY).get(k).getDate());
        }

        int rowNum = 1;
        for (Norms key : analysisResult.keySet()) {
            row = sheet.createRow(rowNum);
            rowNum++;
            name = row.createCell(0);
            name.setCellValue(key.getName());
            minValue = row.createCell(1);
            minValue.setCellValue(key.getMinValue());
            maxValue = row.createCell(2);
            maxValue.setCellValue(key.getMaxValue());
            List<Value> values = analysisResult.get(key);
            int i = 3;
            for (Value val : values) {
                value = row.createCell(i);
                i++;
                value.setCellValue(val.getFormattedValue());
            }
        }

        sheet.autoSizeColumn(0);
        sheet.autoSizeColumn(1);
        sheet.autoSizeColumn(2);
        sheet.autoSizeColumn(3);
        sheet.autoSizeColumn(4);
        sheet.autoSizeColumn(5);

        try {
            book.write(new FileOutputStream(file));
            book.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
