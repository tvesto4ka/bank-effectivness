package ru.mipt.sbt.reader.impl;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import ru.mipt.sbt.reader.BankReportInfo;
import ru.mipt.sbt.reader.ReportReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by Toma on 29.05.2017.
 */
public class ExcelReportReader implements ReportReader {

    private static final String ACTIVE_SHEET_NAME = "активы";
    private static final String PASSIVE_SHEET_NAME = "пассивы";
    private static final String PROFIT_SHEET_NAME = "доходы расходы и прибыль";

    @Override
    public BankReportInfo readFromFile(File file, int yearOrder) {
        try (FileInputStream fileIS = new FileInputStream(file)) {
            Workbook myExcelBook;
            if (file.getAbsolutePath().split("\\.")[1].equalsIgnoreCase("xlsx")) {
                myExcelBook = new XSSFWorkbook(fileIS);
            } else {
                myExcelBook = new HSSFWorkbook(fileIS);
            }
            BankReportInfo bankReportInfo = new BankReportInfo();
            bankReportInfo.setTotalActives(getValue(myExcelBook, ACTIVE_SHEET_NAME, "ИТОГО АКТИВОВ", yearOrder))
                    .setTotalPassives(getValue(myExcelBook, PASSIVE_SHEET_NAME, "ИТОГО ОБЯЗАТЕЛЬСТВ И СОБСТВЕННЫХ СРЕДСТВ", yearOrder))
                    .setCentralBankMoney(getValue(myExcelBook, ACTIVE_SHEET_NAME, "Обязательные резервы на счетах в центральных банках", yearOrder))
                    .setMoney(getValue(myExcelBook, ACTIVE_SHEET_NAME, "Денежные средства и их эквиваленты", yearOrder))
                    .setCosts(getValue(myExcelBook, PROFIT_SHEET_NAME, "Операционные расходы", yearOrder))
                    .setClientsMoney(getValue(myExcelBook, PASSIVE_SHEET_NAME, "Средства физических лиц", yearOrder)
                            //+ getValue(myExcelBook, PASSIVE_SHEET_NAME, "Средства банков", yearOrder)
                            + getValue(myExcelBook, PASSIVE_SHEET_NAME, "Средства корпоративных клиентов", yearOrder))
                    .setIncome(getValue(myExcelBook, PROFIT_SHEET_NAME, "Операционные доходы", yearOrder))
                    .setOwnFunds(getValue(myExcelBook, PASSIVE_SHEET_NAME, "ИТОГО СОБСТВЕННЫХ СРЕДСТВ", yearOrder))
                    .setProfitableAssets(getValue(myExcelBook, ACTIVE_SHEET_NAME, "Средства в банках", yearOrder)
                            + getValue(myExcelBook, ACTIVE_SHEET_NAME, "Кредиты и авансы клиентам", yearOrder)
                            + getValue(myExcelBook, ACTIVE_SHEET_NAME, "Ценные бумаги, заложенные по договорам репо", yearOrder)
                            + getValue(myExcelBook, ACTIVE_SHEET_NAME, "Инвестиционные ценные бумаги, имеющиеся в наличии для продажи", yearOrder)
                            + getValue(myExcelBook, ACTIVE_SHEET_NAME, "Инвестиционные ценные бумаги, удерживаемые до погашения", yearOrder))
                    .setPureProfit(getValue(myExcelBook, PROFIT_SHEET_NAME, "Прибыль за год", yearOrder))
                    .setYear(getYear(myExcelBook, ACTIVE_SHEET_NAME, "Показатель", yearOrder));
            return bankReportInfo;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static Double getValue(Workbook myExcelBook, String sheetName, String rowName, int columnNumber) {
        Sheet sheet = myExcelBook.getSheet(sheetName);
        Double value = null;
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row.getCell(0).getStringCellValue().trim().equalsIgnoreCase(rowName)) {
                value = row.getCell(columnNumber).getNumericCellValue();
                break;
            }
        }
        if (value == null) {
            throw new IllegalArgumentException("In sheet with name = '" + sheetName + "' no row with name = '" + rowName + "'");
        } else if (value < 0) {
            value *= (-1);
        }
        return value;
    }

    private static String getYear(Workbook myExcelBook, String sheetName, String rowName, int columnNumber) {
        Sheet sheet = myExcelBook.getSheet(sheetName);
        Row row = sheet.getRow(0);
        String value;
        value = row.getCell(columnNumber).getStringCellValue();
        if (value == null) {
            throw new IllegalArgumentException("In sheet with name = '" + sheetName + "' no row with name = '" + rowName + "'");
        }
        return value.split(",")[0];
    }
}

