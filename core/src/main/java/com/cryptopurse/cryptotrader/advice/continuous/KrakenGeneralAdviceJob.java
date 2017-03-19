package com.cryptopurse.cryptotrader.advice.continuous;

import com.cryptopurse.cryptotrader.advice.domain.AdviceEnum;
import com.cryptopurse.cryptotrader.advice.domain.StrategyPeriod;
import com.cryptopurse.cryptotrader.advice.service.KrakenGeneralAdviceService;
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
import java.util.function.Consumer;
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
        List<KrakenTrade> recentTrades = krakenTradeRepository.findRecentTrades(DateTime.now().minusDays(21).toDate());
        if (recentTrades.isEmpty()) {
            return;
        }
        Stream.of(StrategyPeriod.values())
                .forEach(period -> generateGeneralAdvices(recentTrades, period));
    }

    private void generateGeneralAdvices(List<KrakenTrade> recentTrades, StrategyPeriod period) {
        try {
            final TimeSeries timeseries = timeSeriesBuilder.timeseries(recentTrades, period.getTimeframeInSeconds());
            Stream.of(indicatorService.smaIndicator(timeseries),
                    indicatorService.demaIndicator(timeseries),
                    indicatorService.macdStrategy(timeseries),
                    indicatorService.cciStrategy(timeseries),
                    indicatorService.rsi2Strategy(timeseries),
                    indicatorService.movingMomentumStrategy(timeseries))
                    .forEach(giveAdviceOnStrategy(period, timeseries));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private Consumer<StrategyWrapper> giveAdviceOnStrategy(StrategyPeriod period, TimeSeries timeseries) {
        return strategy -> {
            if (strategy.getStrategy().shouldEnter(timeseries.getEnd())) {
                krakenGeneralAdviceService.giveAdvice(
                        strategy.getType(),
                        period,
                        AdviceEnum.BUY,
                        "ETH/EUR"
                );
            } else if (strategy.getStrategy().shouldExit(timeseries.getEnd())) {
                krakenGeneralAdviceService.giveAdvice(
                        strategy.getType(),
                        period,
                        AdviceEnum.SELL,
                        "ETH/EUR"
                );
            } else {
                krakenGeneralAdviceService.giveAdvice(
                        strategy.getType(),
                        period,
                        AdviceEnum.SOFT,
                        "ETH/EUR"
                );
            }
        };
    }
}
