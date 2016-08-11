package com.cryptopurse.cryptotrader.market.timeseries;

import com.cryptopurse.cryptotrader.market.domain.KrakenPlacedOrder;
import com.cryptopurse.cryptotrader.market.domain.PlacedOrderEnum;
import eu.verdelhan.ta4j.Decimal;
import eu.verdelhan.ta4j.Order;
import eu.verdelhan.ta4j.Tick;
import eu.verdelhan.ta4j.TimeSeries;
import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

@Component
public class KrakenTimeSeriesOrderLocator {

    public Order convertToTradingRecord(TimeSeries timeSeries, KrakenPlacedOrder order) {
        int currentTick = 0;
        while (currentTick < timeSeries.getTickCount()) {
            Tick tick = timeSeries.getTick(currentTick);
            DateTime startTime = tick.getBeginTime();
            DateTime endTime = tick.getEndTime();
            if (startTime.isBefore(new DateTime(order.getPlacedAt().getTime())) && endTime.isAfter(new DateTime(order.getPlacedAt()))) {
                if (order.getOrderType().equals(PlacedOrderEnum.BUY)) {
                    return Order.buyAt(currentTick, Decimal.valueOf(order.getPrice()), Decimal.valueOf(order.getAmount()));
                } else {
                    return Order.sellAt(currentTick, Decimal.valueOf(order.getPrice()), Decimal.valueOf(order.getAmount()));
                }
            }
            currentTick++;
        }
        throw new IllegalArgumentException("Order was not in our timeseries");
    }
}
