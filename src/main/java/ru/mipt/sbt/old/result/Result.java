package ru.mipt.sbt.old.result;

import java.util.List;

/**
 * Created by Toma on 31.05.2017.
 */
public class Result {
    private Norms norms;
    private List<Double> values;

    public Result(Norms norms, List<Double> values) {
        this.norms = norms;
        this.values = values;
    }

    public Norms getNorms() {
        return norms;
    }

    public Result setNorms(Norms norms) {
        this.norms = norms;
        return this;
    }

    public List<Double> getValues() {
        return values;
    }

    public Result setValues(List<Double> values) {
        this.values = values;
        return this;
    }
}
