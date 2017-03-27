package com.cryptopurse.cryptotrader.market.timeseries;

import com.cryptopurse.cryptotrader.market.domain.TradeHistory;
import com.cryptopurse.cryptotrader.market.domain.TradeHistoryFixture;
import com.cryptopurse.cryptotrader.market.domain.UserTrade;
import com.cryptopurse.cryptotrader.market.domain.UserTradeFixture;
import eu.verdelhan.ta4j.Order;
import eu.verdelhan.ta4j.TimeSeries;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class TimeSeriesOrderLocatorTest {

    private TimeseriesBuilder timeSeriesBuilder = new TimeseriesBuilder();
    private TimeSeriesOrderLocator locator;

    @Before
    public void setUp() throws Exception {
        locator = new TimeSeriesOrderLocator();
    }

    @Test
    public void locateAndFound() throws Exception {
        TimeSeries timeSeries = setupTimeSeries();
        UserTrade userTrade = UserTradeFixture.aBuy().setPlacedAt(DateTime.now().minusMinutes(4).toDate());
        Order order = locator.convertToTradingRecord(timeSeries, userTrade);
        assertThat(order).isNotNull();
        assertThat(order.getIndex()).isEqualTo(1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void locateAndNotFound() throws Exception {
        TimeSeries timeSeries = setupTimeSeries();
        UserTrade userTrade = UserTradeFixture.aBuy().setPlacedAt(DateTime.now().minusMinutes(12).toDate());
        Order order = locator.convertToTradingRecord(timeSeries, userTrade);
        assertThat(order).isNotNull();
    }

    private TimeSeries setupTimeSeries() {
        TradeHistory lastTrade = TradeHistoryFixture.aTrade();
        TradeHistory middleTrade = TradeHistoryFixture.aTrade().setTime(DateTime.now().minusMinutes(4).toDate())
                .setPrice(new BigDecimal(8));
        TradeHistory firstTrade = TradeHistoryFixture.aTrade().setTime(DateTime.now().minusMinutes(10).toDate())
                .setPrice(new BigDecimal(12));
        return timeSeriesBuilder.timeseries(Arrays.asList(firstTrade, middleTrade, lastTrade), 300);
    }
}