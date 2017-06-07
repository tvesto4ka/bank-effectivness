package ru.mipt.sbt.old.result.impl;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import ru.mipt.sbt.old.result.AnalysisResult;
import ru.mipt.sbt.old.result.AnalysisResultWriter;
import ru.mipt.sbt.old.result.Norms;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Toma on 31.05.2017.
 */
public class ExcelAnalysisResultWriter implements AnalysisResultWriter {
    @Override
    public void write(AnalysisResult analysisResult, String file) {
        Workbook book = new XSSFWorkbook();
        Sheet sheet = book.createSheet("AnalysisResult");

        Row row = sheet.createRow(0);
        Cell name = row.createCell(0);
        name.setCellValue("Показатель");

        Cell value = row.createCell(1);
        value.setCellValue("Значение");

        Cell minValue = row.createCell(2);
        minValue.setCellValue("Минимальное рекомендованное значение");

        Cell maxValue = row.createCell(3);
        maxValue.setCellValue("Максимальное рекомендованное значение");

        row = sheet.createRow(1);
        name = row.createCell(0);
        name.setCellValue(Norms.INSTANT_LIQUIDITY.getName());

        value = row.createCell(1);
        value.setCellValue(analysisResult.getFormattedInstantLiquidity());

        minValue = row.createCell(2);
        minValue.setCellValue(Norms.INSTANT_LIQUIDITY.getMinValue());

        maxValue = row.createCell(3);
        maxValue.setCellValue(Norms.INSTANT_LIQUIDITY.getMaxValue());

        //sheet.autoSizeColumn(1);

        try {
            book.write(new FileOutputStream(file));
            book.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
