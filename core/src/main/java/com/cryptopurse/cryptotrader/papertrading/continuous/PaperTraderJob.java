package com.cryptopurse.cryptotrader.papertrading.continuous;

import com.cryptopurse.cryptotrader.advice.domain.StrategyPeriod;
import com.cryptopurse.cryptotrader.exchange.service.supported.SupportedExchanges;
import com.cryptopurse.cryptotrader.market.domain.CurrencyPair;
import com.cryptopurse.cryptotrader.market.domain.PlacedOrderEnum;
import com.cryptopurse.cryptotrader.market.domain.TradeHistory;
import com.cryptopurse.cryptotrader.market.domain.UserTrade;
import com.cryptopurse.cryptotrader.market.dto.StrategyWrapper;
import com.cryptopurse.cryptotrader.market.repository.TradeHistoryRepository;
import com.cryptopurse.cryptotrader.market.service.StrategyService;
import com.cryptopurse.cryptotrader.market.service.UserTradeService;
import com.cryptopurse.cryptotrader.market.timeseries.TimeSeriesOrderLocator;
import com.cryptopurse.cryptotrader.market.timeseries.TimeseriesBuilder;
import eu.verdelhan.ta4j.Order;
import eu.verdelhan.ta4j.TimeSeries;
import eu.verdelhan.ta4j.TradingRecord;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Stream;

@Component
public class PaperTraderJob {

    @Autowired
    private TradeHistoryRepository tradeHistoryRepository;
    @Autowired
    private TimeseriesBuilder timeSeriesBuilder;
    @Autowired
    private StrategyService indicatorService;
    @Autowired
    private UserTradeService userTradeService;
    @Autowired
    private TimeSeriesOrderLocator locator;

    final StrategyPeriod period = StrategyPeriod.FIFTEEN_MIN;
    final CurrencyPair pair = CurrencyPair.ETHEUR;
    final SupportedExchanges exchange = SupportedExchanges.KRAKEN;

    @Scheduled(fixedRate = 60000)
    public void generateAdvicesPerOrder() {
        final List<TradeHistory> recentTrades = tradeHistoryRepository.findRecentTrades(
                DateTime.now().minusDays(14).toDate(),
                SupportedExchanges.KRAKEN,
                pair);
        if (recentTrades.isEmpty()) {
            return;
        }
        Optional<UserTrade> trade = userTradeService.findLastTrade(pair, exchange);
        tradeOnPaper(period, trade.orElse(null), recentTrades);
    }

    private void tradeOnPaper(final StrategyPeriod period, final UserTrade order, final List<TradeHistory> recentTrades) {
        TimeSeries timeseries = timeSeriesBuilder.timeseries(recentTrades, period.getTimeframeInSeconds());
        getStrategyWrapperConsumer(order, timeseries).accept(indicatorService.smaIndicator(timeseries));
    }

    private Consumer<StrategyWrapper> getStrategyWrapperConsumer(final UserTrade order, final TimeSeries timeseries) {
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
                        userTradeService.createTrade(
                                new UserTrade()
                                        .setUser(null)
                                        .setCurrencyPair(pair)
                                        .setExchange(exchange)
                                        .setAmount(1)
                                        .setPlacedAt(new Date())
                                        .setPrice(timeseries.getLastTick().getClosePrice().toDouble())
                                        .setOrderType(PlacedOrderEnum.SELL)
                        );
                    }
                }
            } catch (Exception ex) {
                System.out.printf("exception: " + ex.getMessage());
                System.out.println("order wasn't found, checking if we should enter");
                if (strategy.getStrategy().shouldEnter(timeseries.getEnd())) {
                    userTradeService.createTrade(
                            new UserTrade()
                                    .setUser(null)
                                    .setAmount(1)
                                    .setCurrencyPair(pair)
                                    .setExchange(exchange)
                                    .setOrderType(PlacedOrderEnum.BUY)
                                    .setPlacedAt(new Date())
                                    .setPrice(timeseries.getLastTick().getClosePrice().toDouble())
                    );
                }
            }
        };
    }

}
