package com.cryptopurse.cryptotrader.advice.repository;

import com.cryptopurse.cryptotrader.advice.domain.GeneralAdvice;
import com.cryptopurse.cryptotrader.advice.domain.StrategyPeriod;
import com.cryptopurse.cryptotrader.advice.domain.StrategyType;
import com.cryptopurse.cryptotrader.exchange.service.supported.SupportedExchanges;
import com.cryptopurse.cryptotrader.infrastructure.repository.JpaRepository;
import com.cryptopurse.cryptotrader.market.domain.CurrencyPair;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface GeneralAdviceRepository extends JpaRepository<GeneralAdvice, Long> {

    Optional<GeneralAdvice> findByCurrencyPairAndStrategyPeriodAndStrategyTypeAndExchange(
            @Param("currencyPair") CurrencyPair currencyPair,
            @Param("strategyPeriod") StrategyPeriod strategyPeriod,
            @Param("strategyType") StrategyType strategyType,
            @Param("exchange") SupportedExchanges supportedExchanges
    );

}
