package com.cryptopurse.cryptotrader.market;

import com.cryptopurse.cryptotrader.market.domain.KrakenTrade;
import com.cryptopurse.cryptotrader.market.service.StrategyService;
import com.cryptopurse.cryptotrader.market.repository.KrakenTradeRepository;
import com.cryptopurse.cryptotrader.market.timeseries.KrakenTimeSeriesBuilder;
import eu.verdelhan.ta4j.*;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

@Configuration
public class KrakenAdviceLogger {

    @Autowired
    private KrakenTradeRepository krakenTradeRepository;
    @Autowired
    private KrakenTimeSeriesBuilder timeSeriesBuilder;
    @Autowired
    private StrategyService indicatorService;

    @Scheduled(fixedRate = 60000)
    public void logAdvice() {
        List<KrakenTrade> recentTrades = krakenTradeRepository.findRecentTrades(DateTime.now().minusHours(8).toDate());
        if (recentTrades.isEmpty()) {
            return;
        }
        System.out.println("Advice for 15 minutes");
        TimeSeries timeseries = timeSeriesBuilder.timeseries(recentTrades, 900);
        Strategy smaIndicator = indicatorService.smaIndicator(timeseries);
        Strategy mmStrategy = indicatorService.movingMomentumStrategy(timeseries);
        Strategy cciStrategy = indicatorService.cciStrategy(timeseries);
        Strategy rsiStrategy = indicatorService.rsiStrategy(timeseries);


        TradingRecord myLastOrder = new TradingRecord(Order.buyAt(0, Decimal.valueOf(11.24), Decimal.valueOf(35)));

        if (smaIndicator.shouldEnter(timeseries.getEnd(), myLastOrder)) {
            System.out.println("---> SMA: ENTER at " + timeseries.getLastTick().getClosePrice());
        } else {
            System.out.println("---> SMA EXIT at " + timeseries.getLastTick().getClosePrice());
        }

        if (mmStrategy.shouldEnter(timeseries.getEnd())) {
            System.out.println("---> MM: ENTER at " + timeseries.getLastTick().getClosePrice());
        } else {
            System.out.println("---> MM EXIT at " + timeseries.getLastTick().getClosePrice());
        }

        if (cciStrategy.shouldEnter(timeseries.getEnd())) {
            System.out.println("---> CCI: ENTER at " + timeseries.getLastTick().getClosePrice());
        } else {
            System.out.println("---> CCI EXIT at " + timeseries.getLastTick().getClosePrice());
        }

        if (rsiStrategy.shouldEnter(timeseries.getEnd())) {
            System.out.println("---> RSI: ENTER at " + timeseries.getLastTick().getClosePrice());
        } else {
            System.out.println("---> RSI EXIT at " + timeseries.getLastTick().getClosePrice());
        }
    }

    @Scheduled(fixedRate = 60000)
    public void logAdvice5Min() {
        List<KrakenTrade> recentTrades = krakenTradeRepository.findRecentTrades(DateTime.now().minusHours(8).toDate());
        if (recentTrades.isEmpty()) {
            return;
        }
        System.out.println("Advice for 5 minutes");
        TimeSeries timeseries = timeSeriesBuilder.timeseries(recentTrades, 300);
        Strategy smaIndicator = indicatorService.smaIndicator(timeseries);
        Strategy mmStrategy = indicatorService.movingMomentumStrategy(timeseries);
        Strategy cciStrategy = indicatorService.cciStrategy(timeseries);
        Strategy rsiStrategy = indicatorService.rsiStrategy(timeseries);

        if (smaIndicator.shouldEnter(timeseries.getEnd())) {
            System.out.println("---> SMA: ENTER at " + timeseries.getLastTick().getClosePrice());
        } else {
            System.out.println("---> SMA EXIT at " + timeseries.getLastTick().getClosePrice());
        }

        if (mmStrategy.shouldEnter(timeseries.getEnd())) {
            System.out.println("---> MM: ENTER at " + timeseries.getLastTick().getClosePrice());
        } else {
            System.out.println("---> MM EXIT at " + timeseries.getLastTick().getClosePrice());
        }

        if (cciStrategy.shouldEnter(timeseries.getEnd())) {
            System.out.println("---> CCI: ENTER at " + timeseries.getLastTick().getClosePrice());
        } else {
            System.out.println("---> CCI EXIT at " + timeseries.getLastTick().getClosePrice());
        }

        if (rsiStrategy.shouldEnter(timeseries.getEnd())) {
            System.out.println("---> RSI: ENTER at " + timeseries.getLastTick().getClosePrice());
        } else {
            System.out.println("---> RSI EXIT at " + timeseries.getLastTick().getClosePrice());
        }
    }

