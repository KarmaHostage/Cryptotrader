package com.cryptopurse.cryptotrader.market.domain;

import com.cryptopurse.cryptotrader.exchange.service.supported.SupportedExchanges;
import org.knowm.xchange.dto.Order;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "trade_history")
public class TradeHistory {

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

    @Column(name = "exchange")
    @Enumerated(EnumType.STRING)
    private SupportedExchanges exchange;

    public Long getId() {
        return id;
    }

    public TradeHistory setId(Long id) {
        this.id = id;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public TradeHistory setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }

    public Date getTime() {
        return time;
    }

    public TradeHistory setTime(Date time) {
        this.time = time;
        return this;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public TradeHistory setAmount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    public Order.OrderType getOrderType() {
        return orderType;
    }

    public TradeHistory setOrderType(Order.OrderType orderType) {
        this.orderType = orderType;
        return this;
    }

    public CurrencyPair getCurrencyPair() {
        return currencyPair;
    }

    public TradeHistory setCurrencyPair(CurrencyPair currencyPair) {
        this.currencyPair = currencyPair;
        return this;
    }

    public SupportedExchanges getExchange() {
        return exchange;
    }

    public TradeHistory setExchange(final SupportedExchanges exchange) {
        this.exchange = exchange;
        return this;
    }
}
