package com.cryptopurse.cryptotrader.market.domain;

import com.cryptopurse.cryptotrader.usertrading.UserTrade;

import java.util.Date;

public class UserTradeFixture {

    public static UserTrade aBuy() {
        return new UserTrade()
                .setAmount(10)
                .setId(1L)
                .setOrderType(PlacedOrderEnum.BUY)
                .setAmount(10)
                .setPlacedAt(new Date());
    }

}