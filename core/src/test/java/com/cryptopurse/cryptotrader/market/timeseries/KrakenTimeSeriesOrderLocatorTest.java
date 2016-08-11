package com.cryptopurse.cryptotrader.market.timeseries;

import com.cryptopurse.cryptotrader.market.domain.KrakenPlacedOrder;
import com.cryptopurse.cryptotrader.market.domain.KrakenPlacedOrderFixture;
import com.cryptopurse.cryptotrader.market.domain.KrakenTrade;
import com.cryptopurse.cryptotrader.market.domain.KrakenTradeFixture;
import eu.verdelhan.ta4j.Order;
import eu.verdelhan.ta4j.TimeSeries;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class KrakenTimeSeriesOrderLocatorTest {

    private KrakenTimeSeriesBuilder timeSeriesBuilder = new KrakenTimeSeriesBuilder();
    private KrakenTimeSeriesOrderLocator locator;

    @Before
    public void setUp() throws Exception {
        locator = new KrakenTimeSeriesOrderLocator();
    }

    @Test
    public void locateAndFound() throws Exception {
        TimeSeries timeSeries = setupTimeSeries();
        KrakenPlacedOrder krakenPlacedOrder = KrakenPlacedOrderFixture.aBuy().setPlacedAt(DateTime.now().minusMinutes(4).toDate());
        Order order = locator.convertToTradingRecord(timeSeries, krakenPlacedOrder);
        assertThat(order).isNotNull();
        assertThat(order.getIndex()).isEqualTo(1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void locateAndNotFound() throws Exception {
        TimeSeries timeSeries = setupTimeSeries();
        KrakenPlacedOrder krakenPlacedOrder = KrakenPlacedOrderFixture.aBuy().setPlacedAt(DateTime.now().minusMinutes(12).toDate());
        Order order = locator.convertToTradingRecord(timeSeries, krakenPlacedOrder);
        assertThat(order).isNotNull();
    }

    private TimeSeries setupTimeSeries() {
        KrakenTrade lastTrade = KrakenTradeFixture.aTrade();
        KrakenTrade middleTrade = KrakenTradeFixture.aTrade().setTime(DateTime.now().minusMinutes(4).toDate())
                .setPrice(new BigDecimal(8));
        KrakenTrade firstTrade = KrakenTradeFixture.aTrade().setTime(DateTime.now().minusMinutes(10).toDate())
                .setPrice(new BigDecimal(12));
        return timeSeriesBuilder.timeseries(Arrays.asList(firstTrade, middleTrade, lastTrade), 300);
    }
}