package ru.mipt.sbt.reader;

import ru.mipt.sbt.reader.impl.ExcelReportReader;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Toma on 05.06.2017.
 */
public class ReaderService {
    public List<BankReportInfo> readFile(File file, Integer numberOfYears) {
        ReportReader reader = new ExcelReportReader();
        List<BankReportInfo> data = new LinkedList<>();
        for (int i = 0; i < numberOfYears; i++) {
            BankReportInfo bankReportInfo = reader.readFromFile(file, i + 1);
            if (bankReportInfo != null) {
                data.add(bankReportInfo);
            }
        }
        return data;
    }
}
