package com.karmahostage.cryptotrader.advice.continuous;

import com.karmahostage.cryptotrader.advice.domain.AdviceEnum;
import com.karmahostage.cryptotrader.advice.domain.StrategyPeriod;
import com.karmahostage.cryptotrader.advice.service.GeneralAdviceService;
import com.karmahostage.cryptotrader.exchange.service.supported.SupportedExchanges;
import com.karmahostage.cryptotrader.market.domain.CurrencyPair;
import com.karmahostage.cryptotrader.market.domain.TradeHistory;
import com.karmahostage.cryptotrader.market.dto.StrategyWrapper;
import com.karmahostage.cryptotrader.market.repository.TradeHistoryRepository;
import com.karmahostage.cryptotrader.market.service.StrategyService;
import com.karmahostage.cryptotrader.market.timeseries.TimeseriesBuilder;
import eu.verdelhan.ta4j.TimeSeries;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Configuration
public class GeneralAdviceJob {

    @Autowired
    private TradeHistoryRepository tradeHistoryRepository;
    @Autowired
    private TimeseriesBuilder timeSeriesBuilder;
    @Autowired
    private StrategyService indicatorService;
    @Autowired
    private GeneralAdviceService generalAdviceService;

    @Scheduled(fixedRate = 60000)
    public void generateAdvices() {
        givePoloniexAdvices();
        giveKrakenAdvices();
    }

    private void givePoloniexAdvices() {
        //TODO: implement
    }

    private void giveKrakenAdvices() {
        final List<TradeHistory> allTrades = tradeHistoryRepository.findRecentTrades(DateTime.now().minusDays(21).toDate(), SupportedExchanges.KRAKEN);
        if (allTrades.isEmpty()) {
            return;
        }
        final Map<CurrencyPair, List<TradeHistory>> tradesPerCurrencypair = allTrades
                .stream()
                .collect(Collectors.groupingBy(TradeHistory::getCurrencyPair));

        tradesPerCurrencypair
                .forEach(
                        ((currencyPair, krakenTrades) -> {
                            Stream.of(StrategyPeriod.values())
                                    .forEach(period -> generateGeneralAdvices(krakenTrades, period, currencyPair, SupportedExchanges.KRAKEN));
                        })
                );
    }

    private void generateGeneralAdvices(final List<TradeHistory> recentTrades,
                                        final StrategyPeriod period,
                                        final CurrencyPair currencyPair,
                                        final SupportedExchanges exchange) {
        try {
            final TimeSeries timeseries = timeSeriesBuilder.timeseries(recentTrades, period.getTimeframeInSeconds());
            Stream.of(indicatorService.smaIndicator(timeseries),
                    indicatorService.demaIndicator(timeseries),
                    indicatorService.cciStrategy(timeseries),
                    indicatorService.rsi2Strategy(timeseries),
                    indicatorService.movingMomentumStrategy(timeseries))
                    .forEach(giveAdviceOnStrategy(period, timeseries, currencyPair, exchange));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private Consumer<StrategyWrapper> giveAdviceOnStrategy(final StrategyPeriod period,
                                                           final TimeSeries timeseries,
                                                           final CurrencyPair currencyPair,
                                                           final SupportedExchanges exchange) {
         return strategy -> {
            if (strategy.getStrategy().shouldEnter(timeseries.getEnd())) {
                generalAdviceService.giveAdvice(
                        strategy.getType(),
                        period,
                        AdviceEnum.BUY,
                        currencyPair,
                        exchange
                );
            } else if (strategy.getStrategy().shouldExit(timeseries.getEnd())) {
                generalAdviceService.giveAdvice(
                        strategy.getType(),
                        period,
                        AdviceEnum.SELL,
                        currencyPair,
                        exchange
                );
            } else {
                generalAdviceService.giveAdvice(
                        strategy.getType(),
                        period,
                        AdviceEnum.SOFT,
                        currencyPair,
                        exchange
                );
            }
        };
    }
}
