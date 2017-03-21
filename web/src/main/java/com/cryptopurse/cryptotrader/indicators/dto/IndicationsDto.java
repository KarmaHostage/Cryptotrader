package com.cryptopurse.cryptotrader.indicators.dto;

import com.cryptopurse.cryptotrader.advice.domain.KrakenGeneralAdvice;
import com.cryptopurse.cryptotrader.advice.domain.StrategyPeriod;
import com.cryptopurse.cryptotrader.market.domain.CurrencyPair;

import java.util.List;

public class IndicationsDto {

    private List<KrakenGeneralAdvice> krakenGeneralAdvices;
    private StrategyPeriod period;

    public IndicationsDto(List<KrakenGeneralAdvice> krakenGeneralAdvices, StrategyPeriod period) {
        this.krakenGeneralAdvices = krakenGeneralAdvices;
        this.period = period;
    }

    public List<KrakenGeneralAdvice> getKrakenGeneralAdvices() {
        return krakenGeneralAdvices;
    }

    public IndicationsDto setKrakenGeneralAdvices(List<KrakenGeneralAdvice> krakenGeneralAdvices) {
        this.krakenGeneralAdvices = krakenGeneralAdvices;
        return this;
    }

    public StrategyPeriod getPeriod() {
        return period;
    }

    public IndicationsDto setPeriod(StrategyPeriod period) {
        this.period = period;
        return this;
    }


}
