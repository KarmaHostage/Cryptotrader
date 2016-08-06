package com.cryptopurse.cryptotrader.api.market;

import org.jvnet.hk2.annotations.Service;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.kraken.KrakenExchange;

@Service
public class KrakenMarketService implements MarketService {

    @Override
    public Class<? extends Exchange> exchangeClass() {
        return KrakenExchange.class;
    }
}
