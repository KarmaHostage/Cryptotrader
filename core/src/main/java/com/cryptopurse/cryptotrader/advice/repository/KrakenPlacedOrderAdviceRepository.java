package com.cryptopurse.cryptotrader.advice.repository;

import com.cryptopurse.cryptotrader.advice.domain.KrakenPlacedOrderAdvice;
import com.cryptopurse.cryptotrader.advice.domain.StrategyPeriod;
import com.cryptopurse.cryptotrader.advice.domain.StrategyType;
import com.cryptopurse.cryptotrader.infrastructure.repository.JpaRepository;
import com.cryptopurse.cryptotrader.market.domain.KrakenPlacedOrder;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface KrakenPlacedOrderAdviceRepository extends JpaRepository<KrakenPlacedOrderAdvice, Long> {

    List<KrakenPlacedOrderAdvice> findByKrakenPlacedOrder(@Param("krakenPlacedOrder") KrakenPlacedOrder krakenPlacedOrder);


    Optional<KrakenPlacedOrderAdvice> findByKrakenPlacedOrderAndStrategyTypeAndStrategyPeriod(@Param("krakenPlacedOrder") KrakenPlacedOrder krakenPlacedOrder,
                                                                                              @Param("strategyType") StrategyType strategyType,
                                                                                              @Param("strategyPeriod")StrategyPeriod strategyPeriod);

}
