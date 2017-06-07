package ru.mipt.sbt.builder;

/**
 * Created by Toma on 29.05.2017.
 */
public enum Norms {
    INSTANT_LIQUIDITY("Коэффициент мгновенной ликвидности ", 0.15, 1),
    PROFITABLE_ASSETS("Уровень доходных активов                     ", 0.65, 0.75),
    GENERAL_STABILITY("Коэффициент общей стабильности        ", 0.5, 1),
    RETURN_ON_ASSETS("Коэффициент рентабельности активов  ", 0.005, 0.05),
    CAPITAL_ADEQUACY("Коэффициент достаточности капитала   ", 0.15, 0.2);

    double minValue;
    double maxValue;
    String name;

    Norms(String name, double minValue, double maxValue) {
        this.name = name;
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    public double getMinValue() {
        return minValue;
    }

    public double getMaxValue() {
        return maxValue;
    }

    public String getName() {
        return name;
    }

    public boolean isNormal(Double value) {
        return value < maxValue && value > minValue;
    }
}
