package com.karmahostage.cryptotrader.market.timeseries;

import com.karmahostage.cryptotrader.market.domain.PlacedOrderEnum;
import com.karmahostage.cryptotrader.papertrading.domain.PaperTrade;
import com.karmahostage.cryptotrader.usertrading.UserTrade;
import eu.verdelhan.ta4j.Decimal;
import eu.verdelhan.ta4j.Order;
import eu.verdelhan.ta4j.Tick;
import eu.verdelhan.ta4j.TimeSeries;
import org.joda.time.DateTime;
import org.springframework.stereotype.Component;

@Component
public class TimeSeriesOrderLocator {

    public Order convertToTradingRecord(TimeSeries timeSeries, UserTrade order) {
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

    public Order convertToTradingRecord(TimeSeries timeSeries, final PaperTrade order) {
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
