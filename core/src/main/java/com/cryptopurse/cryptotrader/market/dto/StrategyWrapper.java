package com.cryptopurse.cryptotrader.market.dto;

import com.cryptopurse.cryptotrader.advice.domain.StrategyType;
import eu.verdelhan.ta4j.Strategy;

public class StrategyWrapper {

    public StrategyWrapper(Strategy strategy, StrategyType type) {
        this.strategy = strategy;
        this.type = type;
    }

    private Strategy strategy;
    private StrategyType type;

    public Strategy getStrategy() {
        return strategy;
    }

    public StrategyType getType() {
        return type;
    }
}
