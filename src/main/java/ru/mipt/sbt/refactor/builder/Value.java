package ru.mipt.sbt.refactor.builder;

/**
 * Created by Toma on 31.05.2017.
 */
public class Value {
    private Double value;
    private String date;

    public Double getValue() {
        return value;
    }

    public String getFormattedValue() {
        String format = "%.3f";
        return String.format(format, value);
    }

    public Value(Double value) {
        this.value = value;
    }

    public String getDate() {
        return date;
    }

    public Value setDate(String date) {
        this.date = date;
        return this;
    }
}
