package ru.mipt.sbt.refactor.builder;

import ru.mipt.sbt.refactor.reader.BankReportInfo;

import java.util.List;
import java.util.Map;

/**
 * Created by Toma on 29.05.2017.
 */
public interface AnalysisResultBuilder {
    Map<Norms, List<Value>> createAnalysisResult(List<BankReportInfo> bankReportInfo);
}
