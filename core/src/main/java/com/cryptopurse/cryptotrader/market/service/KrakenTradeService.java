package com.cryptopurse.cryptotrader.market.service;

import com.cryptopurse.cryptotrader.market.domain.KrakenTrade;
import com.cryptopurse.cryptotrader.market.repository.KrakenTradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class KrakenTradeService {

    @Autowired
    private KrakenTradeRepository krakenTradeRepository;

    public List<KrakenTrade> findRecentTrades(@Param("startTime") final Date startTime) {
        return krakenTradeRepository.findRecentTrades(startTime);
    }

    public void insert(KrakenTrade krakenTrade) {
        krakenTradeRepository.save(krakenTrade);
    }

    public void insert(List<KrakenTrade> trades) {
        krakenTradeRepository.save(trades);
    }

}
