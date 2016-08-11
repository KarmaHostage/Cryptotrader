package com.cryptopurse.cryptotrader.advice.continuous;

import com.cryptopurse.cryptotrader.advice.domain.AdviceEnum;
import com.cryptopurse.cryptotrader.advice.domain.StrategyPeriod;
import com.cryptopurse.cryptotrader.advice.domain.StrategyType;
import com.cryptopurse.cryptotrader.advice.service.KrakenGeneralAdviceService;
import com.cryptopurse.cryptotrader.market.domain.KrakenTrade;
import com.cryptopurse.cryptotrader.market.repository.KrakenTradeRepository;
import com.cryptopurse.cryptotrader.market.service.StrategyService;
import com.cryptopurse.cryptotrader.market.timeseries.KrakenTimeSeriesBuilder;
import eu.verdelhan.ta4j.Strategy;
import eu.verdelhan.ta4j.TimeSeries;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;
import java.util.stream.Stream;

@Configuration
public class KrakenGeneralAdviceJob {

    @Autowired
    private KrakenTradeRepository krakenTradeRepository;
    @Autowired
    private KrakenTimeSeriesBuilder timeSeriesBuilder;
    @Autowired
    private StrategyService indicatorService;
    @Autowired
    private KrakenGeneralAdviceService krakenGeneralAdviceService;

    @Scheduled(fixedRate = 60000)
    public void generateAdvices() {
        List<KrakenTrade> recentTrades = krakenTradeRepository.findRecentTrades(DateTime.now().minusDays(14).toDate());
        if (recentTrades.isEmpty()) {
            return;
        }
        Stream.of(StrategyPeriod.values())
                .forEach(period -> generateGeneralAdvices(recentTrades, period));
    }

    public void generateGeneralAdvices(List<KrakenTrade> recentTrades, StrategyPeriod period) {
        TimeSeries timeseries = timeSeriesBuilder.timeseries(recentTrades, period.getTimeframeInSeconds());
        Strategy smaIndicator = indicatorService.smaIndicator(timeseries);
        Strategy demaStrategy = indicatorService.demaIndicator(timeseries);
        Strategy macdStrategy = indicatorService.macdStrategy(timeseries);
        try {
            if (smaIndicator.shouldEnter(timeseries.getEnd())) {
                krakenGeneralAdviceService.giveAdvice(
                        StrategyType.SMA,
                        period,
                        AdviceEnum.BUY,
                        "ETH/EUR"
                );
            } else if (smaIndicator.shouldExit(timeseries.getEnd())) {
                krakenGeneralAdviceService.giveAdvice(
                        StrategyType.SMA,
                        period,
                        AdviceEnum.SELL,
                        "ETH/EUR"
                );
            } else {
                krakenGeneralAdviceService.giveAdvice(
                        StrategyType.SMA,
                        period,
                        AdviceEnum.SOFT,
                        "ETH/EUR"
                );
            }
            if (demaStrategy.shouldEnter(timeseries.getEnd())) {
                krakenGeneralAdviceService.giveAdvice(
                        StrategyType.DEMA,
                        period,
                        AdviceEnum.BUY,
                        "ETH/EUR"
                );
            } else if (demaStrategy.shouldExit(timeseries.getEnd())) {
                krakenGeneralAdviceService.giveAdvice(
                        StrategyType.DEMA,
                        period,
                        AdviceEnum.SELL,
                        "ETH/EUR"
                );
            } else {
                krakenGeneralAdviceService.giveAdvice(
                        StrategyType.DEMA,
                        period,
                        AdviceEnum.SOFT,
                        "ETH/EUR"
                );
            }

            if (macdStrategy.shouldEnter(timeseries.getEnd())) {
                krakenGeneralAdviceService.giveAdvice(
                        StrategyType.MACD,
                        period,
                        AdviceEnum.BUY,
                        "ETH/EUR"
                );
            } else if (macdStrategy.shouldExit(timeseries.getEnd())) {
                krakenGeneralAdviceService.giveAdvice(
                        StrategyType.MACD,
                        period,
                        AdviceEnum.SELL,
                        "ETH/EUR"
                );
            } else {
                krakenGeneralAdviceService.giveAdvice(
                        StrategyType.MACD,
                        period,
                        AdviceEnum.SOFT,
                        "ETH/EUR"
                );
            }
        } catch (Exception ex) {
            System.out.println("the order wasn't found");
        }
    }
}
