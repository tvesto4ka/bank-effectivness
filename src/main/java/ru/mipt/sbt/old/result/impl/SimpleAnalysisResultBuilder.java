package ru.mipt.sbt.old.result.impl;

import ru.mipt.sbt.old.report.BankReportInfo;
import ru.mipt.sbt.old.result.AnalysisResult;
import ru.mipt.sbt.old.result.AnalysisResultBuilder;

/**
 * Created by Toma on 29.05.2017.
 */
public class SimpleAnalysisResultBuilder implements AnalysisResultBuilder {
    @Override
    public AnalysisResult createAnalysisResult(BankReportInfo bankReportInfo) {
        AnalysisResult analysisResult = new AnalysisResult();
        analysisResult.setInstantLiquidity((bankReportInfo.getCentralBankMoney() + bankReportInfo.getMoney()) / bankReportInfo.getClientsMoney())
                .setProfitableAssets(bankReportInfo.getProfitableAssets() / bankReportInfo.getTotalActives())
                .setGeneralStability(bankReportInfo.getCosts() / bankReportInfo.getIncome())
                .setReturnOnAssets(bankReportInfo.getPureProfit() / bankReportInfo.getTotalActives())
                .setCapitalAdequacy(bankReportInfo.getOwnFunds() / bankReportInfo.getTotalPassives());
        return analysisResult;
    }
}
