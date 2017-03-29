package com.cryptopurse.cryptotrader.papertrading.domain;

import com.cryptopurse.cryptotrader.market.domain.PlacedOrderEnum;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "paper_trades")
public class PaperTrade {

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

    @Column(name = "placed_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date placedAt;

    @ManyToOne
    @JoinColumn(name = "papertrade_config_id")
    private PaperTradeConfiguration paperTradeConfiguration;

    public Long getId() {
        return id;
    }

    public PaperTrade setId(Long id) {
        this.id = id;
        return this;
    }

    public double getAmount() {
        return amount;
    }

    public PaperTrade setAmount(double amount) {
        this.amount = amount;
        return this;
    }

    public double getPrice() {
        return price;
    }

    public PaperTrade setPrice(double price) {
        this.price = price;
        return this;
    }

    public PlacedOrderEnum getOrderType() {
        return orderType;
    }

    public PaperTrade setOrderType(PlacedOrderEnum orderType) {
        this.orderType = orderType;
        return this;
    }

    public Date getPlacedAt() {
        return placedAt;
    }

    public PaperTrade setPlacedAt(Date placedAt) {
        this.placedAt = placedAt;
        return this;
    }

    public PaperTradeConfiguration getPaperTradeConfiguration() {
        return paperTradeConfiguration;
    }

    public PaperTrade setPaperTradeConfiguration(final PaperTradeConfiguration paperTradeConfiguration) {
        this.paperTradeConfiguration = paperTradeConfiguration;
        return this;
    }
}
