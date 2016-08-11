package com.cryptopurse.cryptotrader.market.domain;

import eu.verdelhan.ta4j.Order;

public enum PlacedOrderEnum {
    BUY(Order.OrderType.BUY), SELL(Order.OrderType.SELL);

    private Order.OrderType orderType;

    PlacedOrderEnum(Order.OrderType orderType) {
        this.orderType = orderType;
    }

    public Order.OrderType getOrderType() {
        return orderType;
    }
}
