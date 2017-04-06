package com.karmahostage.cryptotrader.market.batch.dto;

public class TradeStatistics {
    private String volume;
    private String tradeCount;
    private String price;

    public TradeStatistics(final String volume, final String tradeCount, final String price) {
        this.volume = volume;
        this.tradeCount = tradeCount;
        this.price = price;
    }

    public String getVolume() {
        return volume;
    }

    public TradeStatistics setVolume(final String volume) {
        this.volume = volume;
        return this;
    }

    public String getTradeCount() {
        return tradeCount;
    }

    public TradeStatistics setTradeCount(final String tradeCount) {
        this.tradeCount = tradeCount;
        return this;
    }

    public String getPrice() {
        return price;
    }

    public TradeStatistics setPrice(final String price) {
        this.price = price;
        return this;
    }
}
