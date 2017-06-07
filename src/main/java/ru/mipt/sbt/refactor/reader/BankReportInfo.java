package ru.mipt.sbt.refactor.reader;

/**
 * Created by Toma on 28.05.2017.
 */
public class BankReportInfo {
    private String year;
    private Double totalActives;
    private Double totalPassives;
    private Double pureProfit;
    private Double ownFunds;
    private Double money;
    private Double centralBankMoney;
    private Double clientsMoney;
    private Double income;
    private Double profitableAssets;
    private Double costs;

    public Double getTotalActives() {
        return totalActives;
    }

    public BankReportInfo setTotalActives(Double totalActives) {
        this.totalActives = totalActives;
        return this;
    }

    public Double getTotalPassives() {
        return totalPassives;
    }

    public BankReportInfo setTotalPassives(Double totalPassives) {
        this.totalPassives = totalPassives;
        return this;
    }

    public Double getPureProfit() {
        return pureProfit;
    }

    public BankReportInfo setPureProfit(Double pureProfit) {
        this.pureProfit = pureProfit;
        return this;
    }

    public Double getOwnFunds() {
        return ownFunds;
    }

    public BankReportInfo setOwnFunds(Double ownFunds) {
        this.ownFunds = ownFunds;
        return this;
    }

    public Double getMoney() {
        return money;
    }

    public BankReportInfo setMoney(Double money) {
        this.money = money;
        return this;
    }

    public Double getCentralBankMoney() {
        return centralBankMoney;
    }

    public BankReportInfo setCentralBankMoney(Double centralBankMoney) {
        this.centralBankMoney = centralBankMoney;
        return this;
    }

    public Double getClientsMoney() {
        return clientsMoney;
    }

    public BankReportInfo setClientsMoney(Double clientsMoney) {
        this.clientsMoney = clientsMoney;
        return this;
    }

    public Double getIncome() {
        return income;
    }

    public BankReportInfo setIncome(Double income) {
        this.income = income;
        return this;
    }

    public Double getProfitableAssets() {
        return profitableAssets;
    }

    public BankReportInfo setProfitableAssets(Double profitableAssets) {
        this.profitableAssets = profitableAssets;
        return this;
    }

    public Double getCosts() {
        return costs;
    }

    public BankReportInfo setCosts(Double costs) {
        this.costs = costs;
        return this;
    }

    public String getYear() {
        return year;
    }

    public BankReportInfo setYear(String year) {
        this.year = year;
        return this;
    }
}
