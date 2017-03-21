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
    @Enumerated(value = EnumType.STRING)
    private CurrencyPair currencyPair;

    @Column(name = "last_import_timestamp")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastImportTimestamp;

    @Column(name = "last_import_id")
    private Long lastImportId;

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

    public CurrencyPair getCurrencyPair() {
        return currencyPair;
    }

    public KrakenImportConfiguration setCurrencyPair(CurrencyPair currencyPair) {
        this.currencyPair = currencyPair;
        return this;
    }
}
