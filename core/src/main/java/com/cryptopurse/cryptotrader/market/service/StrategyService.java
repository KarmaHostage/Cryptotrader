package com.cryptopurse.cryptotrader.market.service;

import com.cryptopurse.cryptotrader.market.strategy.CCICorrectionStrategy;
import com.cryptopurse.cryptotrader.market.strategy.MovingMomentumStrategy;
import com.cryptopurse.cryptotrader.market.strategy.RSI2Strategy;
import eu.verdelhan.ta4j.Strategy;
import eu.verdelhan.ta4j.TimeSeries;
import eu.verdelhan.ta4j.indicators.simple.ClosePriceIndicator;
import eu.verdelhan.ta4j.indicators.trackers.SMAIndicator;
import eu.verdelhan.ta4j.trading.rules.OverIndicatorRule;
import eu.verdelhan.ta4j.trading.rules.UnderIndicatorRule;
import org.springframework.stereotype.Component;

@Component
public class StrategyService {

    public Strategy smaIndicator(TimeSeries timeSeries) {
        ClosePriceIndicator closePrice = new ClosePriceIndicator(timeSeries);
        SMAIndicator sma = new SMAIndicator(closePrice, 10);

        return new Strategy(
                new OverIndicatorRule(sma, closePrice),
                new UnderIndicatorRule(sma, closePrice)
        );
    }

    public Strategy movingMomentumStrategy(TimeSeries timeSeries) {
        return MovingMomentumStrategy.buildStrategy(timeSeries);
    }

    public Strategy cciStrategy(TimeSeries timeSeries) {
        return CCICorrectionStrategy.buildStrategy(timeSeries);
    }

    public Strategy rsiStrategy(TimeSeries timeSeries) {
        return RSI2Strategy.buildStrategy(timeSeries);
    }
}
