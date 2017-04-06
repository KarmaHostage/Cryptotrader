package com.karmahostage.cryptotrader.exchange.service.supported;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.kraken.KrakenExchange;
import org.knowm.xchange.poloniex.PoloniexExchange;

public enum SupportedExchanges {
    KRAKEN(KrakenExchange.class), POLONIEX(PoloniexExchange.class);

    private Class<? extends Exchange> poloniexExchangeClass;

    SupportedExchanges(final Class<? extends Exchange> poloniexExchangeClass) {
        this.poloniexExchangeClass = poloniexExchangeClass;
    }

    public Class<? extends Exchange> getPoloniexExchangeClass() {
        return poloniexExchangeClass;
    }
}
