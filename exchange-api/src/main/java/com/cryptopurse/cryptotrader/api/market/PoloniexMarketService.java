package com.cryptopurse.cryptotrader.api.market;

import com.cryptopurse.cryptotrader.api.exception.CryptotraderApiException;
import org.jvnet.hk2.annotations.Service;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.poloniex.PoloniexExchange;

@Service
public class PoloniexMarketService implements MarketService {

    @Override
    public RuntimeException handleException(Exception ex) {
        throw new CryptotraderApiException(ex);
    }

    @Override
    public Class<? extends Exchange> exchangeClass() {
        return PoloniexExchange.class;
    }

    @Override
    public Trades getTrades(CurrencyPair currencyPair, Long... times) {
        throw new CryptotraderApiException("not supported by cryptotrader");
    }
}
