package com.karmahostage.cryptotrader.advice.repository;

import com.karmahostage.cryptotrader.advice.domain.GeneralAdvice;
import com.karmahostage.cryptotrader.advice.domain.StrategyPeriod;
import com.karmahostage.cryptotrader.advice.domain.StrategyType;
import com.karmahostage.cryptotrader.exchange.service.supported.SupportedExchanges;
import com.karmahostage.cryptotrader.infrastructure.repository.JpaRepository;
import com.karmahostage.cryptotrader.market.domain.CurrencyPair;
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
