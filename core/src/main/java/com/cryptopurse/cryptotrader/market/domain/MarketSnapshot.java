package com.cryptopurse.cryptotrader.market.domain;

import com.cryptopurse.cryptotrader.exchange.service.supported.SupportedExchanges;
import org.knowm.xchange.currency.CurrencyPair;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "market_snapshot")
public class MarketSnapshot {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "currency_pair")
    @Enumerated(EnumType.STRING)
    private CurrencyPair currencyPair;

    @Column(name = "exchange")
    @Enumerated(EnumType.STRING)
    private SupportedExchanges supportedExchange;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "market_timestamp")
    private Date marketTimestamp;

}
