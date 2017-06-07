package ru.mipt.sbt.old.result;

/**
 * Created by Toma on 28.05.2017.
 */
public interface AnalysisResultWriter {
    void write(AnalysisResult analysisResult, String file);
}
