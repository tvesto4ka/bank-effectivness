package ru.mipt.sbt.old.report;

/**
 * Created by Toma on 29.05.2017.
 */
public interface ReportReader {
    BankReportInfo readFromFile(String file);
}
