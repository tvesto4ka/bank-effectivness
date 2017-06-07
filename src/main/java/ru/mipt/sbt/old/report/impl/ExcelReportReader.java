package ru.mipt.sbt.old.report.impl;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import ru.mipt.sbt.old.report.BankReportInfo;
import ru.mipt.sbt.old.report.ReportReader;

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
    public BankReportInfo readFromFile(String file) {
        try (XSSFWorkbook myExcelBook = new XSSFWorkbook(new FileInputStream(file))) {
            BankReportInfo bankReportInfo = new BankReportInfo();
            bankReportInfo.setTotalActives(getValue(myExcelBook, ACTIVE_SHEET_NAME, "ИТОГО АКТИВОВ"))
                    .setTotalPassives(getValue(myExcelBook, PASSIVE_SHEET_NAME, "ИТОГО ОБЯЗАТЕЛЬСТВ И СОБСТВЕННЫХ СРЕДСТВ"))
                    .setCentralBankMoney(getValue(myExcelBook, ACTIVE_SHEET_NAME, "Обязательные резервы на счетах в центральных банках"))
                    .setMoney(getValue(myExcelBook, ACTIVE_SHEET_NAME, "Денежные средства и их эквиваленты"))
                    .setCosts(getValue(myExcelBook, PROFIT_SHEET_NAME, "Операционные расходы"))
                    .setClientsMoney(getValue(myExcelBook, PASSIVE_SHEET_NAME, "Средства физических лиц")
                            //+ getValue(myExcelBook, PASSIVE_SHEET_NAME, "Средства банков")
                            + getValue(myExcelBook, PASSIVE_SHEET_NAME, "Средства корпоративных клиентов"))
                    .setIncome(getValue(myExcelBook, PROFIT_SHEET_NAME, "Операционные доходы"))
                    .setOwnFunds(getValue(myExcelBook, PASSIVE_SHEET_NAME, "ИТОГО СОБСТВЕННЫХ СРЕДСТВ"))
                    .setProfitableAssets(getValue(myExcelBook, ACTIVE_SHEET_NAME, "Средства в банках")
                            + getValue(myExcelBook, ACTIVE_SHEET_NAME, "Кредиты и авансы клиентам")
                            + getValue(myExcelBook, ACTIVE_SHEET_NAME, "Ценные бумаги, заложенные по договорам репо")
                            + getValue(myExcelBook, ACTIVE_SHEET_NAME, "Инвестиционные ценные бумаги, имеющиеся в наличии для продажи")
                            + getValue(myExcelBook, ACTIVE_SHEET_NAME, "Инвестиционные ценные бумаги, удерживаемые до погашения"))
                    .setPureProfit(getValue(myExcelBook, PROFIT_SHEET_NAME, "Прибыль за год"));
            return bankReportInfo;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static Double getValue(XSSFWorkbook myExcelBook, String sheetName, String rowName) {
        XSSFSheet sheet = myExcelBook.getSheet(sheetName);
        Double value = null;
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            XSSFRow row = sheet.getRow(i);
            if (row.getCell(0).getStringCellValue().trim().equalsIgnoreCase(rowName)) {
                value = row.getCell(1).getNumericCellValue();
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
}
