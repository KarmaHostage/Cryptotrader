package com.cryptopurse.cryptotrader.timeseries;

import com.cryptopurse.cryptotrader.advice.domain.StrategyPeriod;
import com.cryptopurse.cryptotrader.market.domain.CurrencyPair;
import com.cryptopurse.cryptotrader.market.domain.KrakenTrade;
import com.cryptopurse.cryptotrader.market.service.KrakenTradeService;
import com.cryptopurse.cryptotrader.market.timeseries.KrakenTimeSeriesBuilder;
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
    private KrakenTradeService krakenTradeService;
    @Autowired
    private KrakenTimeSeriesBuilder krakenTimeSeriesBuilder;

    @RequestMapping(method = GET)
    public List<TimeseriesDto> export(@RequestParam(value = "period", defaultValue = "FIVE_MIN") StrategyPeriod period) {
        final List<KrakenTrade> recentTrades = krakenTradeService.findRecentTrades(
                Date.from(LocalDateTime.now().minus(2, ChronoUnit.DAYS).toInstant(ZoneOffset.UTC)))
                .stream()
                .filter(x -> x.getCurrencyPair().equals(CurrencyPair.ETHBTC))
                .collect(Collectors.toList());
        TimeSeries timeseries = krakenTimeSeriesBuilder.timeseries(recentTrades, period.getTimeframeInSeconds());
        int amountOfTixs = timeseries.getTickCount();
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
