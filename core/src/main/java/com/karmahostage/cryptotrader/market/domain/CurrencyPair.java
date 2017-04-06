package com.karmahostage.cryptotrader.market.domain;

import java.util.Optional;
import java.util.stream.Stream;

public enum CurrencyPair {
    ETHEUR("ETH/EUR", org.knowm.xchange.currency.CurrencyPair.ETH_EUR),
    ETHUSD("ETH/USD", org.knowm.xchange.currency.CurrencyPair.ETH_USD),
    ETHBTC("ETH/BTC", org.knowm.xchange.currency.CurrencyPair.ETH_BTC),
    UNKNOWN("UNKNOWN", null);

    private String textValue;
    private org.knowm.xchange.currency.CurrencyPair xchangePair;

    CurrencyPair(String textValue, org.knowm.xchange.currency.CurrencyPair xchangePair) {
        this.textValue = textValue;
        this.xchangePair = xchangePair;
    }

    public String getTextValue() {
        return textValue;
    }

    public org.knowm.xchange.currency.CurrencyPair getXchangePair() {
        return xchangePair;
    }

    public static Optional<CurrencyPair> fromTextValue(final String textValue) {
        return Stream.of(values())
                .filter(value -> value.getTextValue().equalsIgnoreCase(textValue))
                .findFirst();
    }

    public static CurrencyPair fromXChangeCurrency(org.knowm.xchange.currency.CurrencyPair currencyPair) {
        return Stream.of(values())
                .filter(x -> currencyPair.equals(x.getXchangePair()))
                .findFirst().orElse(CurrencyPair.UNKNOWN);
    }
}