package com.cryptopurse.cryptotrader.api.market;

import com.cryptopurse.cryptotrader.api.exception.CryptotraderApiException;
import com.cryptopurse.cryptotrader.api.exception.CryptotraderRateLimitException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.kraken.KrakenExchange;
import org.springframework.stereotype.Service;

public class KrakenMarketService implements MarketService {

    @Override
    public RuntimeException handleException(Exception ex) {
        if (ex.getMessage().contains("Rate limit exceeded")) {
            return new CryptotraderRateLimitException("rate limit exceeded for Kraken");
        } else {
            return new CryptotraderApiException(ex);
        }
    }

    @Override
    public Class<? extends Exchange> exchangeClass() {
        return KrakenExchange.class;
    }
}
