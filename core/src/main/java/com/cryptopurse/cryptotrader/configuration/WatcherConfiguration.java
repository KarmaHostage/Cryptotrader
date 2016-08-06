package com.cryptopurse.cryptotrader.configuration;

import com.cryptopurse.cryptotrader.exchange.service.supported.SupportedExchanges;
import org.knowm.xchange.currency.CurrencyPair;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class WatcherConfiguration {

    @Value("${com.cryptopurse.cryptotrader.watch.exchange}")
    private SupportedExchanges exchanges;

    @Value("${com.cryptopurse.cryptotrader.watch.currencypair}")
    private CurrencyPair currencyPair;

    @PostConstruct
    public void startup() {
        System.out.println("test");
    }

}
