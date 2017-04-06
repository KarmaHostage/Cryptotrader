package com.karmahostage.cryptotrader.papertrading.domain;

import com.karmahostage.cryptotrader.advice.domain.StrategyPeriod;
import com.karmahostage.cryptotrader.advice.domain.StrategyType;
import com.karmahostage.cryptotrader.exchange.service.supported.SupportedExchanges;
import com.karmahostage.cryptotrader.market.domain.CurrencyPair;
import com.karmahostage.cryptotrader.usermanagement.domain.CryptotraderUser;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "papertrade_configurations")
public class PaperTradeConfiguration {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "currency_pair")
    @Enumerated(value = EnumType.STRING)
    private CurrencyPair currencyPair;

    @Column(name = "creation_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationTime;

    @Column(name = "exchange")
    @Enumerated(value = EnumType.STRING)
    private SupportedExchanges exchange;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private CryptotraderUser user;

    @Column(name = "active")
    private boolean active = true;

    @Column(name = "strategy_period")
    @Enumerated(EnumType.STRING)
    private StrategyPeriod strategyPeriod;

    @Column(name = "strategy_type")
    @Enumerated(EnumType.STRING)
    private StrategyType strategyType;

    public Long getId() {
        return id;
    }

    public PaperTradeConfiguration setId(final Long id) {
        this.id = id;
        return this;
    }

    public CurrencyPair getCurrencyPair() {
        return currencyPair;
    }

    public PaperTradeConfiguration setCurrencyPair(final CurrencyPair currencyPair) {
        this.currencyPair = currencyPair;
        return this;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public PaperTradeConfiguration setCreationTime(final Date creationTime) {
        this.creationTime = creationTime;
        return this;
    }

    public SupportedExchanges getExchange() {
        return exchange;
    }

    public PaperTradeConfiguration setExchange(final SupportedExchanges exchange) {
        this.exchange = exchange;
        return this;
    }

    public CryptotraderUser getUser() {
        return user;
    }

    public PaperTradeConfiguration setUser(final CryptotraderUser user) {
        this.user = user;
        return this;
    }

    public boolean isActive() {
        return active;
    }

    public PaperTradeConfiguration setActive(final boolean active) {
        this.active = active;
        return this;
    }

    public StrategyPeriod getStrategyPeriod() {
        return strategyPeriod;
    }

    public PaperTradeConfiguration setStrategyPeriod(final StrategyPeriod strategyPeriod) {
        this.strategyPeriod = strategyPeriod;
        return this;
    }

    public StrategyType getStrategyType() {
        return strategyType;
    }

    public PaperTradeConfiguration setStrategyType(final StrategyType strategyType) {
        this.strategyType = strategyType;
        return this;
    }

    @Transient
    public boolean isNew() {
        return id == null;
    }
}
