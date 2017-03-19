package com.cryptopurse.cryptotrader.advice.continuous;

import com.cryptopurse.cryptotrader.advice.domain.StrategyPeriod;
import com.cryptopurse.cryptotrader.advice.domain.StrategyType;
import com.cryptopurse.cryptotrader.advice.service.KrakenPlacedOrderAdviceService;
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
import java.util.stream.Stream;

@Configuration
public class KrakenPlacedOrderAdviceJob {

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
    @Autowired
    private KrakenPlacedOrderAdviceService krakenPlacedOrderAdviceService;

    @Scheduled(fixedRate = 60000)
    public void generateAdvicesPerOrder() {
        List<KrakenTrade> recentTrades = krakenTradeRepository.findRecentTrades(DateTime.now().minusDays(14).toDate());
        if (recentTrades.isEmpty()) {
            return;
        }
        placedOrderService.findAllOpen()
                .forEach(order -> generateAdvices(recentTrades, order));
    }

    private void generateAdvices(List<KrakenTrade> recentTrades, KrakenPlacedOrder order) {
        Stream.of(StrategyPeriod.values())
                .forEach(period -> generateAdvice(period, order, recentTrades));
    }

    private void generateAdvice(StrategyPeriod period, KrakenPlacedOrder order, List<KrakenTrade> recentTrades) {

        TimeSeries timeseries = timeSeriesBuilder.timeseries(recentTrades, period.getTimeframeInSeconds());

        Strategy smaIndicator = indicatorService.smaIndicator(timeseries);
        Strategy mmStrategy = indicatorService.movingMomentumStrategy(timeseries);
        Strategy cciStrategy = indicatorService.cciStrategy(timeseries);
        Strategy rsiStrategy = indicatorService.rsi2Strategy(timeseries);
        Strategy demaStrategy = indicatorService.demaIndicator(timeseries);
        Strategy macdDStrategy = indicatorService.macdStrategy(timeseries);

        System.out.println("Generating advice for period: " + period.name() + "\n");
        try {
            Order theOrder = locator.convertToTradingRecord(timeseries, order);
            krakenPlacedOrderAdviceService.giveAdvice(
                    order,
                    StrategyType.SMA,
                    smaIndicator.shouldOperate(timeseries.getEnd(), new TradingRecord(theOrder)),
                    period
            );
            krakenPlacedOrderAdviceService.giveAdvice(
                    order,
                    StrategyType.MM,
                    mmStrategy.shouldOperate(timeseries.getEnd(), new TradingRecord(theOrder)),
                    period
            );
            krakenPlacedOrderAdviceService.giveAdvice(
                    order,
                    StrategyType.CCI,
                    cciStrategy.shouldOperate(timeseries.getEnd(), new TradingRecord(theOrder)),
                    period
            );
            krakenPlacedOrderAdviceService.giveAdvice(
                    order,
                    StrategyType.RSI2,
                    rsiStrategy.shouldOperate(timeseries.getEnd(), new TradingRecord(theOrder)),
                    period
            );
            krakenPlacedOrderAdviceService.giveAdvice(
                    order,
                    StrategyType.DEMA,
                    demaStrategy.shouldOperate(timeseries.getEnd(), new TradingRecord(theOrder)),
                    period
            );
            krakenPlacedOrderAdviceService.giveAdvice(
                    order,
                    StrategyType.MACD,
                    macdDStrategy.shouldOperate(timeseries.getEnd(), new TradingRecord(theOrder)),
                    period
            );
            System.out.println("\n");
        } catch (Exception ex) {
            System.out.println("the order wasn't found");
        }
    }
}
