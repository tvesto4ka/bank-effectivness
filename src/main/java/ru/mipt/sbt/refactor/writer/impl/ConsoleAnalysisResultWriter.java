package ru.mipt.sbt.refactor.writer.impl;

import ru.mipt.sbt.refactor.builder.Norms;
import ru.mipt.sbt.refactor.builder.Value;
import ru.mipt.sbt.refactor.writer.AnalysisResultWriter;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * Created by Toma on 28.05.2017.
 */
public class ConsoleAnalysisResultWriter implements AnalysisResultWriter {
    private static final String BLACK = "\u001B[30m";
    private static final String RED = "\u001B[31m";
    private static final String GREEN = "\u001B[32m";
    private static final String YELLOW = "\u001B[33m";
    private static final String BLUE = "\u001B[34m";
    private static final String PURPLE = "\u001B[35m";
    private static final String CYAN = "\u001B[36m";
    private static final String WHITE = "\u001B[37m";
    //Reset code
    private static final String RESET = "\u001B[0m";

    @Override
    public void write(Map<Norms, List<Value>> analysisResult, File file) {
//        writeNormalizeResults(analysisResult, file);
        writeFullResult(analysisResult);
    }

    public void writeNormalizeResults(Map<Norms, List<Value>> analysisResult){
        System.out.println(RESET + "Показатели в норме:");
        for (Norms norms : analysisResult.keySet()) {
            for (Value value : analysisResult.get(norms)) {
                if (norms.isNormal(value.getValue())) {
                    System.out.println(PURPLE + norms.getName() + RESET + " на " + CYAN + value.getDate() +  RESET + " равен " + GREEN + value.getFormattedValue() + RESET + ". Норма " + BLUE + norms.getMinValue() + "-" + norms.getMaxValue());
                }
            }
        }
        System.out.println();
        System.out.println(RESET + "Показатели НЕ в норме:");
        for (Norms norms : analysisResult.keySet()) {
            for (Value value : analysisResult.get(norms)) {
                if (!norms.isNormal(value.getValue())) {
                    System.out.println(PURPLE + norms.getName() + RESET + " на " + CYAN + value.getDate() +  RESET + " равен "  + RED + value.getFormattedValue() + RESET + ". Норма " + BLUE + norms.getMinValue() + "-" + norms.getMaxValue());
                }
            }
        }
    }

    private void writeFullResult(Map<Norms, List<Value>> analysisResult) {
        for (Norms norms : analysisResult.keySet()) {
            System.out.println(YELLOW + norms.getName() + RESET + " с нормой = " + BLUE + norms.getMinValue() + "-" + norms.getMaxValue() + RESET + " принимал значения:");
            for (Value value : analysisResult.get(norms)) {
                String color;
                if (norms.isNormal(value.getValue())) {
                    color = GREEN;
                } else {
                    color = RED;
                }
                System.out.println("На " + value.getDate() + " равен " + color + value.getFormattedValue() + RESET);
            }
            System.out.println();
        }
    }
}
