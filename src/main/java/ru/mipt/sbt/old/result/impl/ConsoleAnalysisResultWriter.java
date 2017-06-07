package ru.mipt.sbt.old.result.impl;

import ru.mipt.sbt.old.result.AnalysisResult;
import ru.mipt.sbt.old.result.AnalysisResultWriter;
import ru.mipt.sbt.old.result.Norms;

/**
 * Created by Toma on 28.05.2017.
 */
public class ConsoleAnalysisResultWriter implements AnalysisResultWriter {
    @Override
    public void write(AnalysisResult analysisResult, String file) {
        System.out.println("Показатели в норме:");
        if (Norms.INSTANT_LIQUIDITY.isNormal(analysisResult.getInstantLiquidity())) {
            System.out.println(Norms.INSTANT_LIQUIDITY.getName() + " = " + analysisResult.getFormattedInstantLiquidity() + ". Норма = " +
                    Norms.INSTANT_LIQUIDITY.getMinValue() + "-" + Norms.INSTANT_LIQUIDITY.getMaxValue());
        }
        if (Norms.PROFITABLE_ASSETS.isNormal(analysisResult.getProfitableAssets())) {
            System.out.println(Norms.PROFITABLE_ASSETS.getName() + " = " + analysisResult.getFormattedProfitableAssets() + ". Норма = " +
                    Norms.PROFITABLE_ASSETS.getMinValue() + "-" + Norms.PROFITABLE_ASSETS.getMaxValue());
        }
        if (Norms.GENERAL_STABILITY.isNormal(analysisResult.getGeneralStability())) {
            System.out.println(Norms.GENERAL_STABILITY.getName() + " = " + analysisResult.getFormattedGeneralStability() + ". Норма = " +
                    Norms.GENERAL_STABILITY.getMinValue() + "-" + Norms.GENERAL_STABILITY.getMaxValue());
        }
        if (Norms.RETURN_ON_ASSETS.isNormal(analysisResult.getReturnOnAssets())) {
            System.out.println(Norms.RETURN_ON_ASSETS.getName() + " = " + analysisResult.getFormattedReturnOnAssets() + ". Норма = " +
                    Norms.RETURN_ON_ASSETS.getMinValue() + "-" + Norms.RETURN_ON_ASSETS.getMaxValue());
        }
        if (Norms.CAPITAL_ADEQUACY.isNormal(analysisResult.getCapitalAdequacy())) {
            System.out.println(Norms.CAPITAL_ADEQUACY.getName() + " = " + analysisResult.getFormattedCapitalAdequacy() + ". Норма = " +
                    Norms.CAPITAL_ADEQUACY.getMinValue() + "-" + Norms.CAPITAL_ADEQUACY.getMaxValue());
        }

        System.out.println();
        System.out.println("Показатели НЕ в норме:");
        if (!Norms.INSTANT_LIQUIDITY.isNormal(analysisResult.getInstantLiquidity())) {
            System.out.println(Norms.INSTANT_LIQUIDITY.getName() + " = " + analysisResult.getFormattedInstantLiquidity() + ". Норма = " +
                    Norms.INSTANT_LIQUIDITY.getMinValue() + "-" + Norms.INSTANT_LIQUIDITY.getMaxValue());
        }
        if (!Norms.PROFITABLE_ASSETS.isNormal(analysisResult.getProfitableAssets())) {
            System.out.println(Norms.PROFITABLE_ASSETS.getName() + " = " + analysisResult.getFormattedProfitableAssets() + ". Норма = " +
                    Norms.PROFITABLE_ASSETS.getMinValue() + "-" + Norms.PROFITABLE_ASSETS.getMaxValue());
        }
        if (!Norms.GENERAL_STABILITY.isNormal(analysisResult.getGeneralStability())) {
            System.out.println(Norms.GENERAL_STABILITY.getName() + " = " + analysisResult.getFormattedGeneralStability() + ". Норма = " +
                    Norms.GENERAL_STABILITY.getMinValue() + "-" + Norms.GENERAL_STABILITY.getMaxValue());
        }
        if (!Norms.RETURN_ON_ASSETS.isNormal(analysisResult.getReturnOnAssets())) {
            System.out.println(Norms.RETURN_ON_ASSETS.getName() + " = " + analysisResult.getFormattedReturnOnAssets() + ". Норма = " +
                    Norms.RETURN_ON_ASSETS.getMinValue() + "-" + Norms.RETURN_ON_ASSETS.getMaxValue());
        }
        if (!Norms.CAPITAL_ADEQUACY.isNormal(analysisResult.getCapitalAdequacy())) {
            System.out.println(Norms.CAPITAL_ADEQUACY.getName() + " = " + analysisResult.getFormattedCapitalAdequacy() + ". Норма = " +
                    Norms.CAPITAL_ADEQUACY.getMinValue() + "-" + Norms.CAPITAL_ADEQUACY.getMaxValue());
        }
    }
}
