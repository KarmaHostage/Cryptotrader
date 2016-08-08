package com.cryptopurse.cryptotrader.configuration;

import com.cryptopurse.cryptotrader.api.market.KrakenMarketService;
import com.cryptopurse.cryptotrader.api.market.MarketService;
import com.cryptopurse.cryptotrader.api.market.PoloniexMarketService;
import com.cryptopurse.cryptotrader.exchange.service.supported.SupportedExchanges;
import org.knowm.xchange.currency.CurrencyPair;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WatcherConfiguration {

    @Value("${com.cryptopurse.cryptotrader.watch.exchange}")
    private SupportedExchanges exchanges;

    @Value("${com.cryptopurse.cryptotrader.watch.currencypair}")
    private CurrencyPair currencyPair;

    @Bean
    public MarketService provideMarketService() {
        MarketService marketService;
        switch (getExchange()) {
            case POLONIEX:
                marketService =  new PoloniexMarketService();
                break;
            case KRAKEN:
                marketService = new KrakenMarketService();
                break;
            default:
                throw new IllegalArgumentException("Please specify an exchange");
        }

        try {
            marketService.getTicker(getCurrencyPair());
        } catch (Exception ex) {
            throw new IllegalArgumentException("Something went wrong. please make sure your currencypair/exchange combination is correct");
        }
        return marketService;
    }

    public SupportedExchanges getExchange() {
        return exchanges;
    }

    public CurrencyPair getCurrencyPair() {
        return currencyPair;
    }
}
