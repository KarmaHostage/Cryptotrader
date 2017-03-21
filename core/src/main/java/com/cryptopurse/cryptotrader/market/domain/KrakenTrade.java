package com.cryptopurse.cryptotrader.market.domain;

import org.knowm.xchange.dto.Order;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "kraken_trades")
public class KrakenTrade {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "trade_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date time;

    @Column(name = "tradable_amount")
    private BigDecimal amount;

    @Column(name = "order_type")
    @Enumerated(EnumType.STRING)
    private Order.OrderType orderType;

    @Column(name = "currency_pair")
    @Enumerated(value = EnumType.STRING)
    private CurrencyPair currencyPair;

    public Long getId() {
        return id;
    }

    public KrakenTrade setId(Long id) {
        this.id = id;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public KrakenTrade setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public Date getTime() {
        return time;
    }

    public KrakenTrade setTime(Date time) {
        this.time = time;
        return this;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public KrakenTrade setAmount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    public Order.OrderType getOrderType() {
        return orderType;
    }

    public KrakenTrade setOrderType(Order.OrderType orderType) {
        this.orderType = orderType;
        return this;
    }

    public CurrencyPair getCurrencyPair() {
        return currencyPair;
    }

    public KrakenTrade setCurrencyPair(CurrencyPair currencyPair) {
        this.currencyPair = currencyPair;
        return this;
    }
}
