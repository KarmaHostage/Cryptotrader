package com.cryptopurse.cryptotrader.exchange.service.domain;

import java.util.List;

public interface Exchange {

    List<String> getCurrencies();
    List<String> getAssets();

}
