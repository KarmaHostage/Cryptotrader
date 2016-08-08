package com.cryptopurse.cryptotrader.market.domain;

import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "kraken_trades")
public class KrakenTrade {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "price")
    private Double price;

    @Column(name = "trade_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date time;

    @Column(name = "tradable_amount")
    private Double amount;

    @Column(name = "order_type")
    @Enumerated(EnumType.STRING)
    private Order.OrderType orderType;

    @Enumerated(EnumType.STRING)
    @Column(name = "currency_pair")
    private CurrencyPair currencyPair;

    public Long getId() {
        return id;
    }

    public KrakenTrade setId(Long id) {
        this.id = id;
        return this;
    }

    public Double getPrice() {
        return price;
    }

    public KrakenTrade setPrice(Double price) {
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

    public Double getAmount() {
        return amount;
    }

    public KrakenTrade setAmount(Double amount) {
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
