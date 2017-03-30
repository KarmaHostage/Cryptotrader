package com.cryptopurse.cryptotrader.papertrading.controller.dto;

import com.cryptopurse.cryptotrader.papertrading.domain.PaperTrade;
import com.cryptopurse.cryptotrader.papertrading.domain.PaperTradeConfiguration;

import java.util.List;

public class PaperTradePerConfigurationDTo {

    private PaperTradeConfiguration configuration;
    private List<PaperTrade> trades;

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
}
