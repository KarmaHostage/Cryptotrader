package com.cryptopurse.cryptotrader.advice;

import com.cryptopurse.cryptotrader.market.domain.KrakenPlacedOrder;
import com.cryptopurse.cryptotrader.market.domain.KrakenTrade;
import com.cryptopurse.cryptotrader.market.repository.KrakenTradeRepository;
import com.cryptopurse.cryptotrader.market.service.KrakenPlacedOrderService;
import com.cryptopurse.cryptotrader.market.service.StrategyService;
import com.cryptopurse.cryptotrader.market.timeseries.KrakenTimeSeriesBuilder;
import com.cryptopurse.cryptotrader.market.timeseries.KrakenTimeSeriesOrderLocator;
import eu.verdelhan.ta4j.Order;
import eu.verdelhan.ta4j.Strategy;
import eu.verdelhan.ta4j.TimeSeries;
import eu.verdelhan.ta4j.TradingRecord;
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
    @Autowired
    private KrakenPlacedOrderService placedOrderService;
    @Autowired
    private KrakenTimeSeriesOrderLocator locator;

    @Scheduled(fixedRate = 60000)
    public void logAdvice1h() {
        List<KrakenTrade> recentTrades = krakenTradeRepository.findRecentTrades(DateTime.now().minusHours(8).toDate());
        if (recentTrades.isEmpty()) {
            return;
        }
        placedOrderService.findAllOpen()
                .forEach(order -> {
                    checkOrder(recentTrades, order);
                });
    }

    private void checkOrder(List<KrakenTrade> recentTrades, KrakenPlacedOrder order) {
        TimeSeries timeseries = timeSeriesBuilder.timeseries(recentTrades, 3600);
        Strategy smaIndicator = indicatorService.smaIndicator(timeseries);
        Strategy mmStrategy = indicatorService.movingMomentumStrategy(timeseries);
        Strategy cciStrategy = indicatorService.cciStrategy(timeseries);
        Strategy rsiStrategy = indicatorService.rsiStrategy(timeseries);

        try {
            Order theOrder = locator.convertToTradingRecord(timeseries, order);
            if (smaIndicator.shouldExit(timeseries.getEnd(), new TradingRecord(theOrder))) {
                System.out.println("SMA: exit your oder");
            } else {
                System.out.println("SMA: don't exit order yet");
            }

            if (mmStrategy.shouldExit(timeseries.getEnd(), new TradingRecord(theOrder))) {
                System.out.println("MM: exit your oder");
            } else {
                System.out.println("MM: don't exit order yet");
            }

            if (cciStrategy.shouldExit(timeseries.getEnd(), new TradingRecord(theOrder))) {
                System.out.println("CCI: exit your oder");
            } else {
                System.out.println("CCI: don't exit order yet");
            }

            if (rsiStrategy.shouldExit(timeseries.getEnd(), new TradingRecord(theOrder))) {
                System.out.println("RSI: exit your oder");
            } else {
                System.out.println("RSI: don't exit order yet");
            }
        } catch (Exception ex) {
            System.out.println("the order wasn't found");
        }
    }
}
