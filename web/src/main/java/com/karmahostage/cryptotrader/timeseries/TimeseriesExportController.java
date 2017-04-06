package com.karmahostage.cryptotrader.timeseries;

import com.karmahostage.cryptotrader.advice.domain.StrategyPeriod;
import com.karmahostage.cryptotrader.exchange.service.supported.SupportedExchanges;
import com.karmahostage.cryptotrader.market.domain.CurrencyPair;
import com.karmahostage.cryptotrader.market.domain.TradeHistory;
import com.karmahostage.cryptotrader.market.service.TradehistoryService;
import com.karmahostage.cryptotrader.market.timeseries.TimeseriesBuilder;
import eu.verdelhan.ta4j.TimeSeries;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping("/rest/timeseries")
public class TimeseriesExportController {

    @Autowired
    private TradehistoryService tradehistoryService;
    @Autowired
    private TimeseriesBuilder timeseriesBuilder;

    @RequestMapping(method = GET)
    public List<TimeseriesDto> export(@RequestParam(value = "period", defaultValue = "FIVE_MIN") StrategyPeriod period,
                                      @RequestParam(value = "exchange", defaultValue = "KRAKEN") SupportedExchanges exchange,
                                      @RequestParam(value = "pair", defaultValue = "ETHEUR") final CurrencyPair currencyPair) {

        final List<TradeHistory> recentTrades = tradehistoryService.findRecentTrades(
                Date.from(LocalDateTime.now().minus(1, ChronoUnit.DAYS).toInstant(ZoneOffset.UTC)), exchange)
                .stream()
                .filter(x -> x.getCurrencyPair().equals(currencyPair))
                .collect(Collectors.toList());
        final TimeSeries timeseries = timeseriesBuilder.timeseries(recentTrades, period.getTimeframeInSeconds());
        final int amountOfTixs = timeseries.getTickCount();
        return IntStream.range(0, amountOfTixs)
                .mapToObj(timeseries::getTick)
                .map(tick -> new TimeseriesDto(
                        tick.getOpenPrice().toDouble(),
                        tick.getClosePrice().toDouble(),
                        tick.getMinPrice().toDouble(),
                        tick.getMaxPrice().toDouble(),
                        tick.getVolume().toDouble(),
                        toLocalDateTime(tick.getBeginTime()),
                        toLocalDateTime(tick.getEndTime())
                ))
                .collect(Collectors.toList());
    }

    private LocalDateTime toLocalDateTime(final DateTime beginTime) {
        return LocalDateTime.ofInstant(beginTime.toDate().toInstant(), ZoneId.systemDefault());
    }

}
