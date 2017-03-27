package com.cryptopurse.cryptotrader.advice.repository;

import com.cryptopurse.cryptotrader.advice.domain.UserTradeAdvice;
import com.cryptopurse.cryptotrader.advice.domain.StrategyPeriod;
import com.cryptopurse.cryptotrader.advice.domain.StrategyType;
import com.cryptopurse.cryptotrader.infrastructure.repository.JpaRepository;
import com.cryptopurse.cryptotrader.market.domain.UserTrade;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserTradeAdviceRepository extends JpaRepository<UserTradeAdvice, Long> {

    List<UserTradeAdvice> findByUserTrade(@Param("userTrade") UserTrade userTrade);


    Optional<UserTradeAdvice> findByUserTradeAndStrategyTypeAndStrategyPeriod(@Param("userTrade") UserTrade krakenPlacedOrder,
                                                                                      @Param("strategyType") StrategyType strategyType,
                                                                                      @Param("strategyPeriod")StrategyPeriod strategyPeriod);

}
