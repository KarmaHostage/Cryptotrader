package com.karmahostage.cryptotrader.usertrading;

import com.karmahostage.cryptotrader.exchange.service.supported.SupportedExchanges;
import com.karmahostage.cryptotrader.market.domain.CurrencyPair;
import com.karmahostage.cryptotrader.market.domain.PlacedOrderEnum;
import com.karmahostage.cryptotrader.usermanagement.domain.CryptotraderUser;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "user_trades")
public class UserTrade {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "amount")
    private double amount;

    @Column(name = "price")
    private double price;

    @Column(name = "order_type")
    @Enumerated(EnumType.STRING)
    private PlacedOrderEnum orderType;

    @Column(name = "is_closed")
    private boolean closed = false;

    @Column(name = "placed_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date placedAt;

    @Column(name = "currency_pair")
    @Enumerated(EnumType.STRING)
    private CurrencyPair currencyPair;

    @Column(name = "exchange")
    @Enumerated(value = EnumType.STRING)
    private SupportedExchanges exchange;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private CryptotraderUser user;

    public Long getId() {
        return id;
    }

    public UserTrade setId(Long id) {
        this.id = id;
        return this;
    }

    public double getAmount() {
        return amount;
    }

    public UserTrade setAmount(double amount) {
        this.amount = amount;
        return this;
    }

    public double getPrice() {
        return price;
    }

    public UserTrade setPrice(double price) {
        this.price = price;
        return this;
    }

    public PlacedOrderEnum getOrderType() {
        return orderType;
    }

    public UserTrade setOrderType(PlacedOrderEnum orderType) {
        this.orderType = orderType;
        return this;
    }

    public Date getPlacedAt() {
        return placedAt;
    }

    public UserTrade setPlacedAt(Date placedAt) {
        this.placedAt = placedAt;
        return this;
    }

    public CurrencyPair getCurrencyPair() {
        return currencyPair;
    }

    public UserTrade setCurrencyPair(CurrencyPair currencyPair) {
        this.currencyPair = currencyPair;
        return this;
    }

    public CryptotraderUser getUser() {
        return user;
    }

    public UserTrade setUser(CryptotraderUser user) {
        this.user = user;
        return this;
    }

    public SupportedExchanges getExchange() {
        return exchange;
    }

    public UserTrade setExchange(final SupportedExchanges exchange) {
        this.exchange = exchange;
        return this;
    }
}
