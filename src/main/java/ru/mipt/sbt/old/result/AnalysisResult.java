package ru.mipt.sbt.old.result;

/**
 * Created by Toma on 28.05.2017.
 */
public class AnalysisResult {
    private Double instantLiquidity;
    private Double profitableAssets;
    private Double generalStability;
    private Double returnOnAssets;
    private Double capitalAdequacy;

    private static String format = "%.3f";

    public Double getInstantLiquidity() {
        return instantLiquidity;
    }

    public AnalysisResult setInstantLiquidity(Double instantLiquidity) {
        this.instantLiquidity = instantLiquidity;
        return this;
    }

    public Double getProfitableAssets() {
        return profitableAssets;
    }

    public AnalysisResult setProfitableAssets(Double profitableAssets) {
        this.profitableAssets = profitableAssets;
        return this;
    }

    public Double getGeneralStability() {
        return generalStability;
    }

    public AnalysisResult setGeneralStability(Double generalStability) {
        this.generalStability = generalStability;
        return this;
    }

    public Double getReturnOnAssets() {
        return returnOnAssets;
    }

    public AnalysisResult setReturnOnAssets(Double returnOnAssets) {
        this.returnOnAssets = returnOnAssets;
        return this;
    }

    public Double getCapitalAdequacy() {
        return capitalAdequacy;
    }

    public AnalysisResult setCapitalAdequacy(Double capitalAdequacy) {
        this.capitalAdequacy = capitalAdequacy;
        return this;
    }

    public String getFormattedInstantLiquidity() {
        return String.format(format, instantLiquidity);
    }

    public String getFormattedProfitableAssets() {
        return String.format(format, profitableAssets);
    }

    public String getFormattedGeneralStability() {
        return String.format(format, generalStability);
    }

    public String getFormattedReturnOnAssets() {
        return String.format(format, returnOnAssets);
    }

    public String getFormattedCapitalAdequacy() {
        return String.format(format, capitalAdequacy);
    }
}
