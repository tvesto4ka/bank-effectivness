package ru.mipt.sbt.old.result;

import ru.mipt.sbt.old.report.BankReportInfo;

/**
 * Created by Toma on 29.05.2017.
 */
public interface AnalysisResultBuilder {
    AnalysisResult createAnalysisResult(BankReportInfo bankReportInfo);
}
