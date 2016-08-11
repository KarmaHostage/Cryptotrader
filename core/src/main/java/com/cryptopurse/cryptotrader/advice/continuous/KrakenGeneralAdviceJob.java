package com.cryptopurse.cryptotrader.advice.continuous;

import com.cryptopurse.cryptotrader.advice.domain.StrategyPeriod;
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

    @Scheduled(fixedRate = 60000)
    public void logAdvice1h() {
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
        Strategy mmStrategy = indicatorService.movingMomentumStrategy(timeseries);
        Strategy cciStrategy = indicatorService.cciStrategy(timeseries);
        Strategy rsiStrategy = indicatorService.rsiStrategy(timeseries);
        Strategy demaStrategy = indicatorService.demaIndicator(timeseries);
        Strategy macdStrategy = indicatorService.macdStrategy(timeseries);
        System.out.println("General Advice for: " + period);
        try {
            if (smaIndicator.shouldEnter(timeseries.getEnd())) {
                System.out.println("SMA: enter an order to BUY");
            } else if (smaIndicator.shouldExit(timeseries.getEnd())) {
                System.out.println("SMA: enter an order to SELL");
            } else {
                System.out.println("SMA: DONT enter order yet");
            }

            if (mmStrategy.shouldEnter(timeseries.getEnd())) {
                System.out.println("MM: enter an order to BUY");
            } else if (mmStrategy.shouldExit(timeseries.getEnd())) {
                System.out.println("MM: enter an order to SELL");
            } else {
                System.out.println("MM: DONT enter order yet");
            }

            if (cciStrategy.shouldEnter(timeseries.getEnd())) {
                System.out.println("CCI: enter an order to BUY");
            } else if (cciStrategy.shouldExit(timeseries.getEnd())) {
                System.out.println("CCI: enter an order to SELL");
            } else {
                System.out.println("CCI: DONT enter order yet");
            }

            if (rsiStrategy.shouldEnter(timeseries.getEnd())) {
                System.out.println("RSI2: enter an order to BUY");
            } else if (rsiStrategy.shouldExit(timeseries.getEnd())) {
                System.out.println("RSI2: enter an order to SELL");
            } else {
                System.out.println("RSI2: DONT enter order yet");
            }
            if (demaStrategy.shouldEnter(timeseries.getEnd())) {
                System.out.println("DEMA: enter an order to BUY");
            } else if (demaStrategy.shouldExit(timeseries.getEnd())) {
                System.out.println("DEMA: enter an order to SELL");
            } else {
                System.out.println("DEMA: DONT enter order yet");
            }

            if (macdStrategy.shouldEnter(timeseries.getEnd())) {
                System.out.println("MACD: enter an order to BUY");
            } else if (macdStrategy.shouldExit(timeseries.getEnd())) {
                System.out.println("MACD: enter an order to SELL");
            } else {
                System.out.println("MACD: DONT enter order yet");
            }

            System.out.println("\n");
        } catch (Exception ex) {
            System.out.println("the order wasn't found");
        }
    }
}