    @Scheduled(fixedRate = 60000)
    public void logAdvice1h() {
        List<KrakenTrade> recentTrades = krakenTradeRepository.findRecentTrades(DateTime.now().minusHours(8).toDate());
        if (recentTrades.isEmpty()) {
            return;
        }
        System.out.println("Advice for 1hour");
        TimeSeries timeseries = timeSeriesBuilder.timeseries(recentTrades, 3600);
        Strategy smaIndicator = indicatorService.smaIndicator(timeseries);
        Strategy mmStrategy = indicatorService.movingMomentumStrategy(timeseries);
        Strategy cciStrategy = indicatorService.cciStrategy(timeseries);
        Strategy rsiStrategy = indicatorService.rsiStrategy(timeseries);

        if (smaIndicator.shouldEnter(timeseries.getEnd())) {
            System.out.println("---> SMA: ENTER at " + timeseries.getLastTick().getClosePrice());
        } else {
            System.out.println("---> SMA EXIT at " + timeseries.getLastTick().getClosePrice());
        }

        if (mmStrategy.shouldEnter(timeseries.getEnd())) {
            System.out.println("---> MM: ENTER at " + timeseries.getLastTick().getClosePrice());
        } else {
            System.out.println("---> MM EXIT at " + timeseries.getLastTick().getClosePrice());
        }

        if (cciStrategy.shouldEnter(timeseries.getEnd())) {
            System.out.println("---> CCI: ENTER at " + timeseries.getLastTick().getClosePrice());
        } else {
            System.out.println("---> CCI EXIT at " + timeseries.getLastTick().getClosePrice());
        }
        if (rsiStrategy.shouldEnter(timeseries.getEnd())) {
            System.out.println("---> RSI: ENTER at " + timeseries.getLastTick().getClosePrice());
        } else {
            System.out.println("---> RSI EXIT at " + timeseries.getLastTick().getClosePrice());
        }
    }

    @Scheduled(fixedRate = 60000)
    public void logAdvice4h() {
        List<KrakenTrade> recentTrades = krakenTradeRepository.findRecentTrades(DateTime.now().minusHours(8).toDate());
        if (recentTrades.isEmpty()) {
            return;
        }
        System.out.println("Advice for 1hour");
        TimeSeries timeseries = timeSeriesBuilder.timeseries(recentTrades, 14400);
        Strategy smaIndicator = indicatorService.smaIndicator(timeseries);
        Strategy mmStrategy = indicatorService.movingMomentumStrategy(timeseries);
        Strategy cciStrategy = indicatorService.cciStrategy(timeseries);
        Strategy rsiStrategy = indicatorService.rsiStrategy(timeseries);

        if (smaIndicator.shouldEnter(timeseries.getEnd())) {
            System.out.println("---> SMA: ENTER at " + timeseries.getLastTick().getClosePrice());
        } else {
            System.out.println("---> SMA EXIT at " + timeseries.getLastTick().getClosePrice());
        }

        if (mmStrategy.shouldEnter(timeseries.getEnd())) {
            System.out.println("---> MM: ENTER at " + timeseries.getLastTick().getClosePrice());
        } else {
            System.out.println("---> MM EXIT at " + timeseries.getLastTick().getClosePrice());
        }

        if (cciStrategy.shouldEnter(timeseries.getEnd())) {
            System.out.println("---> CCI: ENTER at " + timeseries.getLastTick().getClosePrice());
        } else {
            System.out.println("---> CCI EXIT at " + timeseries.getLastTick().getClosePrice());
        }
        if (rsiStrategy.shouldEnter(timeseries.getEnd())) {
            System.out.println("---> RSI: ENTER at " + timeseries.getLastTick().getClosePrice());
        } else {
            System.out.println("---> RSI EXIT at " + timeseries.getLastTick().getClosePrice());
        }
    }
}
