package com.cryptopurse.cryptotrader.market.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "kraken_import_configuration")
public class KrakenImportConfiguration {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "currency_pair")
    private String currencyPair;

    @Column(name = "last_import_timestamp")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastImportTimestamp;

    @Column(name = "last_import_id")
    private Long lastImportId;

    @Column(name = "ask_price")
    private Double askPrice;

    @Column(name = "bid_price")
    private Double bidPrice;

    @Column(name = "price")
    private Double price;

    public Long getId() {
        return id;
    }

    public KrakenImportConfiguration setId(Long id) {
        this.id = id;
        return this;
    }

    public Date getLastImportTimestamp() {
        return lastImportTimestamp;
    }

    public KrakenImportConfiguration setLastImportTimestamp(Date lastImportTimestamp) {
        this.lastImportTimestamp = lastImportTimestamp;
        return this;
    }

    public Long getLastImportId() {
        return lastImportId;
    }

    public KrakenImportConfiguration setLastImportId(Long lastImportId) {
        this.lastImportId = lastImportId;
        return this;
    }

    public Double getAskPrice() {
        return askPrice;
    }

    public KrakenImportConfiguration setAskPrice(Double askPrice) {
        this.askPrice = askPrice;
        return this;
    }

    public Double getBidPrice() {
        return bidPrice;
    }

    public KrakenImportConfiguration setBidPrice(Double bidPrice) {
        this.bidPrice = bidPrice;
        return this;
    }

    public Double getPrice() {
        return price;
    }

    public KrakenImportConfiguration setPrice(Double price) {
        this.price = price;
        return this;
    }

    public String getCurrencyPair() {
        return currencyPair;
    }

    public KrakenImportConfiguration setCurrencyPair(String currencyPair) {
        this.currencyPair = currencyPair;
        return this;
    }
}
