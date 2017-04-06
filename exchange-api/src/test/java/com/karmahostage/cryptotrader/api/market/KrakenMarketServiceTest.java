package com.karmahostage.cryptotrader.api.market;


import com.karmahostage.cryptotrader.api.exception.CryptotraderRateLimitException;
import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.kraken.KrakenExchange;
import org.mockito.InjectMocks;

import static org.assertj.core.api.Assertions.assertThat;

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

    @Test
    public void testGetTrades() throws Exception {
        try {
            Trades trades = krakenMarketService.getTrades(CurrencyPair.ETH_EUR);
            assertThat(trades).isNotNull();
        } catch (CryptotraderRateLimitException rate) {
            System.out.println("Rate was limited, gotta retry later. Good for now.");
        }
    }

    @Test
    public void testGetTradesSince() throws Exception {
        try {
            Trades trades = krakenMarketService.getTrades(CurrencyPair.ETH_EUR, 1470649096445776196L);
            assertThat(trades).isNotNull();
        } catch (CryptotraderRateLimitException rate) {
            System.out.println("Rate was limited, gotta retry later. Good for now.");
        }
    }
}