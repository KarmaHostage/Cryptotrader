package com.cryptopurse.cryptotrader.advice.domain;

import com.cryptopurse.cryptotrader.usertrading.UserTrade;

import javax.persistence.*;
import java.util.Date;

@Table(name = "user_trade_advice")
@Entity
public class UserTradeAdvice {

    @GeneratedValue
    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private UserTrade userTrade;

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

    public UserTradeAdvice setId(Long id) {
        this.id = id;
        return this;
    }

    public UserTrade getUserTrade() {
        return userTrade;
    }

    public UserTradeAdvice setUserTrade(UserTrade userTrade) {
        this.userTrade = userTrade;
        return this;
    }

    public StrategyType getStrategyType() {
        return strategyType;
    }

    public UserTradeAdvice setStrategyType(StrategyType strategyType) {
        this.strategyType = strategyType;
        return this;
    }

    public boolean isExit() {
        return exit;
    }

    public UserTradeAdvice setExit(boolean exit) {
        this.exit = exit;
        return this;
    }

    public Date getAdviceTime() {
        return adviceTime;
    }

    public UserTradeAdvice setAdviceTime(Date adviceTime) {
        this.adviceTime = adviceTime;
        return this;
    }

    public StrategyPeriod getStrategyPeriod() {
        return strategyPeriod;
    }

    public UserTradeAdvice setStrategyPeriod(StrategyPeriod strategyPeriod) {
        this.strategyPeriod = strategyPeriod;
        return this;
    }
}
