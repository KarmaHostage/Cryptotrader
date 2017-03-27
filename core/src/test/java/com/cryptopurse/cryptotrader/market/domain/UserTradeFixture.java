package com.cryptopurse.cryptotrader.market.domain;

import java.util.Date;

public class UserTradeFixture {

    public static UserTrade aBuy() {
        return new UserTrade()
                .setAmount(10)
                .setClosed(false)
                .setId(1L)
                .setOrderType(PlacedOrderEnum.BUY)
                .setAmount(10)
                .setPlacedAt(new Date());
    }

}