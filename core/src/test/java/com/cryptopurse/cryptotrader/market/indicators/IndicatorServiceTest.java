package com.cryptopurse.cryptotrader.market.indicators;

import com.cryptopurse.cryptotrader.market.domain.KrakenTrade;
import com.cryptopurse.cryptotrader.market.domain.KrakenTradeFixture;
import com.cryptopurse.cryptotrader.market.service.StrategyService;
import com.cryptopurse.cryptotrader.market.timeseries.KrakenTimeSeriesBuilder;
import eu.verdelhan.ta4j.Strategy;
import eu.verdelhan.ta4j.TimeSeries;
import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class IndicatorServiceTest {

    @InjectMocks
    private StrategyService indicatorService;
    private KrakenTimeSeriesBuilder builder = new KrakenTimeSeriesBuilder();

    @Test
    public void createSmaIndicator() throws Exception {
        KrakenTrade lastTrade = KrakenTradeFixture.aTrade();
        KrakenTrade middleTrade = KrakenTradeFixture.aTrade().setTime(DateTime.now().minusMinutes(4).toDate())
                .setPrice(new BigDecimal(8));
        KrakenTrade firstTrade = KrakenTradeFixture.aTrade().setTime(DateTime.now().minusMinutes(10).toDate())
                .setPrice(new BigDecimal(12));
        TimeSeries timeseries = builder.timeseries(Arrays.asList(firstTrade, middleTrade, lastTrade), 300);

        Strategy indicator = indicatorService.smaIndicator(timeseries);
        assertThat(indicator).isNotNull();

        boolean shouldEnter = indicator.shouldEnter(timeseries.getEnd());
        assertThat(shouldEnter).isFalse();
    }


}