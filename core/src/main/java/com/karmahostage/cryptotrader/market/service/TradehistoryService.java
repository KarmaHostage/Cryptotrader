package com.karmahostage.cryptotrader.market.service;

import com.karmahostage.cryptotrader.exchange.service.supported.SupportedExchanges;
import com.karmahostage.cryptotrader.market.domain.TradeHistory;
import com.karmahostage.cryptotrader.market.repository.TradeHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class TradehistoryService {

    @Autowired
    private TradeHistoryRepository tradeHistoryRepository;

    @Transactional(readOnly = true)
    public List<TradeHistory> findRecentTrades(final Date startTime, final SupportedExchanges exchange) {
        return tradeHistoryRepository.findRecentTrades(startTime, exchange);
    }

    @Transactional(readOnly = true)
    public long count() {
        return tradeHistoryRepository.count();
    }

    @Transactional
    public void insert(TradeHistory tradeHistory) {
        tradeHistoryRepository.save(tradeHistory);
    }

    @Transactional
    public void insert(List<TradeHistory> trades) {
        tradeHistoryRepository.save(trades);
    }

    @Transactional
    public void deleteByDateBefore(final Date date) {
        tradeHistoryRepository.deleteTradeHistoryByTimeBefore(date);
    }

}
