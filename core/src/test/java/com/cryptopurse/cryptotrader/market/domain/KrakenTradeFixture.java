package com.cryptopurse.cryptotrader.market.domain;

import org.knowm.xchange.dto.Order;

import java.math.BigDecimal;
import java.util.Date;

public class KrakenTradeFixture {

    public static KrakenTrade aTrade() {
        return new KrakenTrade()
                .setTime(new Date())
                .setOrderType(Order.OrderType.ASK)
                .setCurrencyPair(CurrencyPair.ETHEUR)
                .setAmount(new BigDecimal(10))
                .setPrice(new BigDecimal(12));
    }

}