package com.karmahostage.cryptotrader.api.market;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.mockito.runners.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public abstract class MarketServiceTest {

    abstract MarketService getMarketService();

    @Test
    public void getsGoodExchange() {
        assertThat(getMarketService().getExchange())
                .isInstanceOf(goodExchange());
    }

    abstract Class<? extends Exchange> goodExchange();

    @Test
    public void tickerIsValid() throws Exception {
        Ticker ticker = getMarketService().getTicker(getTestPair());
        assertThat(ticker).isNotNull();
        assertThat(ticker.getAsk()).isNotNull();
        assertThat(ticker.getBid()).isNotNull();
        assertThat(ticker.getHigh()).isNotNull();
        assertThat(ticker.getLow()).isNotNull();
        assertThat(ticker.getVolume()).isNotNull();
    }

    public CurrencyPair getTestPair() {
        return CurrencyPair.ETH_BTC;
    }

}
