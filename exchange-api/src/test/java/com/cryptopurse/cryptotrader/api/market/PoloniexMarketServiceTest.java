package com.cryptopurse.cryptotrader.api.market;

import com.cryptopurse.cryptotrader.api.exception.CryptotraderRateLimitException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.poloniex.PoloniexExchange;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;

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


    @Test
    public void testGetTrades() throws Exception {
        try {
            Trades trades = poloniexMarketService.getTrades(CurrencyPair.ETH_BTC);
            assertThat(trades).isNotNull();
        } catch (CryptotraderRateLimitException rate) {
            System.out.println("Rate was limited, gotta retry later. Good for now.");
        }
    }
}