package ru.mipt.sbt.refactor.builder;

import ru.mipt.sbt.refactor.builder.impl.SimpleAnalysisResultBuilder;
import ru.mipt.sbt.refactor.reader.BankReportInfo;

import java.util.List;
import java.util.Map;

/**
 * Created by Toma on 05.06.2017.
 */
public class AnalysisService {
    public Map<Norms, List<Value>> analyseReport(List<BankReportInfo> data) {
        if (!data.isEmpty()) {
            SimpleAnalysisResultBuilder reportMaker = new SimpleAnalysisResultBuilder();
            return reportMaker.createAnalysisResult(data);
        }
        return null;
    }
}
