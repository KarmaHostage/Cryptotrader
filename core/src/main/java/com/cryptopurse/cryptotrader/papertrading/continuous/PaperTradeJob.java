package com.cryptopurse.cryptotrader.papertrading.continuous;

import com.cryptopurse.cryptotrader.market.domain.PlacedOrderEnum;
import com.cryptopurse.cryptotrader.market.domain.TradeHistory;
import com.cryptopurse.cryptotrader.market.dto.StrategyWrapper;
import com.cryptopurse.cryptotrader.market.repository.TradeHistoryRepository;
import com.cryptopurse.cryptotrader.market.service.StrategyService;
import com.cryptopurse.cryptotrader.market.timeseries.TimeSeriesOrderLocator;
import com.cryptopurse.cryptotrader.market.timeseries.TimeseriesBuilder;
import com.cryptopurse.cryptotrader.papertrading.domain.PaperTrade;
import com.cryptopurse.cryptotrader.papertrading.domain.PaperTradeConfiguration;
import com.cryptopurse.cryptotrader.papertrading.service.PaperTradeConfigurationService;
import com.cryptopurse.cryptotrader.papertrading.service.PaperTradeService;
import eu.verdelhan.ta4j.Order;
import eu.verdelhan.ta4j.TimeSeries;
import eu.verdelhan.ta4j.TradingRecord;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

@Component
public class PaperTradeJob {

    private static final Logger logger = LoggerFactory.getLogger(PaperTradeJob.class);

    @Autowired
    private TradeHistoryRepository tradeHistoryRepository;
    @Autowired
    private TimeseriesBuilder timeSeriesBuilder;
    @Autowired
    private StrategyService indicatorService;
    @Autowired
    private PaperTradeService paperTradeService;
    @Autowired
    private PaperTradeConfigurationService paperTradeConfigurationService;
    @Autowired
    private TimeSeriesOrderLocator locator;

    @Scheduled(fixedDelay = 30000)
    public void generateAdvicesPerOrder() {
        paperTradeConfigurationService.findAllActive()
                .parallelStream()
                .forEach(
                        config -> {
                            final List<TradeHistory> recentTrades = tradeHistoryRepository.findRecentTrades(
                                    DateTime.now().minusDays(14).toDate(),
                                    config.getExchange(),
                                    config.getCurrencyPair());
                            if (!recentTrades.isEmpty()) {
                                try {
                                    Optional<PaperTrade> trade = paperTradeService.findLastTrade(config);
                                    tradeOnPaper(trade.orElse(null), config, recentTrades);
                                } catch (Exception ex) {
                                    logger.error("strategy {} not supported yet", config.getStrategyType());
                                }
                            }
                        }
                );
    }

    private void tradeOnPaper(final PaperTrade paperTrade, final PaperTradeConfiguration config, final List<TradeHistory> recentTrades) {
        TimeSeries timeseries = timeSeriesBuilder.timeseries(recentTrades, config.getStrategyPeriod().getTimeframeInSeconds());
        getStrategyWrapperConsumer(paperTrade, config, timeseries).accept(
                indicatorService.byStrategyType(paperTrade.getPaperTradeConfiguration().getStrategyType(), timeseries)
                        .orElseThrow(() -> new IllegalArgumentException("Strategy Not supported yet"))
        );
    }

    private Consumer<StrategyWrapper> getStrategyWrapperConsumer(final PaperTrade order, final PaperTradeConfiguration config, final TimeSeries timeseries) {
        return strategy -> {
            try {
                if (order == null) {
                    throw new IllegalArgumentException("no previous order was found");
                } else if (order.getOrderType().equals(PlacedOrderEnum.SELL)) {
                    throw new IllegalArgumentException("previous order was a sell order");
                } else {
                    final Order theOrder = locator.convertToTradingRecord(timeseries, order);

                    if (strategy.getStrategy().shouldExit(timeseries.getEnd(), new TradingRecord(theOrder))) {
                        System.out.println("order was found");
                        paperTradeService.createTrade(
                                new PaperTrade()
                                        .setAmount(1)
                                        .setPlacedAt(new Date())
                                        .setPrice(timeseries.getLastTick().getClosePrice().toDouble())
                                        .setOrderType(PlacedOrderEnum.SELL)
                                        .setPaperTradeConfiguration(config)
                        );
                    }
                }
            } catch (Exception ex) {
                logger.debug("exception: {}", ex.getMessage());
                logger.debug("order wasn't found, checking if we should enter");
                if (strategy.getStrategy().shouldEnter(timeseries.getEnd())) {
                    paperTradeService.createTrade(
                            new PaperTrade()
                                    .setOrderType(PlacedOrderEnum.BUY)
                                    .setPlacedAt(new Date())
                                    .setPrice(timeseries.getLastTick().getClosePrice().toDouble())
                                    .setPaperTradeConfiguration(config)
                    );
                }
            }
        };
    }

}
