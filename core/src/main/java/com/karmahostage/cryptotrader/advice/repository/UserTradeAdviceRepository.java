package com.karmahostage.cryptotrader.advice.repository;

import com.karmahostage.cryptotrader.advice.domain.UserTradeAdvice;
import com.karmahostage.cryptotrader.advice.domain.StrategyPeriod;
import com.karmahostage.cryptotrader.advice.domain.StrategyType;
import com.karmahostage.cryptotrader.infrastructure.repository.JpaRepository;
import com.karmahostage.cryptotrader.usertrading.UserTrade;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserTradeAdviceRepository extends JpaRepository<UserTradeAdvice, Long> {

    List<UserTradeAdvice> findByUserTrade(@Param("userTrade") UserTrade userTrade);


    Optional<UserTradeAdvice> findByUserTradeAndStrategyTypeAndStrategyPeriod(@Param("userTrade") UserTrade krakenPlacedOrder,
                                                                                      @Param("strategyType") StrategyType strategyType,
                                                                                      @Param("strategyPeriod")StrategyPeriod strategyPeriod);

}
