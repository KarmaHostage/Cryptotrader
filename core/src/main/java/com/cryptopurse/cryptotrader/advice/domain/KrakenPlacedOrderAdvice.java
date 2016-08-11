package com.cryptopurse.cryptotrader.advice.domain;

import com.cryptopurse.cryptotrader.market.domain.KrakenPlacedOrder;

import javax.persistence.*;
import java.util.Date;

@Table(name = "kraken_placed_order_advice")
@Entity
public class KrakenPlacedOrderAdvice {

    @GeneratedValue
    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private KrakenPlacedOrder krakenPlacedOrder;

    @Enumerated(EnumType.STRING)
    @Column(name = "strategy_type")
    private StrategyType strategyType;

    @Column(name = "should_exit")
    private boolean exit;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "advice_time")
    private Date adviceTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "strategy_period")
    private StrategyPeriod strategyPeriod;

    public Long getId() {
        return id;
    }

    public KrakenPlacedOrderAdvice setId(Long id) {
        this.id = id;
        return this;
    }

    public KrakenPlacedOrder getKrakenPlacedOrder() {
        return krakenPlacedOrder;
    }

    public KrakenPlacedOrderAdvice setKrakenPlacedOrder(KrakenPlacedOrder krakenPlacedOrder) {
        this.krakenPlacedOrder = krakenPlacedOrder;
        return this;
    }

    public StrategyType getStrategyType() {
        return strategyType;
    }

    public KrakenPlacedOrderAdvice setStrategyType(StrategyType strategyType) {
        this.strategyType = strategyType;
        return this;
    }

    public boolean isExit() {
        return exit;
    }

    public KrakenPlacedOrderAdvice setExit(boolean exit) {
        this.exit = exit;
        return this;
    }

    public Date getAdviceTime() {
        return adviceTime;
    }

    public KrakenPlacedOrderAdvice setAdviceTime(Date adviceTime) {
        this.adviceTime = adviceTime;
        return this;
    }

    public StrategyPeriod getStrategyPeriod() {
        return strategyPeriod;
    }

    public KrakenPlacedOrderAdvice setStrategyPeriod(StrategyPeriod strategyPeriod) {
        this.strategyPeriod = strategyPeriod;
        return this;
    }
}
