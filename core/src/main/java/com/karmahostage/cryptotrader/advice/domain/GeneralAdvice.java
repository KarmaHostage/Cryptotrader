package com.karmahostage.cryptotrader.advice.domain;

import com.karmahostage.cryptotrader.exchange.service.supported.SupportedExchanges;
import com.karmahostage.cryptotrader.market.domain.CurrencyPair;

import javax.persistence.*;
import java.util.Date;

@Table(name = "general_advice")
@Entity
public class GeneralAdvice {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "currency_pair")
    @Enumerated(value = EnumType.STRING)
    private CurrencyPair currencyPair;

    @Column(name = "strategy_period")
    @Enumerated(EnumType.STRING)
    private StrategyPeriod strategyPeriod;

    @Column(name = "strategy_type")
    @Enumerated(EnumType.STRING)
    private StrategyType strategyType;

    @Column(name = "strategy_advice")
    @Enumerated(EnumType.STRING)
    private AdviceEnum advice;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "strategy_time")
    private Date strategyTime;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "strategy_time_first_occurrence")
    private Date strategyTimeFirstOccurrence;

    @Column(name = "confirmations")
    private int confirmations;

    @Column(name = "exchange")
    @Enumerated(EnumType.STRING)
    private SupportedExchanges exchange;

    public Long getId() {
        return id;
    }

    public GeneralAdvice setId(Long id) {
        this.id = id;
        return this;
    }

    public CurrencyPair getCurrencyPair() {
        return currencyPair;
    }

    public GeneralAdvice setCurrencyPair(CurrencyPair currencyPair) {
        this.currencyPair = currencyPair;
        return this;
    }

    public StrategyPeriod getStrategyPeriod() {
        return strategyPeriod;
    }

    public GeneralAdvice setStrategyPeriod(StrategyPeriod strategyPeriod) {
        this.strategyPeriod = strategyPeriod;
        return this;
    }

    public StrategyType getStrategyType() {
        return strategyType;
    }

    public GeneralAdvice setStrategyType(StrategyType strategyType) {
        this.strategyType = strategyType;
        return this;
    }

    public AdviceEnum getAdvice() {
        return advice;
    }

    public GeneralAdvice setAdvice(AdviceEnum advice) {
        this.advice = advice;
        return this;
    }

    public Date getStrategyTime() {
        return strategyTime;
    }

    public GeneralAdvice setStrategyTime(Date strategyTime) {
        this.strategyTime = strategyTime;
        return this;
    }

    public int getConfirmations() {
        return confirmations;
    }

    public GeneralAdvice setConfirmations(int confirmations) {
        this.confirmations = confirmations;
        return this;
    }

    public Date getStrategyTimeFirstOccurrence() {
        return strategyTimeFirstOccurrence;
    }

    public GeneralAdvice setStrategyTimeFirstOccurrence(final Date strategyTimeFirstOccurrence) {
        this.strategyTimeFirstOccurrence = strategyTimeFirstOccurrence;
        return this;
    }

    public SupportedExchanges getExchange() {
        return exchange;
    }

    public GeneralAdvice setExchange(final SupportedExchanges exchange) {
        this.exchange = exchange;
        return this;
    }
}
