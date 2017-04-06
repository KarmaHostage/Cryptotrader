package com.karmahostage.cryptotrader.papertrading.controller.dto;

import com.karmahostage.cryptotrader.papertrading.domain.PaperTrade;
import com.karmahostage.cryptotrader.papertrading.domain.PaperTradeConfiguration;

import java.util.List;

public class PaperTradePerConfigurationDTo {

    private PaperTradeConfiguration configuration;
    private List<PaperTrade> trades;
    private String totalProfit;
    private String averageProfitableTrades;
    private String transactionCosts;
    private String averageProfit;

    public PaperTradePerConfigurationDTo(final PaperTradeConfiguration configuration, final List<PaperTrade> trades) {
        this.configuration = configuration;
        this.trades = trades;
    }

    public PaperTradeConfiguration getConfiguration() {
        return configuration;
    }

    public PaperTradePerConfigurationDTo setConfiguration(final PaperTradeConfiguration configuration) {
        this.configuration = configuration;
        return this;
    }

    public List<PaperTrade> getTrades() {
        return trades;
    }

    public PaperTradePerConfigurationDTo setTrades(final List<PaperTrade> trades) {
        this.trades = trades;
        return this;
    }

    public String getTotalProfit() {
        return totalProfit;
    }

    public PaperTradePerConfigurationDTo setTotalProfit(final String totalProfit) {
        this.totalProfit = totalProfit;
        return this;
    }

    public String getAverageProfitableTrades() {
        return averageProfitableTrades;
    }

    public PaperTradePerConfigurationDTo setAverageProfitableTrades(final String averageProfitableTrades) {
        this.averageProfitableTrades = averageProfitableTrades;
        return this;
    }

    public String getTransactionCosts() {
        return transactionCosts;
    }

    public PaperTradePerConfigurationDTo setTransactionCosts(final String transactionCosts) {
        this.transactionCosts = transactionCosts;
        return this;
    }

    public String getAverageProfit() {
        return averageProfit;
    }

    public PaperTradePerConfigurationDTo setAverageProfit(final String averageProfit) {
        this.averageProfit = averageProfit;
        return this;
    }
}
