package com.cryptopurse.cryptotrader.advice.continuous;

import com.cryptopurse.cryptotrader.advice.domain.StrategyPeriod;
import com.cryptopurse.cryptotrader.advice.service.UserTradeAdviceService;
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
import java.util.function.Consumer;
import java.util.stream.Stream;

@Component
public class UserTradeAdviceJob {

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
    @Autowired
    private UserTradeAdviceService userTradeAdviceService;

    final StrategyPeriod period = StrategyPeriod.FIFTEEN_MIN;
    final CurrencyPair pair = CurrencyPair.ETHEUR;
    final SupportedExchanges exchange = SupportedExchanges.KRAKEN;

    @Scheduled(fixedRate = 60000)
    public void generateAdvicesPerOrder() {
        List<TradeHistory> recentTrades = tradeHistoryRepository.findRecentTrades(DateTime.now().minusDays(14).toDate(), SupportedExchanges.KRAKEN);
        if (recentTrades.isEmpty()) {
            return;
        }

        List<UserTrade> openTrades = userTradeService.findAllOpen(pair, exchange);
        if (!openTrades.isEmpty()) {
            openTrades.forEach(order -> generateAdvices(recentTrades, order));
        } else {
            generateAdvice(period, null, recentTrades);
        }
    }

    private void generateAdvices(final List<TradeHistory> recentTrades, final UserTrade order) {
        Stream.of(StrategyPeriod.values())
                .forEach(period -> generateAdvice(period, order, recentTrades));
    }

    private void generateAdvice(final StrategyPeriod period, final UserTrade order, final List<TradeHistory> recentTrades) {
        TimeSeries timeseries = timeSeriesBuilder.timeseries(recentTrades, period.getTimeframeInSeconds());
        Stream.of(indicatorService.smaIndicator(timeseries)).forEach(getStrategyWrapperConsumer(period, order, timeseries));
    }

    private Consumer<StrategyWrapper> getStrategyWrapperConsumer(final StrategyPeriod period, final UserTrade order, final TimeSeries timeseries) {
        return strategy -> {
            try {
                if (order == null) {
                    throw new IllegalArgumentException("no orders found");
                }
                final Order theOrder = locator.convertToTradingRecord(timeseries, order);
                System.out.println("order was found");
                userTradeAdviceService.giveAdvice(
                        order,
                        strategy.getType(),
                        strategy.getStrategy().shouldOperate(timeseries.getEnd(), new TradingRecord(theOrder)),
                        period
                );
            } catch (Exception ex) {
                System.out.printf("exception: " + ex.getMessage());
                System.out.println("order wasn't found, checking if we should enter");
                if (strategy.getStrategy().shouldEnter(timeseries.getEnd())) {
                    userTradeService.createTrade(
                            new UserTrade()
                                    .setUser(null)
                                    .setAmount(1)
                                    .setCurrencyPair(pair)
                                    .setClosed(false)
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
