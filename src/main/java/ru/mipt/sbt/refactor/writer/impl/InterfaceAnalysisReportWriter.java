package ru.mipt.sbt.refactor.writer.impl;

import ru.mipt.sbt.refactor.builder.Norms;
import ru.mipt.sbt.refactor.builder.Value;
import ru.mipt.sbt.refactor.writer.AnalysisResultWriter;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * Created by Toma on 05.06.2017.
 */
public class InterfaceAnalysisReportWriter implements AnalysisResultWriter {

    public String writeNormalResults(Map<Norms, List<Value>> analysisResult) {

        StringBuilder result = new StringBuilder("ПОКАЗАТЕЛИ В НОРМЕ:\n");
        for (Norms norms : analysisResult.keySet()) {
            for (Value value : analysisResult.get(norms)) {
                if (norms.isNormal(value.getValue())) {
                    result.append(norms.getName()).append(" на ").append(value.getDate()).append(" равен ").append(value.getFormattedValue()).append(". Норма ").append(norms.getMinValue()).append("-").append(norms.getMaxValue()).append("\n");
                }
            }
        }
        return result.toString();
    }

    public String writeNotNormalResults(Map<Norms, List<Value>> analysisResult) {

        StringBuilder result = new StringBuilder("\nПОКАЗАТЕЛИ НЕ В НОРМЕ:\n");
        for (Norms norms : analysisResult.keySet()) {
            for (Value value : analysisResult.get(norms)) {
                if (!norms.isNormal(value.getValue())) {
                    result.append(norms.getName()).append(" на ").append(value.getDate()).append(" равен ").append(value.getFormattedValue()).append(". Норма ").append(norms.getMinValue()).append("-").append(norms.getMaxValue()).append("\n");
                }
            }
        }
        return result.toString();
    }

    @Override
    public void write(Map<Norms, List<Value>> analysisResult, File file) {

    }
}
