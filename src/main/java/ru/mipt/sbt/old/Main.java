package ru.mipt.sbt.old;

import ru.mipt.sbt.old.report.BankReportInfo;
import ru.mipt.sbt.old.report.impl.ExcelReportReader;
import ru.mipt.sbt.old.report.ReportReader;
import ru.mipt.sbt.old.result.AnalysisResult;
import ru.mipt.sbt.old.result.AnalysisResultWriter;
import ru.mipt.sbt.old.result.impl.ConsoleAnalysisResultWriter;
import ru.mipt.sbt.old.result.impl.ExcelAnalysisResultWriter;
import ru.mipt.sbt.old.result.impl.SimpleAnalysisResultBuilder;

/**
 * Created by Toma on 28.05.2017.
 */
public class Main {
    public static void main(String[] args) {
        ReportReader reader = new ExcelReportReader();
        BankReportInfo bankReportInfo = reader.readFromFile("C:\\Users\\Toma\\IdeaProjects\\пример отчета.xlsx");
        if (bankReportInfo != null) {
            SimpleAnalysisResultBuilder reportMaker = new SimpleAnalysisResultBuilder();
            AnalysisResult analysisResult = reportMaker.createAnalysisResult(bankReportInfo);
            AnalysisResultWriter consoleWriter = new ConsoleAnalysisResultWriter();
            consoleWriter.write(analysisResult, null);
            AnalysisResultWriter excelWriter = new ExcelAnalysisResultWriter();
            excelWriter.write(analysisResult, "C:\\Users\\Toma\\IdeaProjects\\результаты анализа.xlsx");
        }
    }
}
