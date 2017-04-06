package com.karmahostage.cryptotrader.api.market;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;

public interface MarketService {

    default Ticker getTicker(CurrencyPair currencyPair) {

        try {
            Exchange theExchange = getExchange();
            return theExchange.getMarketDataService().getTicker(currencyPair);
        } catch (Exception ex) {
            throw handleException(ex);
        }
    }

    default Ticker getTicker(CurrencyPair currencyPair, long startDate, long endDate) {
        try {
            Exchange theExchange = getExchange();
            return theExchange.getMarketDataService().getTicker(currencyPair, startDate, endDate);
        } catch (Exception ex) {
            throw handleException(ex);
        }
    }


    default Trades getTrades(CurrencyPair currencyPair) {
        try {
            Exchange theExchange = getExchange();
            return theExchange.getMarketDataService().getTrades(currencyPair);
        } catch (Exception ex) {
            throw handleException(ex);
        }
    }

    default Trades getTrades(CurrencyPair currencyPair, Long... times) {
        try {
            Exchange theExchange = getExchange();
            return theExchange.getMarketDataService().getTrades(currencyPair, times);
        } catch (Exception ex) {
            throw handleException(ex);
        }
    }

    RuntimeException handleException(Exception ex);

    default Exchange getExchange() {
        return ExchangeFactory.INSTANCE.createExchange(exchangeClass().getName());
    }

    Class<? extends Exchange> exchangeClass();

}
