package com.cryptopurse.cryptotrader.api.market;

import com.cryptopurse.cryptotrader.api.exception.CryptotraderApiException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;

public interface MarketService {

    default Ticker getTicker(CurrencyPair currencyPair) {

        try {
            Exchange theExchange = getExchange();
            return theExchange.getPollingMarketDataService().getTicker(currencyPair);
        } catch (Exception ex) {
            throw new CryptotraderApiException("Unable to fetch ticker for Poloniex", ex);
        }
    }

    default Ticker getTicker(CurrencyPair currencyPair, long startDate, long endDate) {
        try {
            Exchange theExchange = getExchange();
            return theExchange.getPollingMarketDataService().getTicker(currencyPair, startDate, endDate);
        } catch (Exception ex) {
            throw new CryptotraderApiException("Unable to fetch ticker for Poloniex", ex);
        }
    }

    default Exchange getExchange() {
        return ExchangeFactory.INSTANCE.createExchange(exchangeClass().getName());
    }

    Class<? extends Exchange> exchangeClass();

}
