package com.cryptopurse.cryptotrader.advice.continuous;

import com.cryptopurse.cryptotrader.advice.domain.AdviceEnum;
import com.cryptopurse.cryptotrader.advice.domain.StrategyPeriod;
import com.cryptopurse.cryptotrader.advice.service.KrakenGeneralAdviceService;
import com.cryptopurse.cryptotrader.market.domain.CurrencyPair;
import com.cryptopurse.cryptotrader.market.domain.KrakenTrade;
import com.cryptopurse.cryptotrader.market.dto.StrategyWrapper;
import com.cryptopurse.cryptotrader.market.repository.KrakenTradeRepository;
import com.cryptopurse.cryptotrader.market.service.StrategyService;
import com.cryptopurse.cryptotrader.market.timeseries.KrakenTimeSeriesBuilder;
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
        List<KrakenTrade> allTrades = krakenTradeRepository.findRecentTrades(DateTime.now().minusDays(21).toDate());
        if (allTrades.isEmpty()) {
            return;
        }
        final Map<CurrencyPair, List<KrakenTrade>> tradesPerCurrencypair = allTrades
                .stream()
                .collect(Collectors.groupingBy(KrakenTrade::getCurrencyPair));

        tradesPerCurrencypair
                .forEach(
                        ((currencyPair, krakenTrades) -> {
                            Stream.of(StrategyPeriod.values())
                                    .forEach(period -> generateGeneralAdvices(krakenTrades, period, currencyPair));
                        })
                );

    }

    private void generateGeneralAdvices(final List<KrakenTrade> recentTrades,
                                        final StrategyPeriod period,
                                        final CurrencyPair currencyPair) {
        try {
            final TimeSeries timeseries = timeSeriesBuilder.timeseries(recentTrades, period.getTimeframeInSeconds());
            Stream.of(indicatorService.smaIndicator(timeseries),
                    indicatorService.demaIndicator(timeseries),
                    indicatorService.macdStrategy(timeseries),
                    indicatorService.cciStrategy(timeseries),
                    indicatorService.rsi2Strategy(timeseries),
                    indicatorService.movingMomentumStrategy(timeseries))
                    .forEach(giveAdviceOnStrategy(period, timeseries, currencyPair));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private Consumer<StrategyWrapper> giveAdviceOnStrategy(final StrategyPeriod period,
                                                           final TimeSeries timeseries,
                                                           final CurrencyPair currencyPair) {
        return strategy -> {
            if (strategy.getStrategy().shouldEnter(timeseries.getEnd())) {
                krakenGeneralAdviceService.giveAdvice(
                        strategy.getType(),
                        period,
                        AdviceEnum.BUY,
                        currencyPair
                );
            } else if (strategy.getStrategy().shouldExit(timeseries.getEnd())) {
                krakenGeneralAdviceService.giveAdvice(
                        strategy.getType(),
                        period,
                        AdviceEnum.SELL,
                        currencyPair
                );
            } else {
                krakenGeneralAdviceService.giveAdvice(
                        strategy.getType(),
                        period,
                        AdviceEnum.SOFT,
                        currencyPair
                );
            }
        };
    }
}
