package com.cryptopurse.cryptotrader.api.market;

import org.jvnet.hk2.annotations.Service;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.poloniex.PoloniexExchange;

@Service
public class PoloniexMarketService implements MarketService {

    @Override
    public Class<? extends Exchange> exchangeClass() {
        return PoloniexExchange.class;
    }
}
