package com.cryptopurse.cryptotrader.market.service;

import com.cryptopurse.cryptotrader.advice.domain.StrategyType;
import com.cryptopurse.cryptotrader.market.dto.StrategyWrapper;
import com.cryptopurse.cryptotrader.market.strategy.CCICorrectionStrategy;
import com.cryptopurse.cryptotrader.market.strategy.MovingMomentumStrategy;
import com.cryptopurse.cryptotrader.market.strategy.RSI2Strategy;
import eu.verdelhan.ta4j.Strategy;
import eu.verdelhan.ta4j.TimeSeries;
import eu.verdelhan.ta4j.indicators.simple.ClosePriceIndicator;
import eu.verdelhan.ta4j.indicators.trackers.DoubleEMAIndicator;
import eu.verdelhan.ta4j.indicators.trackers.SMAIndicator;
import eu.verdelhan.ta4j.trading.rules.OverIndicatorRule;
import eu.verdelhan.ta4j.trading.rules.UnderIndicatorRule;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class StrategyService {

    public Optional<StrategyWrapper> byStrategyType(StrategyType strategyType, TimeSeries timeSeries) {
        switch (strategyType) {
            case SMA:
                return Optional.of(smaIndicator(timeSeries));
            case MM:
                return Optional.of(movingMomentumStrategy(timeSeries));
            case CCI:
                return Optional.of(cciStrategy(timeSeries));
            case DEMA:
                return Optional.of(demaIndicator(timeSeries));
            case RSI2:
                return Optional.of(rsi2Strategy(timeSeries));
            default:
                return Optional.empty();
        }
    }

    public StrategyWrapper smaIndicator(TimeSeries timeSeries) {
        final ClosePriceIndicator closePrice = new ClosePriceIndicator(timeSeries);
        final SMAIndicator sma = new SMAIndicator(closePrice, 50);

        return new StrategyWrapper(
                new Strategy(
                        new OverIndicatorRule(sma, closePrice),
                        new UnderIndicatorRule(sma, closePrice)
                ),
                StrategyType.SMA
        );
    }

    public StrategyWrapper demaIndicator(TimeSeries timeSeries) {
        final ClosePriceIndicator closePrice = new ClosePriceIndicator(timeSeries);
        final DoubleEMAIndicator doubleEMAIndicator = new DoubleEMAIndicator(closePrice, 50);
        return new StrategyWrapper(
                new Strategy(
                        new OverIndicatorRule(doubleEMAIndicator, closePrice),
                        new UnderIndicatorRule(doubleEMAIndicator, closePrice)
                ),
                StrategyType.DEMA);
    }


    public StrategyWrapper movingMomentumStrategy(TimeSeries timeSeries) {
        return new StrategyWrapper(MovingMomentumStrategy.buildStrategy(timeSeries), StrategyType.MM);
    }

    public StrategyWrapper cciStrategy(TimeSeries timeSeries) {
        return
                new StrategyWrapper(CCICorrectionStrategy.buildStrategy(timeSeries),
                        StrategyType.CCI);
    }

    public StrategyWrapper rsi2Strategy(TimeSeries timeSeries) {
        return new StrategyWrapper(RSI2Strategy.buildStrategy(timeSeries), StrategyType.RSI2);
    }
}
