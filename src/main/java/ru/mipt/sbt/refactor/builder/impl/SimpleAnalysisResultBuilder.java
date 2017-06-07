package ru.mipt.sbt.refactor.builder.impl;

import ru.mipt.sbt.refactor.builder.AnalysisResultBuilder;
import ru.mipt.sbt.refactor.builder.Norms;
import ru.mipt.sbt.refactor.builder.Value;
import ru.mipt.sbt.refactor.reader.BankReportInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by Toma on 29.05.2017.
 */
public class SimpleAnalysisResultBuilder implements AnalysisResultBuilder {
    @Override
    public Map<Norms, List<Value>> createAnalysisResult(List<BankReportInfo> bankReportInfo) {
        Map<Norms, List<Value>> values = new HashMap<>();
        for (BankReportInfo info : bankReportInfo) {
            Value value = new Value((info.getCentralBankMoney() + info.getMoney()) / info.getClientsMoney());
            value.setDate(info.getYear());
            if (values.get(Norms.INSTANT_LIQUIDITY) == null) {
                List<Value> valueByYears = new ArrayList<>();
                valueByYears.add(value);
                values.put(Norms.INSTANT_LIQUIDITY, valueByYears);
            } else {
                values.get(Norms.INSTANT_LIQUIDITY).add(value);
            }
            value = new Value(info.getCosts() / info.getIncome());
            value.setDate(info.getYear());
            if (values.get(Norms.GENERAL_STABILITY) == null) {
                List<Value> valueByYears = new ArrayList<>();
                valueByYears.add(value);
                values.put(Norms.GENERAL_STABILITY, valueByYears);
            } else {
                values.get(Norms.GENERAL_STABILITY).add(value);
            }
            value = new Value(info.getPureProfit() / info.getTotalActives());
            value.setDate(info.getYear());
            if (values.get(Norms.RETURN_ON_ASSETS) == null) {
                List<Value> valueByYears = new ArrayList<>();
                valueByYears.add(value);
                values.put(Norms.RETURN_ON_ASSETS, valueByYears);
            } else {
                values.get(Norms.RETURN_ON_ASSETS).add(value);
            }
            value = new Value(info.getOwnFunds() / info.getTotalPassives());
            value.setDate(info.getYear());
            if (values.get(Norms.CAPITAL_ADEQUACY) == null) {
                List<Value> valueByYears = new ArrayList<>();
                valueByYears.add(value);
                values.put(Norms.CAPITAL_ADEQUACY, valueByYears);
            } else {
                values.get(Norms.CAPITAL_ADEQUACY).add(value);
            }
            value = new Value(info.getProfitableAssets() / info.getTotalActives());
            value.setDate(info.getYear());
            if (values.get(Norms.PROFITABLE_ASSETS) == null) {
                List<Value> valueByYears = new ArrayList<>();
                valueByYears.add(value);
                values.put(Norms.PROFITABLE_ASSETS, valueByYears);
            } else {
                values.get(Norms.PROFITABLE_ASSETS).add(value);
            }
        }
        return values;
    }
}
