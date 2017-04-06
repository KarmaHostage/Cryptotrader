package com.karmahostage.cryptotrader.indicators.dto;

import com.karmahostage.cryptotrader.advice.domain.GeneralAdvice;
import com.karmahostage.cryptotrader.advice.domain.StrategyPeriod;

import java.util.List;

public class IndicationsDto {

    private List<GeneralAdvice> generalAdvices;
    private StrategyPeriod period;

    public IndicationsDto(List<GeneralAdvice> generalAdvices, StrategyPeriod period) {
        this.generalAdvices = generalAdvices;
        this.period = period;
    }

    public List<GeneralAdvice> getGeneralAdvices() {
        return generalAdvices;
    }

    public IndicationsDto setGeneralAdvices(List<GeneralAdvice> generalAdvices) {
        this.generalAdvices = generalAdvices;
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
