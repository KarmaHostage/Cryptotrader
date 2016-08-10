package com.cryptopurse.cryptotrader.market.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "kraken_placed_orders")
public class KrakenPlacedOrder {

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

    public Long getId() {
        return id;
    }

    public KrakenPlacedOrder setId(Long id) {
        this.id = id;
        return this;
    }

    public double getAmount() {
        return amount;
    }

    public KrakenPlacedOrder setAmount(double amount) {
        this.amount = amount;
        return this;
    }

    public double getPrice() {
        return price;
    }

    public KrakenPlacedOrder setPrice(double price) {
        this.price = price;
        return this;
    }

    public PlacedOrderEnum getOrderType() {
        return orderType;
    }

    public KrakenPlacedOrder setOrderType(PlacedOrderEnum orderType) {
        this.orderType = orderType;
        return this;
    }

    public boolean isClosed() {
        return closed;
    }

    public KrakenPlacedOrder setClosed(boolean closed) {
        this.closed = closed;
        return this;
    }

    public Date getPlacedAt() {
        return placedAt;
    }

    public KrakenPlacedOrder setPlacedAt(Date placedAt) {
        this.placedAt = placedAt;
        return this;
    }
}
