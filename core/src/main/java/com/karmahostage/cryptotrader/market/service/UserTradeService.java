package com.karmahostage.cryptotrader.market.service;

import com.karmahostage.cryptotrader.exchange.service.supported.SupportedExchanges;
import com.karmahostage.cryptotrader.market.domain.CurrencyPair;
import com.karmahostage.cryptotrader.usertrading.UserTrade;
import com.karmahostage.cryptotrader.market.repository.UserTradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserTradeService {

    @Autowired
    private UserTradeRepository userTradeRepository;

    public UserTrade createTrade(UserTrade userTrade) {
        return userTradeRepository.save(userTrade);
    }

    public Optional<UserTrade> findLastTrade(final CurrencyPair pair, final SupportedExchanges exchange) {
        return userTradeRepository.findTop1ByCurrencyPairAndExchangeOrderByPlacedAtDesc(pair, exchange);
    }

    public List<UserTrade> findAll() {
        return userTradeRepository.findAll();
    }
}
