package com.karmahostage.cryptotrader.market.repository;

import com.karmahostage.cryptotrader.exchange.service.supported.SupportedExchanges;
import com.karmahostage.cryptotrader.infrastructure.repository.JpaRepository;
import com.karmahostage.cryptotrader.market.domain.CurrencyPair;
import com.karmahostage.cryptotrader.usertrading.UserTrade;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserTradeRepository extends JpaRepository<UserTrade, Long> {


    Optional<UserTrade> findTop1ByCurrencyPairAndExchangeOrderByPlacedAtDesc(@Param("currencyPair") CurrencyPair currencyPair,
                                      @Param("exchange") SupportedExchanges exchange);
}
