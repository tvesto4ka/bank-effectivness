package ru.mipt.sbt.refactor.reader;

import java.io.File;

/**
 * Created by Toma on 29.05.2017.
 */
public interface ReportReader {
    BankReportInfo readFromFile(File file, int yearOrder);
}
