package ru.mipt.sbt.refactor.writer;

import ru.mipt.sbt.refactor.builder.Norms;
import ru.mipt.sbt.refactor.builder.Value;
import ru.mipt.sbt.refactor.writer.impl.ConsoleAnalysisResultWriter;
import ru.mipt.sbt.refactor.writer.impl.ExcelAnalysisResultWriter;
import ru.mipt.sbt.refactor.writer.impl.InterfaceAnalysisReportWriter;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * Created by Toma on 05.06.2017.
 */
public class WriterService {
    public String writeNormalResultInInterface(Map<Norms, List<Value>> value) {
        if (!value.isEmpty()) {
            InterfaceAnalysisReportWriter consoleWriter = new InterfaceAnalysisReportWriter();
            return consoleWriter.writeNormalResults(value);
        }
        return null;
    }

    public String writeNotNormalResultInInterface(Map<Norms, List<Value>> value) {
        if (!value.isEmpty()) {
            InterfaceAnalysisReportWriter consoleWriter = new InterfaceAnalysisReportWriter();
            return consoleWriter.writeNotNormalResults(value);
        }
        return null;
    }

    public void writeResultInConsole(Map<Norms, List<Value>> value) {
        if (!value.isEmpty()) {
            AnalysisResultWriter consoleWriter = new ConsoleAnalysisResultWriter();
            consoleWriter.write(value, null);
        }
    }

    public void writeResultInFile(Map<Norms, List<Value>> value, File file) {
        if (!value.isEmpty()) {
            AnalysisResultWriter excelWriter = new ExcelAnalysisResultWriter();
            //"C:\\Users\\Toma\\IdeaProjects\\результаты анализа.xlsx"
            excelWriter.write(value, file);
        }
    }
}
