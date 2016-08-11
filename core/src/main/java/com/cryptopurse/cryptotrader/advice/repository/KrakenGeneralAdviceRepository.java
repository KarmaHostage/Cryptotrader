package com.cryptopurse.cryptotrader.advice.repository;

import com.cryptopurse.cryptotrader.advice.domain.KrakenGeneralAdvice;
import com.cryptopurse.cryptotrader.advice.domain.StrategyPeriod;
import com.cryptopurse.cryptotrader.advice.domain.StrategyType;
import com.cryptopurse.cryptotrader.infrastructure.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface KrakenGeneralAdviceRepository extends JpaRepository<KrakenGeneralAdvice, Long> {

    Optional<KrakenGeneralAdvice> findByCurrencyPairAndStrategyPeriodAndStrategyType(
            @Param("currencyPair") String currencyPair,
            @Param("strategyPeriod") StrategyPeriod strategyPeriod,
            @Param("strategyType") StrategyType strategyType
    );

}
