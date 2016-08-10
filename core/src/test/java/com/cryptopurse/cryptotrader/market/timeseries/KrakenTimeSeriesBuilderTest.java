package com.cryptopurse.cryptotrader.market.timeseries;

import com.cryptopurse.cryptotrader.market.domain.KrakenTrade;
import com.cryptopurse.cryptotrader.market.domain.KrakenTradeFixture;
import eu.verdelhan.ta4j.Decimal;
import eu.verdelhan.ta4j.TimeSeries;
import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class KrakenTimeSeriesBuilderTest {

    @InjectMocks
    private KrakenTimeSeriesBuilder timeSeriesBuilder;

    @Test
    public void testBuildTimeseries() throws Exception {
        KrakenTrade lastTrade = KrakenTradeFixture.aTrade();
        KrakenTrade middleTrade = KrakenTradeFixture.aTrade().setTime(DateTime.now().minusMinutes(4).toDate())
                .setPrice(new BigDecimal(11));
        KrakenTrade firstTrade = KrakenTradeFixture.aTrade().setTime(DateTime.now().minusMinutes(10).toDate())
                .setPrice(new BigDecimal(10));
        TimeSeries timeseries = timeSeriesBuilder.timeseries(Arrays.asList(firstTrade, middleTrade, lastTrade), 300);
        assertThat(timeseries).isNotNull();

        assertThat(timeseries.getTickCount()).isEqualTo(2);
        assertThat(timeseries.getFirstTick().getAmount()).isEqualTo(Decimal.valueOf(firstTrade.getAmount().doubleValue()));

        assertThat(timeseries.getLastTick().getOpenPrice()).isEqualTo(Decimal.valueOf(11));
        assertThat(timeseries.getLastTick().getClosePrice()).isEqualTo(Decimal.valueOf(12));
    }
}