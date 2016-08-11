package com.cryptopurse.cryptotrader.market.domain;

import java.util.Date;

public class KrakenPlacedOrderFixture {

    public static KrakenPlacedOrder aBuy() {
        return new KrakenPlacedOrder()
                .setAmount(10)
                .setClosed(false)
                .setId(1L)
                .setOrderType(PlacedOrderEnum.BUY)
                .setAmount(10)
                .setPlacedAt(new Date());
    }

}