package com.cryptopurse.cryptotrader.api.market;


import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.kraken.KrakenExchange;
import org.mockito.InjectMocks;

public class KrakenMarketServiceTest extends MarketServiceTest {

    @InjectMocks
    private KrakenMarketService krakenMarketService;

    @Override
    MarketService getMarketService() {
        return krakenMarketService;
    }

    @Override
    Class<? extends Exchange> goodExchange() {
        return KrakenExchange.class;
    }

    @Override
    public CurrencyPair getTestPair() {
        return CurrencyPair.ETH_EUR;
    }
}