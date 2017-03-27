package com.cryptopurse.cryptotrader.market.service;

import com.cryptopurse.cryptotrader.advice.domain.StrategyPeriod;
import com.cryptopurse.cryptotrader.exchange.service.supported.SupportedExchanges;
import com.cryptopurse.cryptotrader.market.domain.CurrencyPair;
import com.cryptopurse.cryptotrader.market.domain.UserTrade;
import com.cryptopurse.cryptotrader.market.repository.UserTradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserTradeService {

    @Autowired
    private UserTradeRepository userTradeRepository;

    public List<UserTrade> findAllOpen() {
        return userTradeRepository.findAllOpenOrders();
    }

    public List<UserTrade> findAllOpen(final CurrencyPair pair, final SupportedExchanges exchange) {
        return userTradeRepository.findAllOpen(pair, exchange);
    }

    public UserTrade createTrade(UserTrade userTrade) {
        return userTradeRepository.save(userTrade);
    }
}
