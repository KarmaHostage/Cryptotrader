package com.cryptopurse.cryptotrader.market.timeseries;

import eu.verdelhan.ta4j.Order;
import eu.verdelhan.ta4j.TimeSeries;
import org.springframework.stereotype.Component;

@Component
public class KrakenTimeSeriesOrderLocator {

    public void indexInTimeSeries(TimeSeries timeSeries, Order order) {
        int currentTick = 0;
        while (currentTick < timeSeries.getTickCount()) {

        }
    }

}
