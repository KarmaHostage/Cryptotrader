package com.cryptopurse.cryptotrader.api.market;

import org.junit.runner.RunWith;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.poloniex.PoloniexExchange;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PoloniexMarketServiceTest extends MarketServiceTest {

    @InjectMocks
    private PoloniexMarketService poloniexMarketService;


    @Override
    MarketService getMarketService() {
        return poloniexMarketService;
    }

    @Override
    Class<? extends Exchange> goodExchange() {
        return PoloniexExchange.class;
    }
}