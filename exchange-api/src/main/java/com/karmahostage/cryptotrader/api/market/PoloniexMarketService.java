package com.karmahostage.cryptotrader.api.market;

import com.karmahostage.cryptotrader.api.exception.CryptotraderApiException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.poloniex.PoloniexExchange;

public class PoloniexMarketService implements MarketService {

    @Override
    public RuntimeException handleException(Exception ex) {
        throw new CryptotraderApiException(ex);
    }

    @Override
    public Class<? extends Exchange> exchangeClass() {
        return PoloniexExchange.class;
    }
}
