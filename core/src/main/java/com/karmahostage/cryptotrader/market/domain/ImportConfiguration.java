package com.karmahostage.cryptotrader.market.domain;

import com.karmahostage.cryptotrader.exchange.service.supported.SupportedExchanges;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "import_configuration")
public class ImportConfiguration {

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

    @Column(name = "exchange")
    @Enumerated(EnumType.STRING)
    private SupportedExchanges exchange;

    public Long getId() {
        return id;
    }

    public ImportConfiguration setId(Long id) {
        this.id = id;
        return this;
    }

    public Date getLastImportTimestamp() {
        return lastImportTimestamp;
    }

    public ImportConfiguration setLastImportTimestamp(Date lastImportTimestamp) {
        this.lastImportTimestamp = lastImportTimestamp;
        return this;
    }

    public Long getLastImportId() {
        return lastImportId;
    }

    public ImportConfiguration setLastImportId(Long lastImportId) {
        this.lastImportId = lastImportId;
        return this;
    }

    public CurrencyPair getCurrencyPair() {
        return currencyPair;
    }

    public ImportConfiguration setCurrencyPair(CurrencyPair currencyPair) {
        this.currencyPair = currencyPair;
        return this;
    }

    public SupportedExchanges getExchange() {
        return exchange;
    }

    public ImportConfiguration setExchange(final SupportedExchanges exchange) {
        this.exchange = exchange;
        return this;
    }

    @Transient
    public boolean isNew() {
        return id == null;
    }
}
