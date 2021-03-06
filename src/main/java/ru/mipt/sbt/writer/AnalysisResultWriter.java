package ru.mipt.sbt.writer;

import ru.mipt.sbt.builder.Norms;
import ru.mipt.sbt.builder.Value;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * Created by Toma on 28.05.2017.
 */
public interface AnalysisResultWriter {
    void write(Map<Norms, List<Value>> analysisResult, File file);
}
