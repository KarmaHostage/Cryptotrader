package com.cryptopurse.cryptotrader.advice.domain;

import javax.persistence.*;
import java.util.Date;

@Table(name = "kraken_general_advice")
@Entity
public class KrakenGeneralAdvice {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "currency_pair")
    private String currencyPair;

    @Column(name = "strategy_period")
    @Enumerated(EnumType.STRING)
    private StrategyPeriod strategyPeriod;

    @Column(name = "strategy_type")
    @Enumerated(EnumType.STRING)
    private StrategyType strategyType;

    @Column(name = "strategy_advice")
    @Enumerated(EnumType.STRING)
    private AdviceEnum adviceEnum;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "strategy_time")
    private Date strategyTime;

    @Column(name = "confirmations")
    private int confirmations;

    public Long getId() {
        return id;
    }

    public KrakenGeneralAdvice setId(Long id) {
        this.id = id;
        return this;
    }

    public String getCurrencyPair() {
        return currencyPair;
    }

    public KrakenGeneralAdvice setCurrencyPair(String currencyPair) {
        this.currencyPair = currencyPair;
        return this;
    }

    public StrategyPeriod getStrategyPeriod() {
        return strategyPeriod;
    }

    public KrakenGeneralAdvice setStrategyPeriod(StrategyPeriod strategyPeriod) {
        this.strategyPeriod = strategyPeriod;
        return this;
    }

    public StrategyType getStrategyType() {
        return strategyType;
    }

    public KrakenGeneralAdvice setStrategyType(StrategyType strategyType) {
        this.strategyType = strategyType;
        return this;
    }

    public AdviceEnum getAdviceEnum() {
        return adviceEnum;
    }

    public KrakenGeneralAdvice setAdviceEnum(AdviceEnum adviceEnum) {
        this.adviceEnum = adviceEnum;
        return this;
    }

    public Date getStrategyTime() {
        return strategyTime;
    }

    public KrakenGeneralAdvice setStrategyTime(Date strategyTime) {
        this.strategyTime = strategyTime;
        return this;
    }

    public int getConfirmations() {
        return confirmations;
    }

    public KrakenGeneralAdvice setConfirmations(int confirmations) {
        this.confirmations = confirmations;
        return this;
    }
}
